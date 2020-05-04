package TypeChecking;

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

import java.util.Arrays;
import java.util.HashSet;

public class TypeChecking extends AstVisitor<RoboNode> {

    /*private TypeNode generalize(RoboNode left, RoboNode right) {
        var result = new TypeNode();
        var leftType = left.Type.Type;
        var rightType = right.Type.Type;
        HashSet<String> additionTypes = new HashSet<String>(Arrays.asList("num", "text"));

        if (leftType.equals("num") && rightType.equals("num")) {
            result.Type = "num";
        } else if (!additionTypes.contains(leftType) || !additionTypes.contains(rightType)) {
            this.error(left.LineNumber, "Cannot generalize " + leftType + " and " + rightType + " types");
            return null;
        } else if (leftType.equals("text") || rightType.equals("text")) {

        }

        return result;
    } */

    @Override
    public RoboNode visit(AdditionExprNode node) {
        HashSet<String> additionTypes = new HashSet<String>(Arrays.asList("num", "text"));
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;

        if (leftType.equals("num") && rightType.equals("num")) {
            node.Type = new TypeNode();
            node.Type.Type = "num";
        } else if ((leftType.equals("text") || rightType.equals("text")) && additionTypes.contains(leftType) && additionTypes.contains(rightType)) {
            node.Type = new TypeNode();
            node.Type.Type = "text";
        } else if (!additionTypes.contains(leftType) || !additionTypes.contains(rightType)) {
            this.error(left.LineNumber, "Cannot add type " + leftType + "and type " + rightType + " together, as they are not compatible for addition");
            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(DivisionExprNode node) {
        // @todo divison by 0?

        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;

        if (!leftType.equals("num") || !rightType.equals("num")) {
            this.error(left.LineNumber, "Cannot divide type " + leftType + "and type " + rightType + " together, as they are not compatible for division");
        } else {
            node.Type = new TypeNode();
            node.Type.Type = "num";
        }

        return node;
    }

    @Override
    public RoboNode visit(DigitExprNode node) {
        // 1,2,3,4,5,6,7,8,9... tal
        node.Type.Type = "num";

        return node;
    }

    @Override
    public RoboNode visit(ModuloExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;

        if (!leftType.equals("num") || !rightType.equals("num")) {
            this.error(left.LineNumber, "Cannot use modulo on type " + leftType + "and type " + rightType + " together, as they are not compatible for modulo");
        } else {
            node.Type = new TypeNode();
            node.Type.Type = "num";
        }

        return node;
    }

    @Override
    public RoboNode visit(MultiplicationExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;

        if (!leftType.equals("num") || !rightType.equals("num")) {
            this.error(left.LineNumber, "Cannot multiply on type " + leftType + "and type " + rightType + " together, as they are not compatible for multiplication");
        } else {
            node.Type = new TypeNode();
            node.Type.Type = "num";
        }

        return node;
    }

    @Override
    public RoboNode visit(SubtractionExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;

        if (!leftType.equals("num") || !rightType.equals("num")) {
            this.error(left.LineNumber, "Cannot subract type " + leftType + "and type " + rightType + ", as they are not compatible for subtraction");
            return null;
        } else {
            node.Type = new TypeNode();
            node.Type.Type = "num";
        }

        return node;
    }

    @Override
    public RoboNode visit(IdentifierNode node) {
        var variable = AST.symbolTable.GetVariable(node.Id);
        var result = new IdentifierNode();
        result.Id = variable.Id;
        result.Type = new TypeNode();
        result.Type.Type = variable.Type;
        result.LineNumber = node.LineNumber;

        return result;
    }

    @Override
    public RoboNode visit(CaretExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;

        if (!leftType.equals("num") || !rightType.equals("num")) {
            this.error(left.LineNumber, "Cannot use power-operator on type " + leftType + "and type " + rightType + ", as they are not compatible for power-operation");
            return null;
        } else {
            node.Type = new TypeNode();
            node.Type.Type = "num";
        }

        return node;
    }

    @Override
    public RoboNode visit(TypeNode node) {
        return node;
    }

    @Override
    public RoboNode visit(VariableDeclNode node) {
        var value = visit(node.Value);
        if (value == null) {
            return null;
        } else if (!node.Type.Type.equals(value.Type.Type)) {
            this.error(node.LineNumber, "Cannot assign type " + value.Type.Type + "to a variable with type " + node.Type.Type);
            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(FormalParamNode node) {
        return null;
    }

    @Override
    public RoboNode visit(FunctionDeclNode node) {
        return null;
    }

    @Override
    public RoboNode visit(BlockNode node) {
        return null;
    }

    @Override
    public RoboNode visit(StringExprNode node) {
        return node;
    }

    @Override
    public RoboNode visit(AssignmentNode node) {
        visit(node.Value);

        return node;
    }

    @Override
    public RoboNode visit(DecrementOperatorNode node) {
        return null;
    }

    @Override
    public RoboNode visit(IncrementOperatorNode node) {
        return null;
    }

    @Override
    public RoboNode visit(ListDeclNode node) {
        return null;
    }

    @Override
    public RoboNode visit(AndExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(OrExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(EqualExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(NotEqualExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(GreatEqualExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(GreaterExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(LessEqualExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(LessExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(BoolValueNode node) {
        node.Type = new TypeNode();
        node.Type.Type = "bool";

        return node;
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


    private void error(int lineNumber, String err) {
        AST.errors.add("[Line " + lineNumber + "] " + err + "\n");
    }
}
