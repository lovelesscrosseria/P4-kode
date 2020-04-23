/*import GrammarOut.*;
import expr.*;
import expr.Number;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

public class MakeExpressions implements roboListener{
    private Stack<Expression> exprStack = new Stack<>();

    public Expression getExpression()
    {
        return exprStack.get(0);
    }

    @Override
    public void enterProgram(roboParser.ProgramContext ctx) {

    }

    @Override
    public void exitProgram(roboParser.ProgramContext ctx) {
        //TODO Push the final note (root of the tree) to stack
        //exprStack.push(new SymbolExpression("+"));
        System.out.println(exprStack);

    }

    @Override
    public void enterStrategy(roboParser.StrategyContext ctx) {

    }

    @Override
    public void exitStrategy(roboParser.StrategyContext ctx) {

    }

    @Override
    public void enterBehavior(roboParser.BehaviorContext ctx) {

    }

    @Override
    public void exitBehavior(roboParser.BehaviorContext ctx) {

    }

    @Override
    public void enterFunction_decl(roboParser.Function_declContext ctx) {

    }

    @Override
    public void exitFunction_decl(roboParser.Function_declContext ctx) {

    }

    @Override
    public void enterEvent_decl(roboParser.Event_declContext ctx) {

    }

    @Override
    public void exitEvent_decl(roboParser.Event_declContext ctx) {

    }

    @Override
    public void enterVariable_decl(roboParser.Variable_declContext ctx) {

    }

    @Override
    public void exitVariable_decl(roboParser.Variable_declContext ctx) {

    }

    @Override
    public void enterList_decl(roboParser.List_declContext ctx) {

    }

    @Override
    public void exitList_decl(roboParser.List_declContext ctx) {

    }

    @Override
    public void enterDictionary_decl(roboParser.Dictionary_declContext ctx) {

    }

    @Override
    public void exitDictionary_decl(roboParser.Dictionary_declContext ctx) {

    }

    @Override
    public void enterCollection_expr(roboParser.Collection_exprContext ctx) {

    }

    @Override
    public void exitCollection_expr(roboParser.Collection_exprContext ctx) {

    }

    @Override
    public void enterCollection_statement(roboParser.Collection_statementContext ctx) {

    }

    @Override
    public void exitCollection_statement(roboParser.Collection_statementContext ctx) {

    }

    @Override
    public void enterRoboCode_method(roboParser.RoboCode_methodContext ctx) {

    }

    @Override
    public void exitRoboCode_method(roboParser.RoboCode_methodContext ctx) {

    }

    @Override
    public void enterAssignment(roboParser.AssignmentContext ctx) {

    }

    @Override
    public void exitAssignment(roboParser.AssignmentContext ctx) {

    }

    @Override
    public void enterStat(roboParser.StatContext ctx) {

    }

    @Override
    public void exitStat(roboParser.StatContext ctx) {

    }

    @Override
    public void enterFunction_call(roboParser.Function_callContext ctx) {

    }

    @Override
    public void exitFunction_call(roboParser.Function_callContext ctx) {

    }

    @Override
    public void enterFor_loop(roboParser.For_loopContext ctx) {

    }

    @Override
    public void exitFor_loop(roboParser.For_loopContext ctx) {

    }

    @Override
    public void enterWhile_loop(roboParser.While_loopContext ctx) {

    }

    @Override
    public void exitWhile_loop(roboParser.While_loopContext ctx) {

    }

    @Override
    public void enterDo_while_loop(roboParser.Do_while_loopContext ctx) {

    }

    @Override
    public void exitDo_while_loop(roboParser.Do_while_loopContext ctx) {

    }

    @Override
    public void enterBlock(roboParser.BlockContext ctx) {

    }

    @Override
    public void exitBlock(roboParser.BlockContext ctx) {

    }

    @Override
    public void enterFormal_params(roboParser.Formal_paramsContext ctx) {

    }

    @Override
    public void exitFormal_params(roboParser.Formal_paramsContext ctx) {

    }

    @Override
    public void enterParams(roboParser.ParamsContext ctx) {

    }

    @Override
    public void exitParams(roboParser.ParamsContext ctx) {

    }

    @Override
    public void enterFuncExpr(roboParser.FuncExprContext ctx) {

    }

    @Override
    public void exitFuncExpr(roboParser.FuncExprContext ctx) {

    }

    @Override
    public void enterTrueExpr(roboParser.TrueExprContext ctx) {

    }

    @Override
    public void exitTrueExpr(roboParser.TrueExprContext ctx) {

    }

    @Override
    public void enterDecrExpr(roboParser.DecrExprContext ctx) {

    }

    @Override
    public void exitDecrExpr(roboParser.DecrExprContext ctx) {

    }

    @Override
    public void enterCollExpr(roboParser.CollExprContext ctx) {

    }

    @Override
    public void exitCollExpr(roboParser.CollExprContext ctx) {

    }

    @Override
    public void enterIncrExpr(roboParser.IncrExprContext ctx) {

    }

    @Override
    public void exitIncrExpr(roboParser.IncrExprContext ctx) {

    }

    @Override
    public void enterParensExpr(roboParser.ParensExprContext ctx) {

    }

    @Override
    public void exitParensExpr(roboParser.ParensExprContext ctx) {

    }

    @Override
    public void enterDigitExpr(roboParser.DigitExprContext ctx) {

    }

    @Override
    public void exitDigitExpr(roboParser.DigitExprContext ctx) {
        double n = Double.parseDouble(ctx.getText());
        exprStack.push(new Number(n));


    }

    @Override
    public void enterStringExpr(roboParser.StringExprContext ctx) {

    }

    @Override
    public void exitStringExpr(roboParser.StringExprContext ctx) {

    }

    @Override
    public void enterInfixExpr(roboParser.InfixExprContext ctx) {

    }

    @Override
    public void exitInfixExpr(roboParser.InfixExprContext ctx) {
        NumExpression right = (NumExpression) exprStack.pop();
        SymbolExpression symbol = (SymbolExpression) exprStack.pop();
        NumExpression left = (NumExpression) exprStack.pop();

        exprStack.push(new InfixExpression(left, right, symbol));

    }

    @Override
    public void enterNotExpr(roboParser.NotExprContext ctx) {

    }

    @Override
    public void exitNotExpr(roboParser.NotExprContext ctx) {

    }

    @Override
    public void enterCaretExpr(roboParser.CaretExprContext ctx) {

    }

    @Override
    public void exitCaretExpr(roboParser.CaretExprContext ctx) {

    }

    @Override
    public void enterFalseExpr(roboParser.FalseExprContext ctx) {

    }

    @Override
    public void exitFalseExpr(roboParser.FalseExprContext ctx) {

    }

    @Override
    public void enterBoolExpr(roboParser.BoolExprContext ctx) {

    }

    @Override
    public void exitBoolExpr(roboParser.BoolExprContext ctx) {

    }

    @Override
    public void enterIdExpr(roboParser.IdExprContext ctx) {

    }

    @Override
    public void exitIdExpr(roboParser.IdExprContext ctx) {

    }

    @Override
    public void enterType(roboParser.TypeContext ctx) {

    }

    @Override
    public void exitType(roboParser.TypeContext ctx) {

    }

    @Override
    public void enterReturn_stat(roboParser.Return_statContext ctx) {

    }

    @Override
    public void exitReturn_stat(roboParser.Return_statContext ctx) {

    }

    @Override
    public void enterDecrement_operator(roboParser.Decrement_operatorContext ctx) {

    }

    @Override
    public void exitDecrement_operator(roboParser.Decrement_operatorContext ctx) {

    }

    @Override
    public void enterIncrement_operator(roboParser.Increment_operatorContext ctx) {

    }

    @Override
    public void exitIncrement_operator(roboParser.Increment_operatorContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {
        switch (terminalNode.getText()){
            case "+":
            case "-":
            case "*":
            case "/":
            case "%":
                exprStack.push(new SymbolExpression(terminalNode.getText()));

        }

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
*/