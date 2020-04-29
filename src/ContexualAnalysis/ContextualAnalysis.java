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
import ContexualAnalysis.Loops.DoWhileLoopSymboleTableNode;
import ContexualAnalysis.Loops.WhileLoopSymbolTableNode;

import java.util.Stack;

public class ContextualAnalysis extends AstVisitor<RoboNode> {
    private Stack<MethodSymbolTableNode> currentFunction = new Stack<MethodSymbolTableNode>();
    private StrategySymbolTableNode currentStrategy;

    @Override
    public RoboNode visit(DecrementOperatorExprNode node) {
        var s = this.GetVariable(node.Id);

        if (s == null) {
            this.error(node.LineNumber ,"Variable " + node.Id.Id + " is not defined");
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

        if (s == null) {
            this.error(node.LineNumber, "Variable " + node.Id + " is not defined");
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

        if (s != null) {
            this.error(node.LineNumber,"Variable " + node.Id.Id + " is already defined");
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
            this.error(node.LineNumber,"Cannot define a formal parameter when not inside a function");
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

        this.currentFunction.push(functionNode);

        visit(node.block);

        this.currentFunction.pop();

        return null;
    }

    @Override
    public RoboNode visit(BlockNode node) {
        for (var item : node.statements) {
            visit(item);
        }

        return null;
    }

    @Override
    public RoboNode visit(StringExprNode node) {
        return null;
    }

    @Override
    public RoboNode visit(AssignmentNode node) {
        var variable = this.GetVariable(node.Id);

        if (variable == null) {
            this.error(node.LineNumber,"Variable " + node.Id.Id + " is not defined");
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
        var variable = this.GetVariable(node.Id);
        if (variable != null) {
            this.error(node.LineNumber,"Variable " + node.Id.Id + " is already defined");
        }

        for (var item : node.nodes) {
            visit(item);
        }

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
        // what about ChangeStrategy(" strategy name ")

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

        this.currentFunction.push(behavior);
        visit(node.Block);
        this.currentFunction.pop();

        this.currentStrategy.addBehavior(behavior);

        return null;
    }

    @Override
    public RoboNode visit(EventNode node) {
        var event = this.GetEvent(node.Id);
        if (event != null) {
            this.error(node.LineNumber, "The event " + node.Id.Id + " is already declared");
            return null;
        }

        event = new EventSymbolTableNode();
        event.Id = node.Id.Id;

        this.currentFunction.push(event);
        visit(node.Block);
        this.currentFunction.pop();

        AST.symbolTable.PutEvent(event);

        return null;
    }

    @Override
    public RoboNode visit(FunctionCallNode node) {
        var function = this.GetFunction(node.Method);
        if (function == null) {
            this.error(node.LineNumber, "The function " + node.Method.Id + " is not declared");
            return null;
        }

        if (node.Params.size() != function.getNumberOfParams()) {
            this.error(node.LineNumber, "The function " + node.Method.Id + " uses " + function.getNumberOfParams() + " parameters, but " + node.Params.size() + " was given");
            return null;
        }

        return null;
    }

    @Override
    public RoboNode visit(ParamNode node) {
        return null;
    }

    // todo
    @Override
    public RoboNode visit(RoboCodeMethodNode node) {
        return null;
    }

    @Override
    public RoboNode visit(DictionaryDeclNode node) {
        var variable = this.GetVariable(node.Id);
        if (variable != null) {
            this.error(node.LineNumber,"Variable " + node.Id.Id + " is already defined");
        }

        for (var item : node.Nodes) {
            visit(item);
        }

        return null;
    }

    @Override
    public RoboNode visit(DictionaryValueNode node) {
        visit(node.Value);
        visit(node.Key);
        return null;
    }

    @Override
    public RoboNode visit(ForLoopNode node) {
        return null;
    }

    @Override
    public RoboNode visit(DoWhileLoopNode node) {
        var loop = new DoWhileLoopSymboleTableNode();
        this.currentFunction.push(loop);
        visit(node.Block);
        this.currentFunction.pop();

        visit(node.Condition);

        return null;
    }

    @Override
    public RoboNode visit(WhileLoopNode node) {
        var loop = new WhileLoopSymbolTableNode();
        visit(node.Condition);
        this.currentFunction.push(loop);
        visit(node.Block);
        this.currentFunction.pop();

        return null;
    }

    @Override
    public RoboNode visit(FunctionCallExprNode node) {
        var function = this.GetFunction(node.Method);
        if (function == null) {
            this.error(node.LineNumber, "The function " + node.Method.Id + " is not declared");
            return null;
        }

        if (node.Params.size() != function.getNumberOfParams()) {
            this.error(node.LineNumber, "The function " + node.Method.Id + " uses " + function.getNumberOfParams() + " parameters, but " + node.Params.size() + " was given");
            return null;
        }

        return null;
    }

    @Override
    public RoboNode visit(RoboCodeMethodExprNode node) {

        return null;
    }

    @Override
    public RoboNode visit(ReturnNode node) {
        visit(node.Value);
        return null;
    }

    private void error(int lineNumber, String err) {
        AST.errors.add("[Line " + lineNumber + "] " +err + "\n");
    }

    private VariableSymbolTableNode GetVariable(IdentifierNode variableId) {
        // check global scope
        var result = AST.symbolTable.GetVariable(variableId.Id);

        if (result != null) {
            return result;
        }

        //  check function scope
        if (!this.currentFunction.empty() && this.currentFunction.peek() != null) {
            result = currentFunction.peek().getLocalVariable(variableId.Id);
        }

        return result;
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
