package AST;

import java.util.ArrayList;

import AST.Nodes.Infix.*;
import AST.Nodes.RoboNode;
import AST.Nodes.Variables.IdentifierNode;
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

        return new AST(nodes);
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitStrategy(roboParser.StrategyContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitBehavior(roboParser.BehaviorContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitFunction_decl(roboParser.Function_declContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitEvent_decl(roboParser.Event_declContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitVariable_decl(roboParser.Variable_declContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitList_decl(roboParser.List_declContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitDictionary_decl(roboParser.Dictionary_declContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitCollection_expr(roboParser.Collection_exprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitCollection_statement(roboParser.Collection_statementContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitRoboCode_method(roboParser.RoboCode_methodContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitAssignment(roboParser.AssignmentContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitStat(roboParser.StatContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitFunction_call(roboParser.Function_callContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitLoop(roboParser.LoopContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitBlock(roboParser.BlockContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitFormal_params(roboParser.Formal_paramsContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitParams(roboParser.ParamsContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitFuncExpr(roboParser.FuncExprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitTrueExpr(roboParser.TrueExprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitDecrExpr(roboParser.DecrExprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitCollExpr(roboParser.CollExprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitIncrExpr(roboParser.IncrExprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitParensExpr(roboParser.ParensExprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public DigitExprNode visitDigitExpr(roboParser.DigitExprContext ctx) {
        DigitExprNode node = new DigitExprNode();
        node.value = Double.parseDouble(ctx.getText());
        return node;
    }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitStringExpr(roboParser.StringExprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitRoboMethodExpr(roboParser.RoboMethodExprContext ctx) { return visitChildren(ctx); }
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
    @Override public RoboNode visitNotExpr(roboParser.NotExprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public CaretExprNode visitCaretExpr(roboParser.CaretExprContext ctx) {
        var node = new CaretExprNode();
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
    @Override public RoboNode visitRoboEventCallExpr(roboParser.RoboEventCallExprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitFalseExpr(roboParser.FalseExprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitBoolExpr(roboParser.BoolExprContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public IdentifierNode visitIdExpr(roboParser.IdExprContext ctx) {
        var node = new IdentifierNode();
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
    @Override public RoboNode visitType(roboParser.TypeContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitReturn_stat(roboParser.Return_statContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitDecrement_operator(roboParser.Decrement_operatorContext ctx) { return visitChildren(ctx); }
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override public RoboNode visitIncrement_operator(roboParser.Increment_operatorContext ctx) { return visitChildren(ctx); }
}
