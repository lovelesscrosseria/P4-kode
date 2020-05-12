package CodeGeneration;

import AST.AST;
import AST.AstVisitor;
import AST.Nodes.Bool.*;
import AST.Nodes.Functions.*;
import AST.Nodes.Infix.*;
import AST.Nodes.Loops.DoWhileLoopNode;
import AST.Nodes.Loops.ForLoopNode;
import AST.Nodes.Loops.WhileLoopNode;
import AST.Nodes.ProgramNode;
import AST.Nodes.RoboNode;
import AST.Nodes.Variables.*;
import ContexualAnalysis.EventSymbolTableNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JavaCodeGen extends AstVisitor<RoboNode> {
    private File outputFile;
    private FileWriter fw;
    private HashMap<String, String> convertFunctionNames = new HashMap<String, String>(Map.ofEntries(
            Map.entry("length", "size"),
            Map.entry("health", "getEnergy")
    ));
    private String currentFunction = null;

    @Override
    public RoboNode visit(ProgramNode node) {
        this.defaultEmit();
        for (var item : node.nodes) {
            visit(item);
        }
        this.emit("}");
        this.emitStrategyInterface();
        this.emitStrategies();
        return null;
    }

    private void emitStrategyInterface() {
        this.emit("interface IStrategy { \n");
        this.emit("} \n");
    }

    private void emitStrategies() {
        var strategies = AST.symbolTable.GetStrategies();

        for (var strategy : strategies) {
            this.emit("class Strategy_" + strategy.Id + " implements IStrategy { \n");

            this.emit("} \n");
        }
    }


    public JavaCodeGen(String fileLocation) {
        try {
            this.outputFile = new File(fileLocation + "\\RoosterRobot.java");
            this.fw = new FileWriter(outputFile);
            if (outputFile.createNewFile()) {
                System.out.println("Created output file: RoosterRobot.java");
            }

        } catch (IOException e) {
            AST.errors.add("Could not write to output file at location: " + fileLocation);
        }
    }

    @Override
    public RoboNode visit(AdditionExprNode node) {
        this.emit("(");
        this.visit(node.Left);
        this.emit( " + (");
        this.visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(DivisionExprNode node) {
        this.emit("(");
        this.visit(node.Left);
        this.emit( " / (");
        this.visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(DigitExprNode node) {
        this.emit(node.value + "");
        return null;
    }

    @Override
    public RoboNode visit(ModuloExprNode node) {
        this.emit("(");
        this.visit(node.Left);
        this.emit( " % (");
        this.visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(MultiplicationExprNode node) {
        this.emit("(");
        this.visit(node.Left);
        this.emit( " * (");
        this.visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(SubtractionExprNode node) {
        this.emit("(");
        this.visit(node.Left);
        this.emit( " - (");
        this.visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(IdentifierNode node) {
        this.emit(node.Id);
        return null;
    }

    @Override
    public RoboNode visit(CaretExprNode node) {
        this.emit("(");
        this.emit("Math.pow(");
        this.visit(node.Left);
        this.emit(", (");
        this.visit(node.Right);
        this.emit(")))");
        return null;
    }

    @Override
    public RoboNode visit(TypeNode node) {
        this.emitSimpleType(node);
        return null;
    }

    @Override
    public RoboNode visit(VariableDeclNode node) {
        this.emitSimpleType(node.Type);
        this.emit(node.Id.Id + " = ");
        visit(node.Value);
        emit("; \n");

        return null;
    }

    @Override
    public RoboNode visit(FormalParamNode node) {
        visit(node.Type);
        this.emit(" ");
        visit(node.Id);
        return null;
    }

    @Override
    public RoboNode visit(FunctionDeclNode node) {
        this.currentFunction = node.Id.Id;
        this.emit("public ");
        this.visit(node.Type);
        this.visit(node.Id);
        this.emit("(");
        for (int  i = 0; i < node.Params.size(); i++) {
            var param = node.Params.get(i);
            visit(param);
            if ((i + 1) != node.Params.size()) {
                this.emit(", ");
            }
        }
        this.emit(") \n");
        this.visit(node.block);
        this.currentFunction = null;
        return null;
    }

    @Override
    public RoboNode visit(BlockNode node) {
        this.emit("{\n");
        if (currentFunction != null && currentFunction.equals("run")) {
            this.currentFunction = null;
            var events = AST.symbolTable.GetEvents();
            for (var event : events.values()) {
                this.generateEvent(event);
            }
        }
        for (var stmt : node.statements) {
            visit(stmt);
        }
        this.emit("}\n");
        return null;
    }

    @Override
    public RoboNode visit(StringExprNode node) {
        this.emit(node.value);
        return null;
    }

    @Override
    public RoboNode visit(AssignmentNode node) {
        visit(node.Id);
        this.emit(" = ");
        visit(node.Value);
        this.emit("; \n");
        return null;
    }

    @Override
    public RoboNode visit(DecrementOperatorNode node) {
        visit(node.Id);
        this.emit("--;\n");
        return null;
    }

    @Override
    public RoboNode visit(IncrementOperatorNode node) {
        visit(node.Id);
        this.emit("++;\n");
        return null;
    }

    @Override
    public RoboNode visit(ListDeclNode node) {
        this.emit("ArrayList<");
        this.emitFullType(node.Type);
        this.emit("> ");
        visit(node.Id);
        this.emit(" = new ArrayList<");
        this.emitFullType(node.Type);
        if (node.nodes.size() > 0) {
            this.emit(">(Arrays.asList(");
            for (int i = 0; i < node.nodes.size(); i++) {
                visit(node.nodes.get(i));
                if ((i + 1) != node.nodes.size()) {
                    this.emit(", ");
                }
            }
            this.emit("));\n");
        } else {
            this.emit(">();\n");
        }

        return null;
    }

    @Override
    public RoboNode visit(AndExprNode node) {
        this.emit("(");
        visit(node.Left);
        this.emit(" && (");
        visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(OrExprNode node) {
        this.emit("(");
        visit(node.Left);
        this.emit(" || (");
        visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(EqualExprNode node) {
        this.emit("(");
        visit(node.Left);
        this.emit(" == (");
        visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(NotEqualExprNode node) {
        this.emit("(");
        visit(node.Left);
        this.emit(" != (");
        visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(GreatEqualExprNode node) {
        this.emit("(");
        visit(node.Left);
        this.emit(" >= (");
        visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(GreaterExprNode node) {
        this.emit("(");
        visit(node.Left);
        this.emit(" > (");
        visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(LessEqualExprNode node) {
        this.emit("(");
        visit(node.Left);
        this.emit(" <= (");
        visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(LessExprNode node) {
        this.emit("(");
        visit(node.Left);
        this.emit(" < (");
        visit(node.Right);
        this.emit("))");
        return null;
    }

    @Override
    public RoboNode visit(BoolValueNode node) {
        emit(node.Value.equals("false") ? "false" : "true");
        return null;
    }

    @Override
    public RoboNode visit(StrategyNode node) {
        return null;
    }

    @Override
    public RoboNode visit(BehaviorNode node) {
        return null;
    }

    @Override
    public RoboNode visit(EventNode node) {
        return null;
    }

    @Override
    public RoboNode visit(FunctionCallNode node) {
        if (this.convertFunctionNames.containsKey(node.Method.Id)) {
            node.Method.Id = this.convertFunctionNames.get(node.Method.Id);
        }
        visit(node.Method);
        this.emit("(");

        for (int i = 0; i < node.Params.size(); i++) {
            var param = node.Params.get(i);
            visit(param);
            if ((i + 1) != node.Params.size()) {
                this.emit(", ");
            }
        }

        this.emit("); \n");
        return null;
    }

    @Override
    public RoboNode visit(ParamNode node) {
        visit(node.Value);
        return null;
    }

    @Override
    public RoboNode visit(RoboCodeMethodNode node) {
        visit(node.Method);
        return null;
    }

    @Override
    public RoboNode visit(DictionaryDeclNode node) {
        this.emit("HashMap<");
        this.emitFullType(node.key);
        this.emit(",");
        this.emitFullType(node.value);
        this.emit("> ");
        visit(node.Id);
        this.emit(" = ");
        this.emit("new HashMap<");
        this.emitFullType(node.key);
        this.emit(",");
        this.emitFullType(node.value);
        this.emit(">()");

        if (node.Nodes.size() > 0) {
            this.emit(" {{ \n");
            for (int i = 0; i < node.Nodes.size(); i++) {
                this.emit("put(");
                visit(node.Nodes.get(i));
                this.emit(");\n");
            }

            this.emit("}}; \n");
        } else {
            this.emit(";\n");
        }

        return null;
    }

    @Override
    public RoboNode visit(DictionaryValueNode node) {
        visit(node.Key);
        this.emit(", ");
        visit(node.Value);
        return null;
    }

    @Override
    public RoboNode visit(ForLoopNode node) {
        this.emit("for (");
        visit(node.Init);
        if (!(node.Init instanceof VariableDeclNode)) {
            this.emit("; ");
        }
        visit(node.Condition);
        this.emit(";");
        visit(node.Increment);
        this.emit(") \n");
        visit(node.Block);
        return null;
    }

    @Override
    public RoboNode visit(DoWhileLoopNode node) {
        this.emit("do");
        visit(node.Block);
        this.emit("while(");
        visit(node.Condition);
        this.emit(");\n");
        return null;
    }

    @Override
    public RoboNode visit(WhileLoopNode node) {
        this.emit("while(");
        visit(node.Condition);
        this.emit(")\n");
        visit(node.Block);

        return null;
    }

    @Override
    public RoboNode visit(FunctionCallExprNode node) {
        if (this.convertFunctionNames.containsKey(node.Method.Id)) {
            node.Method.Id = this.convertFunctionNames.get(node.Method.Id);
        }
        visit(node.Method);
        this.emit("(");

        for (int i = 0; i < node.Params.size(); i++) {
            var param = node.Params.get(i);
            visit(param);
            if ((i + 1) != node.Params.size()) {
                this.emit(", ");
            }
        }

        this.emit(")");
        return null;
    }

    @Override
    public RoboNode visit(RoboCodeMethodExprNode node) {
        var func = node.Method;
        if (this.convertFunctionNames.containsKey(func.Method.Id)) {
            func.Method.Id = this.convertFunctionNames.get(func.Method.Id);
        }
        visit(func.Method);
        this.emit("(");

        for (int i = 0; i < func.Params.size(); i++) {
            var param = func.Params.get(i);
            visit(param);
            if ((i + 1) != func.Params.size()) {
                this.emit(", ");
            }
        }

        this.emit(")");
        return null;
    }

    @Override
    public RoboNode visit(ReturnNode node) {
        emit("return ");
        visit(node.Value);
        emit("; \n");
        return null;
    }

    @Override
    public RoboNode visit(DecrementOperatorExprNode node) {
        visit(node.Id);
        this.emit("--");
        return null;
    }

    @Override
    public RoboNode visit(IncrementOperatorExprNode node) {
        visit(node.Id);
        this.emit("++");
        return null;
    }

    @Override
    public RoboNode visit(ParensVariableNode node) {
        emit("(");
        visit(node.value);
        emit(")");
        return null;
    }

    @Override
    public RoboNode visit(NotExprNode node) {
        emit("!(");
        visit(node.Value);
        emit(")");
        return null;
    }

    @Override
    public RoboNode visit(DotOperationNode node) {
        visit(node.Id);
        emit(".");
        visit(node.Method);
        return null;
    }

    @Override
    public RoboNode visit(DotOperationExprNode node) {
        var func = node.Method;

        visit(node.Id);
        emit(".");

        if (this.convertFunctionNames.containsKey(func.Method.Id)) {
            func.Method.Id = this.convertFunctionNames.get(func.Method.Id);
        }

        visit(func.Method);
        this.emit("(");

        for (int i = 0; i < func.Params.size(); i++) {
            var param = func.Params.get(i);
            visit(param);
            if ((i + 1) != func.Params.size()) {
                this.emit(", ");
            }
        }

        this.emit(")");
        return null;
    }

    @Override
    public RoboNode visit(IfNode node) {
        this.emit("if (");
        visit(node.expr);
        this.emit(")\n");
        visit(node.block);
        for (var elseIfnode : node.ifElseNodes) {
            this.emit("else if (");
            visit(elseIfnode.expr);
            this.emit(")\n");
            visit(elseIfnode.block);
        }
        if (node.elseBlock != null) {
            this.emit("else \n");
            visit(node.elseBlock.block);
        }
        return null;
    }

    private void emit(String line) {
        try {
            this.fw.write(line);
            this.fw.flush();
        } catch (IOException e) {
            AST.errors.add("Could not write to output file at location: " + this.outputFile.getAbsolutePath());
        }
    }

    private void defaultEmit() {
        this.emit("import robocode.*; \n");
        this.emit("import java.util.ArrayList; \n");
        this.emit("import java.util.Arrays; \n");
        this.emit("import java.util.HashMap; \n");
        this.emit("import java.util.Map; \n");
        this.emit("public class RoosterRobot extends AdvancedRobot \n");
        this.emit("{ \n");
        this.emitOnCustomEvent();
        this.emitOnScannedRobot();
    }

    private void emitOnScannedRobot() {
        this.emit("public void onScannedRobot(ScannedRobotEvent e) { \n");
        this.emit("strategy.onScannedRobot(this, e);\n");
        this.emit("}\n");
    }

    private void emitOnCustomEvent() {
        boolean isFirst = true;
        this.emit("public void onCustomEvent(CustomEvent e) {\n");
        var events = AST.symbolTable.GetEvents();
        for (var event : events.values()) {
            if (!isFirst) {
                this.emit("else ");
            }
            this.emit("if(e.getCondition().getName().equals(\"" + event.Id + "\")) {\n");
            this.emit("strategy." + event.Id + "(this);\n");
            this.emit("}\n");
            isFirst = false;
        }
        this.emit("}\n");

    }

    private void emitSimpleType(TypeNode type) {
        switch (type.Type) {
            case "num":
                this.emit("double ");
                break;
            case "bool":
                this.emit("boolean ");
                break;
            case "void":
                this.emit("void ");
                break;
            case "text":
                this.emit("String ");
                break;
            case "ScannedRobotEvent":
                this.emit("ScannedRobotEvent ");
                break;
            default:
                AST.errors.add(type.Type + " not implemented in emitSimpleType()");
                break;
        }
    }

    private void emitFullType(TypeNode type) {
        switch (type.Type) {
            case "num":
                this.emit("Double");
                break;
            case "bool":
                this.emit("Boolean");
                break;
            case "text":
                this.emit("String");
                break;
            default:
                AST.errors.add(type.Type + " not implemented in emitFullType()");
                break;
        }
    }

    private void generateEvent(EventSymbolTableNode event) {
        this.emit("addCustomEvent(new Condition(\"" + event.Id + "\") {\n");
        this.emit("public boolean test() ");
        visit(event.block);
        this.emit("});\n");
    }
}
