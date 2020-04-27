package ContexualAnalysis;

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

public class ContextualAnalysis extends AstVisitor<RoboNode> {
    private FunctionSymbolTableNode currentFunction;


    @Override
    public RoboNode visit(AdditionExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(DivisionExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(DigitExprNode node) {
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
        var s = AST.symbolTable.GetVariables()
                .stream()
                .filter((variable) -> variable.Id.equals(node.Id))
                .findFirst();

        if (s.isEmpty()) {
            this.error("Variable " + node.Id + " is not defined");
        }

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
        var s = AST.symbolTable.GetVariables()
                .stream()
                .filter((variable) -> variable.Id.equals(node.Id.Id))
                .findFirst();

        if (s.isPresent()) {
            this.error("Variable " + node.Id + " is already defined");
        } else if (currentFunction != null) {
            var varNode = new VariableSymbolTableNode();
            varNode.Id = node.Id.Id;
            varNode.Type = node.Type.Type;
            currentFunction.addLocalVarDeclaration(varNode);
        } else {
            var varNode = new VariableSymbolTableNode();
            varNode.Id = node.Id.Id;
            varNode.Type = node.Type.Type;

            AST.symbolTable.PutVariable(varNode);
        }

        visit(node.Value);

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

    private void error(String err) {
        AST.errors.add(err + "\n");
    }
}
