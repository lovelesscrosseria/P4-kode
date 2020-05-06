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

import java.util.Stack;

public class MethodDeclaration extends AstVisitor<RoboNode> {
    private Stack<MethodSymbolTableNode> currentFunction = new Stack<MethodSymbolTableNode>();
    private StrategySymbolTableNode currentStrategy;

    @Override
    public RoboNode visit(IfNode node) {
        return null;
    }

    @Override
    public RoboNode visit(AdditionExprNode node) {
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
        var function = this.GetFunction(node.Id);
        if (function != null) {
            // function is already declared
            this.error(node.LineNumber,"A func with name " + node.Id.Id + " is already declared");
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

            functionNode.addLocalVariableDeclaration(paramNode);
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
        var strategy = this.GetStrategy(node.Id);
        if (strategy != null) {
            this.error(node.LineNumber, "A strategy with name " + node.Id + " is already defined");
            return null;
        }

        strategy = new StrategySymbolTableNode();
        strategy.Id = node.Id.Id;

        AST.symbolTable.PutStrategy(strategy);

        this.currentStrategy = strategy;

        for (var behavior : node.behaviorNodes) {
            visit(behavior);
        }

        this.currentStrategy = null;

        return null;
    }

    @Override
    public RoboNode visit(BehaviorNode node) {
        var behavior = new BehaviorSymbolTableNode();
        behavior.Id = node.Id.Id;

        if (this.GetBehavior(node.Id) != null) {
            this.error(node.LineNumber, "A behavior with name " + node.Id.Id + " is already defined");
            return null;
        }

        for (var paramNode : node.Params) {
            var param = new VariableSymbolTableNode();
            param.Type = paramNode.Type.Type;
            param.Id = paramNode.Id.Id;
            behavior.addParam(param);
            behavior.addLocalVariableDeclaration(param);
        }

        this.currentStrategy.addBehavior(behavior);

        return null;
    }

    @Override
    public RoboNode visit(EventNode node) {
        var event = this.GetEvent(node.Id);
        if (event != null) {
            this.error(node.LineNumber, "The Event " + node.Id.Id + " is already declared");
            return null;
        }

        event = new EventSymbolTableNode();
        event.Id = node.Id.Id;

        AST.symbolTable.PutEvent(event);

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

    private FunctionSymbolTableNode GetFunction(IdentifierNode functionId) {
        return AST.symbolTable.GetFunction(functionId.Id);
    }

    private EventSymbolTableNode GetEvent(IdentifierNode eventId) {
        return AST.symbolTable.GetEvent(eventId.Id);
    }

    private StrategySymbolTableNode GetStrategy(IdentifierNode strategyId) {
        return AST.symbolTable.GetStrategy(strategyId.Id);
    }

    private BehaviorSymbolTableNode GetBehavior(IdentifierNode behaviorId) {
        return this.currentStrategy.getBehavior(behaviorId.Id);
    }
}
