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
import ContexualAnalysis.BehaviorSymbolTableNode;
import ContexualAnalysis.EventSymbolTableNode;
import ContexualAnalysis.StrategySymbolTableNode;
import ContexualAnalysis.VariableSymbolTableNode;

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
    private BehaviorSymbolTableNode currentBehavior = null;

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

    public JavaCodeGen(String fileLocation) {
        try {
            this.outputFile = new File(fileLocation + "\\Rooster.java");
            this.fw = new FileWriter(outputFile);
            if (outputFile.createNewFile()) {
                System.out.println("Created output file: Rooster.java");
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
            this.emit("strategy.onRun(this);\n");
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
        if (this.currentBehavior != null) {
            this.emit("obj.");
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
        var func = node.Method;
        if (this.convertFunctionNames.containsKey(func.Method.Id)) {
            func.Method.Id = this.convertFunctionNames.get(func.Method.Id);
        }

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
            this.emit(" {");
            this.emit(" private static final long serialVersionUID = 10L; ");
            this.emit("{ \n");

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
        if (this.currentBehavior != null) {
            this.emit("obj.");
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
        if (this.currentBehavior != null) {
            emit("obj.");
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
        this.emit("package Rooster; \n");
        this.emit("import robocode.*; \n");
        this.emit("import java.util.ArrayList; \n");
        this.emit("import java.util.Arrays; \n");
        this.emit("import java.util.HashMap; \n");
        this.emit("import java.util.Map; \n");
        this.emit("public class Rooster extends AdvancedRobot \n");
        this.emit("{ \n");
        this.emitPrintMethod();
        this.emitStrategyVariable();
        this.emitChangeStrategy();
        this.emitOnCustomEvent();
        this.emitOnScannedRobot();
    }

    private void emitPrintMethod() {
        this.emit("public void print(String s) { \n");
        this.emit("System.out.println(s);\n");
        this.emit("}\n");
    }

    private void emitStrategyVariable() {
        var strategy = AST.symbolTable.GetStrategies().size() == 0 ? null : AST.symbolTable.GetStrategies().toArray(new StrategySymbolTableNode[0])[0];

        if (strategy == null) {
            return;
        }

        this.emit("public IStrategy strategy = new Strategy_" + strategy.Id + "(); \n");
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

    private void emitStrategyInterface() {
        var behaviors = this.getAllBehaviorsDefined().values();
        this.emit("interface IStrategy { \n");
        for (var behavior : behaviors) {
            this.emitBehaviorHead(behavior, false);
        }
        this.emit("} \n");
    }

    private void emitStrategies() {
        var strategies = AST.symbolTable.GetStrategies();


        for (var strategy : strategies) {
            var currentStrategyBehaviors = strategy.getBehaviors();
            this.emit("class Strategy_" + strategy.Id + " implements IStrategy { \n");

            this.emitStrategyBehaviors(currentStrategyBehaviors);

            this.emit("} \n");
        }
    }

    private void emitStrategyBehaviors(HashMap<String, BehaviorSymbolTableNode> currentStrategyBehaviors) {
        var allBehaviorsDefined = this.getAllBehaviorsDefined().values();

        for (var behavior : currentStrategyBehaviors.values()) {
            this.currentBehavior = behavior;
            this.emitBehavior(behavior);
            this.currentBehavior = null;
        }
        for (var behavior : allBehaviorsDefined) {
            if (!currentStrategyBehaviors.containsKey(behavior.Id)) {
                this.emitEmptyBehavior(behavior);
            }
        }
    }

    private void emitEmptyBehavior(BehaviorSymbolTableNode behavior) {
        this.emitBehaviorHead(behavior, true);
        this.emit("{ } \n");
    }

    private void emitBehaviorHead(BehaviorSymbolTableNode behavior, boolean isPartOfFunction) {
        var behaviorParams = behavior.getParams().values().toArray(new VariableSymbolTableNode[0]);
        if (isPartOfFunction) {
            this.emit("public ");
        }
        this.emit("void " + behavior.Id + "(Rooster obj");
        if (behaviorParams.length > 0) {
            this.emit(", ");
        }

        for (int i = 0; i < behaviorParams.length; i++) {
            var param = behaviorParams[i];
            this.emit("" + param.Type + " " + param.Id);

            if ((i + 1) != behaviorParams.length) {
                this.emit(", ");
            }
        }

        this.emit(")");
        if (isPartOfFunction) {
            this.emit("\n");
        } else {
            this.emit("; \n");
        }
    }

    private void emitBehavior(BehaviorSymbolTableNode behavior) {
        this.emitBehaviorHead(behavior, true);
        this.visit(behavior.block);
    }

    private void emitChangeStrategy() {
        var strategies = AST.symbolTable.GetStrategies();

        this.emit("public void changeStrategy(String strat) {\n");

        for (var strategy : strategies) {
            this.emit("if(strat == \"" + strategy.Id + "\") {\n");
            this.emit("this.strategy = new Strategy_" + strategy.Id + "();\n");
            this.emit("}\n");
        }

        this.emit("this.strategy.onRun(this);\n");

        this.emit("}\n");
    }

    private HashMap<String, BehaviorSymbolTableNode> getAllBehaviorsDefined() {
        HashMap<String, BehaviorSymbolTableNode> nodes = new HashMap<String, BehaviorSymbolTableNode>();
        var strategies = AST.symbolTable.GetStrategies();
        var events = AST.symbolTable.GetEvents().values();

        for (var strategy : strategies) {
            var behaviors = strategy.getBehaviors();
            for (var behavior : behaviors.values()) {
                if (!nodes.containsKey(behavior.Id)) {
                    nodes.put(behavior.Id, behavior);
                }
            }
        }

        for (var event : events) {
            if (!nodes.containsKey(event.Id)) {
                var behavior = new BehaviorSymbolTableNode();
                behavior.block = event.block;
                behavior.Id = event.Id;
                behavior.Type = "void";
                nodes.put(event.Id, behavior);
            }
        }

        return nodes;
    }
}
