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

import java.util.Optional;
import java.util.Stack;
import java.util.function.Function;

public class ContextualAnalysis extends AstVisitor<RoboNode> {
    private Stack<FunctionSymbolTableNode> currentFunction = new Stack<FunctionSymbolTableNode>();

    @Override
    public RoboNode visit(DecrementOperatorExprNode node) {
        var s = this.GetVariable(node.Id);

        if (s.isEmpty()) {
            this.error("Variable " + node.Id.Id + " is not defined");
        }

        return null;
    }

    @Override
    public RoboNode visit(AdditionExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(DivisionExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(DigitExprNode node) { return null; }

    @Override
    public RoboNode visit(ModuloExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(MultiplicationExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(SubtractionExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(IdentifierNode node) {
        var s = this.GetVariable(node);

        if (s.isEmpty()) {
            this.error("Variable " + node.Id + " is not defined");
        }

        return null;
    }

    @Override
    public RoboNode visit(CaretExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(TypeNode node) {
        return null;
    }

    @Override
    public RoboNode visit(VariableDeclNode node) {
        var s = this.GetVariable(node.Id);

        if (s.isPresent()) {
            this.error("Variable " + node.Id + " is already defined");
        } else if (!currentFunction.empty() && currentFunction.peek() != null) {
            var varNode = new VariableSymbolTableNode();
            varNode.Id = node.Id.Id;
            varNode.Type = node.Type.Type;
            currentFunction.peek().addLocalVariableDeclaration(varNode);
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
        if (!this.currentFunction.empty()) {
            // should never ever happen, as this is bound through the CFG.
            this.error("Cannot define a formal parameter when not inside a function");
            return null;
        }

        var variable = new VariableSymbolTableNode();
        variable.Type = node.Type.Type;
        variable.Id = node.Id.Id;
        this.currentFunction.peek().addLocalVariableDeclaration(variable);

        return null;
    }

    @Override
    public RoboNode visit(FunctionDeclNode node) {
        var function = this.GetFunction(node.Id);
        if (function.isPresent()) {
            // function is already declared
            this.error("[Line " + node.LineNumber + "] A func with name " + node.Id.Id + " is already declared");
            return null;
        }

        var functionNode = new FunctionSymbolTableNode();
        functionNode.Id = node.Id.Id;
        functionNode.Type = node.Type.Type;

        // push parameters onto function for later use
        for (var param : node.Params) {
            var paramNode = new VariableSymbolTableNode();
            paramNode.Id = param.Id.Id;
            paramNode.Type = param.Type.Type;

            functionNode.addParam(paramNode);
        }

        AST.symbolTable.PutFunction(functionNode);

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
        var variable = this.GetVariable(node.Id);

        if (variable.isEmpty()) {
            this.error("Variable " + node.Id + " is not defined");
        }

        visit(node.Value);

        return null;
    }

    @Override
    public RoboNode visit(DecrementOperatorNode node) {
        visit(node.Id);

        return null;
    }

    @Override
    public RoboNode visit(IncrementOperatorNode node) {
        visit(node.Id);

        return null;
    }

    @Override
    public RoboNode visit(ListDeclNode node) {
        return null;
    }

    @Override
    public RoboNode visit(AndExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(OrExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(EqualExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(NotEqualExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(GreatEqualExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(GreaterExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(LessEqualExprNode node) {
        visit(node.Left);
        visit(node.Right);

        return null;
    }

    @Override
    public RoboNode visit(LessExprNode node) {
        visit(node.Left);
        visit(node.Right);

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

    private Optional<VariableSymbolTableNode> GetVariable(IdentifierNode variableId) {
        // check global scope
        var result = AST.symbolTable.GetVariables()
                .stream()
                .filter((variable) -> variable.Id.equals(variableId.Id))
                .findFirst();

        if (result.isPresent()) {
            return result;
        }

        //  check function scope
        if (!this.currentFunction.empty() && this.currentFunction.peek() != null) {
            result = currentFunction.peek().getLocalVariables()
                    .stream()
                    .filter((variable) -> variable.Id.equals(variableId.Id))
                    .findFirst();
        }

        return result;
    }

    private Optional<FunctionSymbolTableNode> GetFunction(IdentifierNode functionId) {
        return AST.symbolTable.Getfunctions()
                .stream()
                .filter((function) -> function.Id.equals(functionId.Id))
                .findFirst();
    }
}
