package AST;

import java.util.ArrayList;

import AST.Nodes.Bool.BoolExprNode;
import AST.Nodes.Functions.BlockNode;
import AST.Nodes.Functions.FormalParamNode;
import AST.Nodes.Functions.FunctionDeclNode;
import AST.Nodes.Infix.*;
import AST.Nodes.Bool.*;
import AST.Nodes.Functions.*;
import AST.Nodes.Loops.DoWhileLoopNode;
import AST.Nodes.Loops.ForLoopNode;
import AST.Nodes.Loops.WhileLoopNode;
import AST.Nodes.ProgramNode;
import AST.Nodes.RoboNode;
import AST.Nodes.Variables.*;
import GrammarOut.roboBaseVisitor;
import GrammarOut.roboLexer;
import GrammarOut.roboParser;

public class BuildAstVisitor extends roboBaseVisitor<RoboNode> {
    /**
     * Program start
     */
    @Override public AST visitProgram(roboParser.ProgramContext ctx) {
        ArrayList<RoboNode> nodes = new ArrayList<RoboNode>();
        for (var child : ctx.children) {
            var node = visit(child);
            nodes.add(node);
        }

        return new AST(new ProgramNode(nodes));
    }

    @Override
    public RoboNode visitIfStmt(roboParser.IfStmtContext ctx) {
        var ifNode =  new IfNode();
        ifNode.expr = visit(ctx.ifExpr);
        ifNode.block = (BlockNode) visit(ctx.ifBlock);
        ifNode.LineNumber = ctx.start.getLine();

        for (int i = 0; i < ctx.children.size(); i += 3) {
            var item = ctx.children.get(i);

            if (item.getText().equals("else if")) {
                var elseIfNode = new ElseIfNode();
                elseIfNode.expr = visit(ctx.children.get(i +1 ));
                elseIfNode.block = (BlockNode) visit(ctx.children.get(i + 2));
                elseIfNode.LineNumber = ctx.start.getLine();
                ifNode.ifElseNodes.add(elseIfNode);
            } else if (item.getText().equals("else")) {
                var elseNode = new ElseNode();
                elseNode.block = (BlockNode) visit(ctx.children.get(i + 1));
                elseNode.LineNumber = ctx.start.getLine();
                ifNode.elseBlock = elseNode;
                break;
            }
        }

        return ifNode;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitStrategy(roboParser.StrategyContext ctx) {
        var node = new StrategyNode();
        node.LineNumber = ctx.start.getLine();
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.id.getText();

        for (var item : ctx.behavior()) {
            var behavior = visit(item);
            node.behaviorNodes.add((BehaviorNode) behavior);
        }

        return node;

    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitBehavior(roboParser.BehaviorContext ctx) {
        var node = new BehaviorNode();
        node.Id = new IdentifierNode();
        node.LineNumber = ctx.start.getLine();
        node.Id.Id = ctx.id.getText();
        node.Params = GetFormalParams(ctx.formal_params());
        node.Block = (BlockNode) visit(ctx.funcBlock);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitFunction_decl(roboParser.Function_declContext ctx) {
        var node = new FunctionDeclNode();
        node.LineNumber = ctx.start.getLine();
        node.Type = (TypeNode) visit(ctx.funcType);
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.funcId.getText();
        node.Params = GetFormalParams(ctx.formal_params());
        node.block = (BlockNode) visit(ctx.funcBlock);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitEvent_decl(roboParser.Event_declContext ctx) {
        var node = new EventNode();
        node.LineNumber = ctx.start.getLine();
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.id.getText();
        node.Block = (BlockNode) visit(ctx.eventBlock);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public VariableDeclNode visitVariable_decl(roboParser.Variable_declContext ctx) {
        var node = new VariableDeclNode();
        node.LineNumber = ctx.start.getLine();
        node.Type = (TypeNode) visit(ctx.varType);
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.varId.getText();

        node.Value = ctx.value == null ? null : (RoboNode) visit(ctx.value);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitList_decl(roboParser.List_declContext ctx) {
        var node = new ListDeclNode();
        node.LineNumber = ctx.start.getLine();
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.id.getText();
        node.Type = (TypeNode) visit(ctx.listType);

        for (var item : ctx.expr()) {
            node.nodes.add(visit(item));
        }

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitDictionary_decl(roboParser.Dictionary_declContext ctx) {
        var node = new DictionaryDeclNode();
        node.LineNumber = ctx.start.getLine();
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.id.getText();

        node.key = (TypeNode) visit(ctx.dicKey);
        node.value = (TypeNode) visit(ctx.dicValue);

        for (var item : ctx.dictionaryValue()) {
            node.Nodes.add((DictionaryValueNode) visit(item));
        }

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public RoboNode visitDictionaryValue(roboParser.DictionaryValueContext ctx) {
        var node = new DictionaryValueNode();
        node.LineNumber = ctx.start.getLine();
        node.Key = visit(ctx.key);
        node.Value = visit(ctx.value);

        return node;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public RoboNode visitDotOperation(roboParser.DotOperationContext ctx) {
        var node = new DotOperationNode();
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.id.getText();

        node.Method = (FunctionCallNode) visit(ctx.method);
        node.LineNumber = ctx.start.getLine();

        return node;
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitRoboCode_method(roboParser.RoboCode_methodContext ctx) {
        var node = new RoboCodeMethodNode();
        node.LineNumber = ctx.start.getLine();
        node.Method = (FunctionCallNode) visit(ctx.method);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public AssignmentNode visitAssignment(roboParser.AssignmentContext ctx) {
        var node = new AssignmentNode();
        node.LineNumber = ctx.start.getLine();
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.id.getText();

        node.Value = visit(ctx.value);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitStat(roboParser.StatContext ctx) {
        return visit(ctx.getChild(0));
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitFunction_call(roboParser.Function_callContext ctx) {
        var node = new FunctionCallNode();
        node.LineNumber = ctx.start.getLine();
        node.Method = new IdentifierNode();
        node.Method.Id = ctx.id.getText();

        node.Params = GetParams(ctx.funcParams);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitLoop(roboParser.LoopContext ctx) {
        RoboNode returnNode = null;
        switch (ctx.loopType.getText()) {
            case "for":
                var forLoopNode = new ForLoopNode();
                forLoopNode.LineNumber = ctx.start.getLine();
                forLoopNode.Block = (BlockNode) visit(ctx.loopBlock);
                forLoopNode.Condition = visit(ctx.loopCondition);
                forLoopNode.Increment = visit(ctx.loopIncrement);
                forLoopNode.Init = ctx.forLoopAssign == null ? visit(ctx.forLoopVarDec) : visit(ctx.forLoopAssign);

                returnNode = forLoopNode;
                break;
            case "while":;
                var whileLoopNode = new WhileLoopNode();
                whileLoopNode.LineNumber = ctx.start.getLine();
                whileLoopNode.Block = (BlockNode) visit(ctx.loopBlock);
                whileLoopNode.Condition = visit(ctx.loopCondition);

                returnNode = whileLoopNode;
                break;
            case "do":
                var doWhileLoopNode = new DoWhileLoopNode();
                doWhileLoopNode.LineNumber = ctx.start.getLine();
                doWhileLoopNode.Block = (BlockNode) visit(ctx.loopBlock);
                doWhileLoopNode.Condition = visit(ctx.loopCondition);

                returnNode = doWhileLoopNode;
                break;
        }

        return returnNode;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitBlock(roboParser.BlockContext ctx) {
        var node = new BlockNode();
        node.LineNumber = ctx.start.getLine();
        for (var stat : ctx.children) {
            if (stat instanceof roboParser.StatContext) {
                node.statements.add(visit(stat));
            }
        }

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitFormal_params(roboParser.Formal_paramsContext ctx) {
        var node = new FormalParamNode();
        node.LineNumber = ctx.start.getLine();
        node.Type = (TypeNode) visit(ctx.paramType);
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.paramId.getText();

        //return visitChildren(ctx);
        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitParams(roboParser.ParamsContext ctx) {
        var node = new ParamNode();
        node.LineNumber = ctx.start.getLine();
        node.Value = visit(ctx.paramExpr);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public FunctionCallExprNode visitFuncExpr(roboParser.FuncExprContext ctx) {
        var funcCallNode = (FunctionCallNode) visit(ctx.funcCall);
        var node = new FunctionCallExprNode();
        node.LineNumber = ctx.start.getLine();
        node.Params = funcCallNode.Params;
        node.Method = funcCallNode.Method;


        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitTrueExpr(roboParser.TrueExprContext ctx) {
        BoolValueNode node = new BoolValueNode();
        node.LineNumber = ctx.start.getLine();
        node.Value = ctx.value.getText();

        //return visitChildren(ctx);
        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitDecrExpr(roboParser.DecrExprContext ctx) {
        var node = new DecrementOperatorExprNode();
        node.LineNumber = ctx.start.getLine();
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.decrement_operator().ID().getText();

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitIncrExpr(roboParser.IncrExprContext ctx) {
        var node = new IncrementOperatorExprNode();
        node.LineNumber = ctx.start.getLine();
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.increment_operator().ID().getText();

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitCollExpr(roboParser.CollExprContext ctx) {
        var node = new DotOperationExprNode();

        var dotOperationNode = (DotOperationNode) visit(ctx.collectionExpr);
        node.Id = dotOperationNode.Id;
        node.Method = dotOperationNode.Method;
        node.LineNumber = ctx.start.getLine();

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitParensExpr(roboParser.ParensExprContext ctx) {
        var node = new ParensVariableNode();
        node.LineNumber = ctx.start.getLine();
        node.value = visit(ctx.value);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public DigitExprNode visitDigitExpr(roboParser.DigitExprContext ctx) {
        DigitExprNode node = new DigitExprNode();
        node.LineNumber = ctx.start.getLine();
        node.value = Double.parseDouble(ctx.getText());
        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public StringExprNode visitStringExpr(roboParser.StringExprContext ctx) {
        var node = new StringExprNode();
        node.LineNumber = ctx.start.getLine();
        node.value = ctx.value.getText();

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboCodeMethodExprNode visitRoboMethodExpr(roboParser.RoboMethodExprContext ctx) {
        var node = new RoboCodeMethodExprNode();
        node.LineNumber = ctx.start.getLine();
        node.Method = (FunctionCallNode) visit(ctx.roboMethod.method);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public InfixExprNode visitInfixExpr(roboParser.InfixExprContext ctx) {
        InfixExprNode node = new SubtractionExprNode();
        switch (ctx.op.getType())
        {
            case roboLexer.MUL_OP:
                node = new MultiplicationExprNode();
                break;
            case roboLexer.DIV_OP:
                node = new DivisionExprNode();
                break;
            case roboLexer.MOD_OP:
                node = new ModuloExprNode();
                break;
            case roboLexer.ADD_OP:
                node = new AdditionExprNode();
                break;
            case roboLexer.SUB_OP:
                node = new SubtractionExprNode();
                break;
        }

        node.LineNumber = ctx.start.getLine();
        node.Left = visit(ctx.left);
        node.Right = visit(ctx.right);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitNotExpr(roboParser.NotExprContext ctx) {
        var notNode = new NotExprNode();

        notNode.Value = visit(ctx.value);

        return notNode;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public CaretExprNode visitCaretExpr(roboParser.CaretExprContext ctx) {
        var node = new CaretExprNode();
        node.LineNumber = ctx.start.getLine();
        node.Left = visit(ctx.left);
        node.Right = visit(ctx.right);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public BoolValueNode visitFalseExpr(roboParser.FalseExprContext ctx) {
        BoolValueNode node = new BoolValueNode();
        node.LineNumber = ctx.start.getLine();
        node.Value = ctx.value.getText();

        //return visitChildren(ctx);
        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public BoolExprNode visitBoolExpr(roboParser.BoolExprContext ctx) {
        BoolExprNode node;
        switch(ctx.op.getType()){
            case roboLexer.OR_OP:
                node = new OrExprNode();
                break;
            case roboLexer.AND_OP:
                node = new AndExprNode();
                break;
            case roboLexer.EQUAL_OP:
                node = new EqualExprNode();
                break;
            case roboLexer.NOTEQ_OP:
                node = new NotEqualExprNode();
                break;
            case roboLexer.GEQ_OP:
                node = new GreatEqualExprNode();
                break;
            case roboLexer.GREATER_OP:
                node = new GreaterExprNode();
                break;
            case roboLexer.LEQ_OP:
                node = new LessEqualExprNode();
                break;
            case roboLexer.LESS_OP:
                node = new LessExprNode();
                break;
            default:
                throw new Error("Bool expression wrong");
                //throw new Exception("Wrong Operator for a Bool Expression");
        }

        node.LineNumber = ctx.start.getLine();
        node.Left = visit(ctx.left);
        node.Right = visit(ctx.right);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public IdentifierNode visitIdExpr(roboParser.IdExprContext ctx) {
        var node = new IdentifierNode();
        node.LineNumber = ctx.start.getLine();
        node.Id = ctx.id.getText();

        //return visitChildren(ctx);
        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public TypeNode visitType(roboParser.TypeContext ctx) {
        var node = new TypeNode();
        node.LineNumber = ctx.start.getLine();
        node.Type = ctx.getText();

        //return visitChildren(ctx);
        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitReturn_stat(roboParser.Return_statContext ctx) {
        var node = new ReturnNode();
        node.LineNumber = ctx.start.getLine();
        node.Value = visit(ctx.value);

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitDecrement_operator(roboParser.Decrement_operatorContext ctx) {
        var node = new DecrementOperatorNode();
        node.LineNumber = ctx.start.getLine();
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.ID().getText();

        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitIncrement_operator(roboParser.Increment_operatorContext ctx) {
        var node = new IncrementOperatorNode();
        node.LineNumber = ctx.start.getLine();
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.ID().getText();

        return node;
    }


    private ArrayList<ParamNode> GetParams(roboParser.ParamsContext ctx) {
        ArrayList<ParamNode> params = new ArrayList<ParamNode>();
        if (ctx == null) {
            return params;
        }

        var node = new ParamNode();
        node.LineNumber = ctx.start.getLine();
        node.Value = visit(ctx.paramExpr);

        params.add(node);

        if (ctx.params() != null) {
            for (var param : ctx.params()) {
                params.addAll(GetParams(param));
            }
        }

        return params;
    }

    private ArrayList<FormalParamNode> GetFormalParams(roboParser.Formal_paramsContext ctx) {
        ArrayList<FormalParamNode> params = new ArrayList<FormalParamNode>();
        if (ctx == null) {
            return params;
        }
        var node = new FormalParamNode();
        node.Type = (TypeNode) visit(ctx.paramType);
        node.LineNumber = ctx.start.getLine();
        node.Id = new IdentifierNode();
        node.Id.Id = ctx.paramId.getText();

        params.add(node);

        if (ctx.formal_params() != null) {
            for (var param : ctx.formal_params())
            params.addAll(GetFormalParams(param));
        }

        return params;
    }
}
