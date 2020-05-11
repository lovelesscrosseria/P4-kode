package CodeGeneration;

import AST.AST;
import AST.AstVisitor;
import AST.Nodes.Bool.*;
import AST.Nodes.Functions.*;
import AST.Nodes.Infix.*;
import AST.Nodes.Loops.DoWhileLoopNode;
import AST.Nodes.Loops.ForLoopNode;
import AST.Nodes.Loops.WhileLoopNode;
import AST.Nodes.RoboNode;
import AST.Nodes.Variables.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class JavaCodeGen extends AstVisitor<RoboNode> {
    private File outputFile;
    private FileWriter fw;

    public JavaCodeGen(String fileLocation) {
        try {
            this.outputFile = new File(fileLocation + "\\RoosterRobot.java");
            this.fw = new FileWriter(outputFile);
            if (outputFile.createNewFile()) {
                System.out.println("Created output file: RoosterRobot.java");
            }

            this.defaultEmit();

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
        this.emit("Math.Pow(");
        this.visit(node.Left);
        this.emit(", (");
        this.visit(node.Right);
        this.emit("))");
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

        return null;
    }

    @Override
    public RoboNode visit(BlockNode node) {
        this.emit("{\n");
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
        return null;
    }

    @Override
    public RoboNode visit(ParamNode node) {
        return null;
    }

    @Override
    public RoboNode visit(RoboCodeMethodNode node) {
        return null;
    }

    @Override
    public RoboNode visit(DictionaryDeclNode node) {
        return null;
    }

    @Override
    public RoboNode visit(DictionaryValueNode node) {
        return null;
    }

    @Override
    public RoboNode visit(ForLoopNode node) {
        return null;
    }

    @Override
    public RoboNode visit(DoWhileLoopNode node) {
        return null;
    }

    @Override
    public RoboNode visit(WhileLoopNode node) {
        return null;
    }

    @Override
    public RoboNode visit(FunctionCallExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(RoboCodeMethodExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(ReturnNode node) {
        return null;
    }

    @Override
    public RoboNode visit(DecrementOperatorExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(IncrementOperatorExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(ParensVariableNode node) {
        return null;
    }

    @Override
    public RoboNode visit(NotExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(DotOperationNode node) {
        return null;
    }

    @Override
    public RoboNode visit(DotOperationExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(IfNode node) {
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
        this.emit("public class RoosterRobot extends AdvancedRobot \n");
        this.emit("{ \n");
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
}
