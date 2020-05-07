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
import ContexualAnalysis.*;

import java.util.*;

public class TypeChecking extends AstVisitor<RoboNode> {
    private StrategySymbolTableNode currentStrategy;

    @Override
    public RoboNode visit(AdditionExprNode node) {
        HashSet<String> additionTypes = new HashSet<String>(Arrays.asList("num", "text"));
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;
        if (isIdentifierDictionaryOrList(left) || isIdentifierDictionaryOrList(right)) {
            this.error(left.LineNumber, "Only num and text types are suitable for addition");
        } else if (leftType.equals("num") && rightType.equals("num")) {
            node.Type = new TypeNode();
            node.Type.Type = "num";
        } else if ((leftType.equals("text") || rightType.equals("text")) && additionTypes.contains(leftType) && additionTypes.contains(rightType)) {
            node.Type = new TypeNode();
            node.Type.Type = "text";
        } else if (!additionTypes.contains(leftType) || !additionTypes.contains(rightType)) {
            this.error(left.LineNumber, "Cannot add type " + leftType + " and type " + rightType + " together, as they are not compatible for addition");
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
        if (isIdentifierDictionaryOrList(left) || isIdentifierDictionaryOrList(right)) {
            this.error(left.LineNumber, "Only num types are suitable for division");
        } else if (!leftType.equals("num") || !rightType.equals("num")) {
            this.error(left.LineNumber, "Cannot divide type " + leftType + " and type " + rightType + " together, as they are not compatible for division");
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

        if (isIdentifierDictionaryOrList(left) || isIdentifierDictionaryOrList(right)) {
            this.error(left.LineNumber, "Only num types are suitable for modulo");
        } else if (!leftType.equals("num") || !rightType.equals("num")) {
            this.error(left.LineNumber, "Cannot use modulo on type " + leftType + " and type " + rightType + " together, as they are not compatible for modulo");
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

        if (isIdentifierDictionaryOrList(left) || isIdentifierDictionaryOrList(right)) {
            this.error(left.LineNumber, "Only num types are suitable for multiplication");
        } else if (!leftType.equals("num") || !rightType.equals("num")) {
            this.error(left.LineNumber, "Cannot multiply on type " + leftType + " and type " + rightType + " together, as they are not compatible for multiplication");
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

        if (isIdentifierDictionaryOrList(left) || isIdentifierDictionaryOrList(right)) {
            this.error(left.LineNumber, "Only num types are suitable for subtraction");
        } else if (!leftType.equals("num") || !rightType.equals("num")) {
            this.error(left.LineNumber, "Cannot subract type " + leftType + " and type " + rightType + ", as they are not compatible for subtraction");
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

        IdentifierNode result = new IdentifierNode();
        result.Id = variable.Id;
        if (variable instanceof ListVariableSymbolTableNode) {
            result = new ListIdentifierNode();
        } else if (variable instanceof DictionaryVariableSymbolTableNode) {
            result = new DictionaryIdentifierNode();
        }

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
        if (isIdentifierDictionaryOrList(left) || isIdentifierDictionaryOrList(right)) {
            this.error(left.LineNumber, "Only num types are suitable for power-operations");
        } else if (!leftType.equals("num") || !rightType.equals("num")) {
            this.error(left.LineNumber, "Cannot use power-operator on type " + leftType + " and type " + rightType + ", as they are not compatible for power-operation");
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
            this.error(node.LineNumber, "Cannot assign type " + value.Type.Type + " to a variable with type " + node.Type.Type);
            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(FormalParamNode node) {
        return node;
    }

    @Override
    public RoboNode visit(FunctionDeclNode node) {
        var function = this.GetFunction(node.Id);
        AST.symbolTable.EnterFunction(function);

        visit(node.block);

        AST.symbolTable.ExitFunction();
        return node;
    }

    @Override
    public RoboNode visit(BlockNode node) {
        boolean hasSeenReturn = false;

        for (var stmt : node.statements) {
            if (stmt instanceof ReturnNode && !hasSeenReturn) {
                hasSeenReturn = true;
                visit(stmt);
            } else if (hasSeenReturn) {
                // error
                error(stmt.LineNumber, "Cannot perform any statements after a return value");
            } else {
                // not return, has not seen return
                visit(stmt);
            }
        }

        return node;
    }

    @Override
    public RoboNode visit(StringExprNode node) {
        return node;
    }

    @Override
    public RoboNode visit(AssignmentNode node) {
        var value = visit(node.Value);
        var variable = AST.symbolTable.GetVariable(node.Id.Id);
        if (variable instanceof ListVariableSymbolTableNode || variable instanceof DictionaryVariableSymbolTableNode) {
            this.error(value.LineNumber, "Only primitive types are suitable for assignment");
            return null;
        } else if (value == null || value.Type == null) {
            return null;
        } else if (!variable.Type.equals(value.Type.Type)) {
            this.error(node.LineNumber, "Cannot assign type " + value.Type.Type + " to a variable with type " + variable.Type);
            return null;
        }
        return node;
    }

    @Override
    public RoboNode visit(DecrementOperatorNode node) {
        var variable = AST.symbolTable.GetVariable(node.Id.Id);
        if (!variable.Type.equals("num")) {
            this.error(node.LineNumber, "Cannot use decrement-operator on type " + variable.Type + ". It must only be used on type num.");
            return null;
        }

        node.Type = new TypeNode();
        node.Type.Type = "num";
        return node;
    }

    @Override
    public RoboNode visit(IncrementOperatorNode node) {
        var variable = AST.symbolTable.GetVariable(node.Id.Id);
        if (!variable.Type.equals("num")) {
            this.error(node.LineNumber, "Cannot use increment-operator on type " + variable.Type + ". It must only be used on type num.");
            return null;
        }

        node.Type = new TypeNode();
        node.Type.Type = "num";
        return node;
    }

    @Override
    public RoboNode visit(ListDeclNode node) {
        return null;
    }

    @Override
    public RoboNode visit(AndExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;
        if (leftType.equals("bool") && rightType.equals("bool")) {
            node.Type = new TypeNode();
            node.Type.Type = "bool";
        }  else {
            this.error(left.LineNumber, "Cannot perform and-operator on type " + leftType + " and type " + rightType + ", as they are not compatible for an and-operation");
            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(OrExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;
        if (leftType.equals("bool") && rightType.equals("bool")) {
            node.Type = new TypeNode();
            node.Type.Type = "bool";
        }  else {
            this.error(left.LineNumber, "Cannot perform or-operator on type " + leftType + " and type " + rightType + ", as they are not compatible for an or-operation");
            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(EqualExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;
        if (leftType.equals(rightType)) {
            node.Type = new TypeNode();
            node.Type.Type = "bool";
        }  else {
            this.error(left.LineNumber, "Cannot perform equals-operator on type " + leftType + " and type " + rightType + ", as they are not compatible for an equals-operation");
            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(NotEqualExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;
        if (leftType.equals(rightType)) {
            node.Type = new TypeNode();
            node.Type.Type = "bool";
        }  else {
            this.error(left.LineNumber, "Cannot perform not-equal-operator on type " + leftType + " and type " + rightType + ", as they are not compatible for an not-equal-operation");
            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(GreatEqualExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;

        if (leftType.equals("num") && rightType.equals("num")) {
            node.Type = new TypeNode();
            node.Type.Type = "bool";
        }  else {
            this.error(left.LineNumber, "Cannot perform greater-equal-operator on type " + leftType + " and type " + rightType + ", as they are not compatible for an greater-equal-operation");
            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(GreaterExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;

        if (leftType.equals("num") && rightType.equals("num")) {
            node.Type = new TypeNode();
            node.Type.Type = "bool";
        }  else {
            this.error(left.LineNumber, "Cannot perform greater-operator on type " + leftType + " and type " + rightType + ", as they are not compatible for an greater-operation");
            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(LessEqualExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;

        if (leftType.equals("num") && rightType.equals("num")) {
            node.Type = new TypeNode();
            node.Type.Type = "bool";
        }  else {
            this.error(left.LineNumber, "Cannot perform less-equal-operator on type " + leftType + " and type " + rightType + ", as they are not compatible for an less-equal-operation");
            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(LessExprNode node) {
        var left = visit(node.Left);
        var right = visit(node.Right);

        var leftType = left.Type.Type;
        var rightType = right.Type.Type;

        if (leftType.equals("num") && rightType.equals("num")) {
            node.Type = new TypeNode();
            node.Type.Type = "bool";
        }  else {
            this.error(left.LineNumber, "Cannot perform less-operator on type " + leftType + " and type " + rightType + ", as they are not compatible for an less-operation");
            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(BoolValueNode node) {
        node.Type = new TypeNode();
        node.Type.Type = "bool";

        return node;
    }

    @Override
    public RoboNode visit(StrategyNode node) {
        this.currentStrategy = this.GetStrategy(node.Id);

        for (var behavior : node.behaviorNodes) {
            visit(behavior);
        }

        return node;
    }

    @Override
    public RoboNode visit(BehaviorNode node) {
        var behavior = this.GetBehavior(node.Id);
        AST.symbolTable.EnterFunction(behavior);

        visit(node.Block);

        AST.symbolTable.ExitFunction();
        return node;
    }

    @Override
    public RoboNode visit(EventNode node) {
        var event = this.GetEvent(node.Id);
        AST.symbolTable.EnterFunction(event);

        visit(node.Block);

        AST.symbolTable.ExitFunction();
        return node;
    }

    @Override
    public RoboNode visit(FunctionCallNode node) {
        var function = this.GetFunction(node.Method);
        var formalParams = new ArrayList<VariableSymbolTableNode>(function.getParams().values());

        for (int i = 0; i < formalParams.size(); i++) {
            var paramType = visit(node.Params.get(i));
            if (!formalParams.get(i).Type.equals(paramType.Type.Type)) {
                this.error(node.LineNumber, node.Method.Id + "() Param #" + (i + 1) + " is not of type " + formalParams.get(i).Type);
            }
        }

        node.Type = new TypeNode();
        node.Type.Type = function.Type;

        return node;
    }

    @Override
    public RoboNode visit(ParamNode node) {
        node.Value = visit(node.Value);
        node.Type = node.Value.Type;
        return node;
    }

    @Override
    public RoboNode visit(RoboCodeMethodNode node) {
        var roboMethod = AST.symbolTable.getRobocodeRuntimeMethod(node.Method.Method.Id);
        var formalParams = new ArrayList<VariableSymbolTableNode>(roboMethod.getParams().values());
        Collections.reverse(formalParams);

        for (int i = 0; i < formalParams.size(); i++) {
            var paramType = visit(node.Method.Params.get(i));
            if (!formalParams.get(i).Type.equals(paramType.Type.Type)) {
                this.error(node.LineNumber, node.Method.Method.Id + "() Param #" + (i + 1) + " is not of type " + formalParams.get(i).Type);
            }
        }

        node.Type = new TypeNode();
        node.Type.Type = roboMethod.Type;

        return node;
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
        var id = new IdentifierNode();
        id.Id = node.LoopId;
        var loop = this.GetFunction(id);
        AST.symbolTable.EnterFunction(loop);

        visit(node.Init);

        var conditionNode = visit(node.Condition);

        if (!conditionNode.Type.Type.equals("bool")) {
            this.error(conditionNode.LineNumber, "The condition-expression of a for-loop must be of type bool");
        }
        var incrementNode = visit(node.Increment);
        if (!(incrementNode instanceof DecrementOperatorExprNode || incrementNode instanceof IncrementOperatorExprNode)) {
            this.error(conditionNode.LineNumber, "The increment-expression of a for-loop must be either increment or decrement (i-- or i++)");
        }

        visit(node.Block);

        AST.symbolTable.ExitFunction();
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
        var function = this.GetFunction(node.Method);
        var formalParams = new ArrayList<VariableSymbolTableNode>(function.getParams().values());

        for (int i = 0; i < formalParams.size(); i++) {
            var paramType = visit(node.Params.get(i));
            if (!formalParams.get(i).Type.equals(paramType.Type.Type)) {
                this.error(node.LineNumber, node.Method.Id + "() Param #" + i + " is not of type " + formalParams.get(i).Type);
            }
        }

        node.Type = new TypeNode();
        node.Type.Type = function.Type;

        return node;
    }

    @Override
    public RoboNode visit(RoboCodeMethodExprNode node) {
        var roboMethod = AST.symbolTable.getRobocodeRuntimeMethod(node.Method.Method.Id);
        var formalParams = new ArrayList<VariableSymbolTableNode>(roboMethod.getParams().values());
        Collections.reverse(formalParams);

        for (int i = 0; i < formalParams.size(); i++) {
            var paramType = visit(node.Method.Params.get(i));
            if (!formalParams.get(i).Type.equals(paramType.Type.Type)) {
                this.error(node.LineNumber, node.Method.Method.Id + "() Param #" + i + " is not of type " + formalParams.get(i).Type);
            }
        }

        node.Type = new TypeNode();
        node.Type.Type = roboMethod.Type;

        return node;
    }

    @Override
    public RoboNode visit(ReturnNode node) {
        return visit(node.Value);
    }

    @Override
    public RoboNode visit(DecrementOperatorExprNode node) {
        var variable = AST.symbolTable.GetVariable(node.Id.Id);
        if (!variable.Type.equals("num")) {
            this.error(node.LineNumber, "Cannot use decrement-operator on type " + variable.Type + ". It must only be used on type num.");
            return null;
        }

        node.Type = new TypeNode();
        node.Type.Type = "num";

        return node;
    }

    @Override
    public RoboNode visit(IncrementOperatorExprNode node) {
        var variable = AST.symbolTable.GetVariable(node.Id.Id);
        if (!variable.Type.equals("num")) {
            this.error(node.LineNumber, "Cannot use increment-operator on type " + variable.Type + ". It must only be used on type num.");
            return null;
        }

        node.Type = new TypeNode();
        node.Type.Type = "num";
        return node;
    }

    @Override
    public RoboNode visit(ParensVariableNode node) {
        return visit(node.value);
    }

    @Override
    public RoboNode visit(NotExprNode node) {
        var result = visit(node.Value);
        if (result == null) {
            return null;
        } else if (!result.Type.Type.equals("bool")) {
            this.error(node.LineNumber, "Cannot perform not-operation on type " + result.Type.Type + ". not-operations must be on type bool");
            return null;
        }

        node.Type = new TypeNode();
        node.Type.Type = "bool";
        return node;
    }

    @Override
    public RoboNode visit(DotOperationNode node) {
        var variable = AST.symbolTable.GetVariable(node.Id.Id);
        if (variable instanceof ListVariableSymbolTableNode) {
            if (!this.checkDotOperationExists("list", node)) {
                return null;
            }

            for (int i = 0; i < node.Method.Params.size(); i++) {
                var paramType = visit(node.Method.Params.get(i));
                if (!paramType.Type.Type.equals(variable.Type)) {
                    this.error(node.LineNumber, node.Method.Method.Id + "() Param #" + (i + 1) + " is not of type " + variable.Type);
                }
            }

        } else if (variable instanceof DictionaryVariableSymbolTableNode) {
            if (!this.checkDotOperationExists("dictionary", node)) {
                return null;
            }
            var dicVar = (DictionaryVariableSymbolTableNode) variable;

            for (int i = 0; i < node.Method.Params.size(); i++) {
                var paramType = visit(node.Method.Params.get(i));
                if (i % 2 == 0 && !paramType.Type.Type.equals(dicVar.Key)) {
                    this.error(node.LineNumber, node.Method.Method.Id + "() Param #" + (i + 1) + " is not of type " + dicVar.Key);
                } else if (i % 2 == 1 && !paramType.Type.Type.equals(dicVar.Value)){
                    this.error(node.LineNumber, node.Method.Method.Id + "() Param #" + (i + 1) + " is not of type " + dicVar.Value);
                }
            }
        } else if (variable.Type.equals("ScannedRobotEvent")) {
            if (!this.checkDotOperationExists("ScannedRobotEvent", node)) {
                return null;
            }

            var functionCallParams = node.Method.Params;
            var formalParamsMap = AST.symbolTable.getDotOperationMethod("ScannedRobotEvent", node.Method.Method.Id, node.Method.Params.size())
                    .getParams()
                    .values();

            var formalParamsArr = formalParamsMap.toArray(new VariableSymbolTableNode[formalParamsMap.size()]);

            for (int i = 0; i < functionCallParams.size(); i++) {
                if (!functionCallParams.get(i).Value.Type.Type.equals(formalParamsArr[i].Type)) {
                    this.error(node.LineNumber, node.Method.Method.Id + "() Param #" + (i + 1) + " is not of type " + formalParamsArr[i].Type);
                }
            }

            return null;
        }

        return node;
    }

    @Override
    public RoboNode visit(DotOperationExprNode node) {
        var variable = AST.symbolTable.GetVariable(node.Id.Id);
        if (variable instanceof ListVariableSymbolTableNode) {
            if (!this.checkDotOperationExists("list", node)) {
                return null;
            }

            for (int i = 0; i < node.Method.Params.size(); i++) {
                var paramType = visit(node.Method.Params.get(i));
                if (!paramType.Type.Type.equals(variable.Type)) {
                    this.error(node.LineNumber, node.Method.Method.Id + "() Param #" + (i + 1) + " is not of type " + variable.Type);
                }
            }

            node.Type = new TypeNode();
            node.Type.Type = this.getDotOperation("list", node).Type;

        } else if (variable instanceof DictionaryVariableSymbolTableNode) {
            if (!this.checkDotOperationExists("dictionary", node)) {
                return null;
            }
            var dicVar = (DictionaryVariableSymbolTableNode) variable;

            for (int i = 0; i < node.Method.Params.size(); i++) {
                var paramType = visit(node.Method.Params.get(i));
                if (i % 2 == 0 && !paramType.Type.Type.equals(dicVar.Key)) {
                    this.error(node.LineNumber, node.Method.Method.Id + "() Param #" + (i + 1) + " is not of type " + dicVar.Key);
                } else if (i % 2 == 1 && !paramType.Type.Type.equals(dicVar.Value)){
                    this.error(node.LineNumber, node.Method.Method.Id + "() Param #" + (i + 1) + " is not of type " + dicVar.Value);
                }
            }

            node.Type = new TypeNode();
            node.Type.Type = this.getDotOperation("dictionary", node).Type;
        } else if (variable.Type.equals("ScannedRobotEvent")) {
            if (!this.checkDotOperationExists("ScannedRobotEvent", node)) {
                return null;
            }

            var functionCallParams = node.Method.Params;
            var formalParamsMap = AST.symbolTable.getDotOperationMethod("ScannedRobotEvent", node.Method.Method.Id, node.Method.Params.size())
                    .getParams()
                    .values();

            var formalParamsArr = formalParamsMap.toArray(new VariableSymbolTableNode[formalParamsMap.size()]);

            for (int i = 0; i < functionCallParams.size(); i++) {
                if (!functionCallParams.get(i).Value.Type.Type.equals(formalParamsArr[i].Type)) {
                    this.error(node.LineNumber, node.Method.Method.Id + "() Param #" + (i + 1) + " is not of type " + formalParamsArr[i].Type);
                }
            }

            node.Type = new TypeNode();
            node.Type.Type = this.getDotOperation("ScannedRobotEvent", node).Type;
        }

        return node;
    }

    @Override
    public RoboNode visit(IfNode node) {
        var exprNode = visit(node.expr);
        if (exprNode == null) {
            return null;
        } else if (!exprNode.Type.Type.equals("bool")) {
            this.error(node.LineNumber, "The expression of an if-statement must be of type bool");
        }

        visit(node.block);

        for (var ifElseNode : node.ifElseNodes) {
            var ifElseExprNode = visit(ifElseNode.expr);
            if (ifElseExprNode == null) {
                return null;
            } else if (!ifElseExprNode.Type.Type.equals("bool")) {
                this.error(ifElseNode.LineNumber, "The expression of an if-statement must be of type bool");
            }

            visit(ifElseNode.block);
        }

        if (node.elseBlock != null) {
            visit(node.elseBlock.block);
        }

        return node;
    }

    private boolean checkDotOperationExists(String type, DotOperationNode node) {
        var operationExistsonType = AST.symbolTable.getDotOperationMethod(type, node.Method.Method.Id) != null;
        var operationExistsOnTypeWithParams = AST.symbolTable.getDotOperationMethod(type, node.Method.Method.Id, node.Method.Params.size()) != null;
        if (!operationExistsonType) {
            // no valid method found
            this.error(node.LineNumber, "A method with name " + node.Method.Method.Id + " on type " + type + " could not be found");
            return false;
        } else if (!operationExistsOnTypeWithParams) {
            // no valid method with number of params found
            this.error(node.LineNumber, "A method with name " + node.Method.Method.Id + " on type " + type + " which takes " + node.Method.Params.size() + " parameters could not be found");
            return false;
        }

        return true;
    }

    private boolean checkDotOperationExists(String type, DotOperationExprNode node) {
        var operationExistsOnType = AST.symbolTable.getDotOperationMethod(type, node.Method.Method.Id) != null;
        var operationExistsOnTypeWithParams = AST.symbolTable.getDotOperationMethod(type, node.Method.Method.Id, node.Method.Params.size()) != null;
        if (!operationExistsOnType) {
            // no valid method found
            this.error(node.LineNumber, "A method with name " + node.Method.Method.Id + " on type " + type + " could not be found");
            return false;
        } else if (!operationExistsOnTypeWithParams) {
            // no valid method with number of params found
            this.error(node.LineNumber, "A method with name " + node.Method.Method.Id + " on type " + type + " which takes " + node.Method.Params.size() + " parameters could not be found");
            return false;
        }

        return true;
    }

    private MethodSymbolTableNode getDotOperation(String type, DotOperationExprNode node) {

        return AST.symbolTable.getDotOperationMethod(type, node.Method.Method.Id, node.Method.Params.size());

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

    private boolean isIdentifierDictionaryOrList(RoboNode node) {
        return node instanceof ListIdentifierNode || node instanceof DictionaryIdentifierNode;
    }
}
