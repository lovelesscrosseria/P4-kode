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
import ContexualAnalysis.Loops.ForLoopSymbolTableNode;
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
        var variable = this.GetVariable(node.Id);
        if (variable == null && !currentFunction.empty() && currentFunction.peek() != null) {
            // variable is not defined and is inside scope
            var varNode = new VariableSymbolTableNode();
            varNode.Id = node.Id.Id;
            varNode.Type = node.Type.Type;
            currentFunction.peek().addLocalVariableDeclaration(varNode);
            AST.symbolTable.PutLocalDeclaration(varNode);
        } else if (variable == null) {
            // variable is not defined and is in global scope
            var varNode = new VariableSymbolTableNode();
            varNode.Id = node.Id.Id;
            varNode.Type = node.Type.Type;

            AST.symbolTable.PutVariable(varNode);
        } else if (!currentFunction.empty() && currentFunction.peek() != null) {
            // variable is declared somwhere and we are inside a scope
            if(AST.symbolTable.IsVariableLocal(variable)) {
                // the variable is declared locally, and we are inside the scope, which is not allowed
                this.error(node.LineNumber,"Variable " + node.Id.Id + " is already defined");
            } else {
                // the variable is not declared locally, and we are inside the scope, which is allowed
                var varNode = new VariableSymbolTableNode();
                varNode.Id = node.Id.Id;
                varNode.Type = node.Type.Type;
                currentFunction.peek().addLocalVariableDeclaration(varNode);
                AST.symbolTable.PutLocalDeclaration(varNode);
            }
        } else {
            // variable is declared in global scope and we are inside the global scope
            this.error(node.LineNumber,"Variable " + node.Id.Id + " is already defined");
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
        if (function == null) {
            // function is already declared
            this.error(node.LineNumber,"A func with name " + node.Id.Id + " is not declared");
            return null;
        }
        AST.symbolTable.EnterScope(function.GetParams());
        this.currentFunction.push(function);

        visit(node.block);

        this.currentFunction.pop();
        AST.symbolTable.ExitScope();

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
        if (variable == null && !currentFunction.empty() && currentFunction.peek() != null) {
            // variable is not defined and is inside scope
            var varNode = new ListVariableSymbolTableNode();
            varNode.Id = node.Id.Id;
            varNode.Type = node.Type.Type;
            currentFunction.peek().addLocalVariableDeclaration(varNode);
            AST.symbolTable.PutLocalDeclaration(varNode);
        } else if (variable == null) {
            // variable is not defined and is in global scope
            var varNode = new ListVariableSymbolTableNode();
            varNode.Id = node.Id.Id;
            varNode.Type = node.Type.Type;

            AST.symbolTable.PutVariable(varNode);
        } else if (!currentFunction.empty() && currentFunction.peek() != null) {
            // variable is declared somwhere and we are inside a scope
            if(AST.symbolTable.IsVariableLocal(variable)) {
                // the variable is declared locally, and we are inside the scope, which is not allowed
                this.error(node.LineNumber,"Variable " + node.Id.Id + " is already defined");
            } else {
                // the variable is not declared locally, and we are inside the scope, which is allowed
                var varNode = new ListVariableSymbolTableNode();
                varNode.Id = node.Id.Id;
                varNode.Type = node.Type.Type;
                currentFunction.peek().addLocalVariableDeclaration(varNode);
                AST.symbolTable.PutLocalDeclaration(varNode);
            }
        } else {
            // variable is declared in global scope and we are inside the global scope
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
        if (strategy == null) {
            this.error(node.LineNumber, "A strategy with name " + node.Id + " is not defined");
            return null;
        }

        AST.symbolTable.EnterScope();

        this.currentStrategy = strategy;

        for (var behavior : node.behaviorNodes) {
            visit(behavior);
        }

        this.currentStrategy = null;
        AST.symbolTable.ExitScope();
        return null;
    }

    @Override
    public RoboNode visit(BehaviorNode node) {
        var behavior = new BehaviorSymbolTableNode();
        behavior.Id = node.Id.Id;

        if (this.GetBehavior(node.Id) == null) {
            this.error(node.LineNumber, "A behavior with name " + node.Id.Id + " is not defined");
            return null;
        }

        AST.symbolTable.EnterScope(behavior.GetParams());
        this.currentFunction.push(behavior);
        visit(node.Block);
        this.currentFunction.pop();
        AST.symbolTable.ExitScope();

        return null;
    }

    @Override
    public RoboNode visit(EventNode node) {
        var event = this.GetEvent(node.Id);
        if (event == null) {
            this.error(node.LineNumber, "The Event " + node.Id.Id + " is not declared");
            return null;
        }

        AST.symbolTable.EnterScope();
        this.currentFunction.push(event);
        visit(node.Block);
        this.currentFunction.pop();
        AST.symbolTable.ExitScope();

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
        var methodName = node.Method.Method.Id;
        var method = AST.symbolTable.getRobocodeRuntimeMethod(methodName);
        if (method == null) {
            this.error(node.LineNumber, "A robocode method with name " + methodName + " is not defined");
            return null;
        }
        if (method.getNumberOfParams() != node.Method.Params.size()) {
            var error =  String.format("The robocode method with name %s requires %d parameters, but %d was provided", methodName, method.getNumberOfParams(), node.Method.Params.size());
            this.error(node.LineNumber, error);
            return null;
        }

        return null;
    }

    @Override
    public RoboNode visit(DictionaryDeclNode node) {
        var variable = this.GetVariable(node.Id);
        if (variable == null && !currentFunction.empty() && currentFunction.peek() != null) {
            // variable is not defined and is inside scope
            var varNode = new DictionaryVariableSymbolTableNode();
            varNode.Id = node.Id.Id;
            varNode.Key = node.key.Type;
            varNode.Value = node.value.Type;
            currentFunction.peek().addLocalVariableDeclaration(varNode);
            AST.symbolTable.PutLocalDeclaration(varNode);
        } else if (variable == null) {
            // variable is not defined and is in global scope
            var varNode = new DictionaryVariableSymbolTableNode();
            varNode.Id = node.Id.Id;
            varNode.Key = node.key.Type;
            varNode.Value = node.value.Type;

            AST.symbolTable.PutVariable(varNode);
        } else if (!currentFunction.empty() && currentFunction.peek() != null) {
            // variable is declared somwhere and we are inside a scope
            if(AST.symbolTable.IsVariableLocal(variable)) {
                // the variable is declared locally, and we are inside the scope, which is not allowed
                this.error(node.LineNumber,"Variable " + node.Id.Id + " is already defined");
            } else {
                // the variable is not declared locally, and we are inside the scope, which is allowed
                var varNode = new DictionaryVariableSymbolTableNode();
                varNode.Id = node.Id.Id;
                varNode.Key = node.key.Type;
                varNode.Value = node.value.Type;
                currentFunction.peek().addLocalVariableDeclaration(varNode);
                AST.symbolTable.PutLocalDeclaration(varNode);
            }
        } else {
            // variable is declared in global scope and we are inside the global scope
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
        var loop = new ForLoopSymbolTableNode();

        AST.symbolTable.EnterScope();
        this.currentFunction.push(loop);

        visit(node.Init);
        visit(node.Condition);
        visit(node.Block);
        visit(node.Increment);

        this.currentFunction.pop();
        AST.symbolTable.ExitScope();

        return null;
    }

    @Override
    public RoboNode visit(DoWhileLoopNode node) {
        var loop = new DoWhileLoopSymboleTableNode();

        AST.symbolTable.EnterScope();
        this.currentFunction.push(loop);
        visit(node.Block);
        this.currentFunction.pop();
        AST.symbolTable.ExitScope();
        visit(node.Condition);

        return null;
    }

    @Override
    public RoboNode visit(WhileLoopNode node) {
        var loop = new WhileLoopSymbolTableNode();
        visit(node.Condition);
        AST.symbolTable.EnterScope();
        this.currentFunction.push(loop);
        visit(node.Block);
        this.currentFunction.pop();
        AST.symbolTable.ExitScope();

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
        var methodName = node.Method.Method.Id;
        var method = AST.symbolTable.getRobocodeRuntimeMethod(methodName);
        if (method == null) {
            this.error(node.LineNumber, "A robocode method with name " + methodName + " is not defined");
            return null;
        }
        if (method.getNumberOfParams() != node.Method.Params.size()) {
            var error =  String.format("The robocode method with name %s requires %d parameters, but %d was provided", methodName, method.getNumberOfParams(), node.Method.Params.size());
            this.error(node.LineNumber, error);
            return null;
        }

        return null;
    }

    @Override
    public RoboNode visit(ReturnNode node) {
        visit(node.Value);
        return null;
    }

    @Override
    public RoboNode visit(IncrementOperatorExprNode node) {
        visit(node.Id);
        return null;
    }

    @Override
    public RoboNode visit(ParensVariableNode node) {
        visit(node.value);
        return null;
    }

    @Override
    public RoboNode visit(NotExprNode node) {
        visit(node.Value);
        return null;
    }

    @Override
    public RoboNode visit(DotOperationNode node) {
        visit(node.Id);
        var methodId = node.Method.Method.Id;
        var method = AST.symbolTable.getDotOperationMethod(methodId, node.Method.Params.size());

        if (method == null) {
            this.error(node.LineNumber, "No method found for '" + methodId + "' which takes " + node.Method.Params.size() + " parameters");
            return null;
        }

        return null;
    }

    @Override
    public RoboNode visit(DotOperationExprNode node) {
        visit(node.Id);
        var methodId = node.Method.Method.Id;
        var method = AST.symbolTable.getDotOperationMethod(methodId, node.Method.Params.size());

        if (method == null) {
            this.error(node.LineNumber, "No method found for '" + methodId + "' which takes " + node.Method.Params.size() + " parameters");
            return null;
        }

        return null;
    }

    private void error(int lineNumber, String err) {
        AST.errors.add("[Line " + lineNumber + "] " + err + "\n");
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
