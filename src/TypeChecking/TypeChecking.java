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

public class TypeChecking extends AstVisitor<RoboNode> {
    @Override
    public RoboNode visit(AdditionExprNode node) {
        var left = visit(node.Left);
        // num y = 5 + x
        // (x ^5) + 5
        // left = string | int
        // right = int | string
        // result = string => string + int

        // left = int
        // right = int
        // result = int => int + int

        // left = bool
        // right = int | string | void | bool
        // error

        // left = int | string | void | bool
        // right = bool
        // error

        return null;
    }

    @Override
    public RoboNode visit(DivisionExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(DigitExprNode node) {
        // 1,2,3,4,5,6,7,8,9... tal
        return null;
    }

    @Override
    public RoboNode visit(ModuloExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(MultiplicationExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(SubtractionExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(IdentifierNode node) {
        return null;
    }

    @Override
    public RoboNode visit(CaretExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(TypeNode node) {
        return null;
    }

    @Override
    public RoboNode visit(VariableDeclNode node) {
        return null;
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
        return null;
    }

    @Override
    public RoboNode visit(AssignmentNode node) {
        return null;
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


    private void error(int lineNumber, String err) {
        AST.errors.add("[Line " + lineNumber + "] " + err + "\n");
    }
}
