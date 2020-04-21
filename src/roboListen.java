import GrammarOut.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class roboListen implements roboListener {
    @Override
    public void enterProgram(roboParser.ProgramContext ctx) {
        System.err.println("entering program");

    }

    @Override
    public void exitProgram(roboParser.ProgramContext ctx) {
        System.err.println("exiting program");

    }

    @Override
    public void enterStrategy(roboParser.StrategyContext ctx) {
        System.err.println("entering strategy");

    }

    @Override
    public void exitStrategy(roboParser.StrategyContext ctx) {
        System.err.println("exiting strategy");

    }

    @Override
    public void enterBehavior(roboParser.BehaviorContext ctx) {
        System.err.println("entering behavior");

    }

    @Override
    public void exitBehavior(roboParser.BehaviorContext ctx) {
        System.err.println("exiting behavior");

    }

    @Override
    public void enterFunction_decl(roboParser.Function_declContext ctx) {
        System.err.println("entering Func-Decleration");

    }

    @Override
    public void exitFunction_decl(roboParser.Function_declContext ctx) {
        System.err.println("exiting Func-Decleration");

    }

    @Override
    public void enterEvent_decl(roboParser.Event_declContext ctx) {
        System.err.println("entering Event-Decleration");

    }

    @Override
    public void exitEvent_decl(roboParser.Event_declContext ctx) {
        System.err.println("exiting Event-Decleration");

    }

    @Override
    public void enterVariable_decl(roboParser.Variable_declContext ctx) {
        System.err.println("entering variabe_decl");

    }

    @Override
    public void exitVariable_decl(roboParser.Variable_declContext ctx) {
        System.err.println("exiting variable_decl");

    }

    @Override
    public void enterList_decl(roboParser.List_declContext ctx) {
        System.err.println("entering List_decl");

    }

    @Override
    public void exitList_decl(roboParser.List_declContext ctx) {
        System.err.println("exiting List_decl");

    }

    @Override
    public void enterDictionary_decl(roboParser.Dictionary_declContext ctx) {
        System.err.println("entering Dictionary_decl");

    }

    @Override
    public void exitDictionary_decl(roboParser.Dictionary_declContext ctx) {
        System.err.println("exiting Dictionary_decl");

    }

    @Override
    public void enterCollection_expr(roboParser.Collection_exprContext ctx) {
        System.err.println("entering Collection_expression");

    }

    @Override
    public void exitCollection_expr(roboParser.Collection_exprContext ctx) {
        System.err.println("exiting Collection_expression");

    }

    @Override
    public void enterCollection_statement(roboParser.Collection_statementContext ctx) {
        System.err.println("entering Collection_statement");

    }

    @Override
    public void exitCollection_statement(roboParser.Collection_statementContext ctx) {
        System.err.println("exiting Collection_statement");

    }

    @Override
    public void enterRoboCode_method(roboParser.RoboCode_methodContext ctx) {
        System.err.println("entering Robocode_method");

    }

    @Override
    public void exitRoboCode_method(roboParser.RoboCode_methodContext ctx) {
        System.err.println("exiting Robocode_method");

    }

    @Override
    public void enterAssignment(roboParser.AssignmentContext ctx) {
        System.err.println("entering Assignment");

    }

    @Override
    public void exitAssignment(roboParser.AssignmentContext ctx) {
        System.err.println("exiting Assignment");

    }

    @Override
    public void enterStat(roboParser.StatContext ctx) {
        System.err.println("entering statement");

    }

    @Override
    public void exitStat(roboParser.StatContext ctx) {
        System.err.println("exiting statement");

    }

    @Override
    public void enterFunction_call(roboParser.Function_callContext ctx) {
        System.err.println("entering Function call");

    }

    @Override
    public void exitFunction_call(roboParser.Function_callContext ctx) {
        System.err.println("exitingFunction call");

    }

    @Override
    public void enterFor_loop(roboParser.For_loopContext ctx) {
        System.err.println("entering For loop");

    }

    @Override
    public void exitFor_loop(roboParser.For_loopContext ctx) {
        System.err.println("exiting For loop");

    }

    @Override
    public void enterWhile_loop(roboParser.While_loopContext ctx) {
        System.err.println("entering while loop");

    }

    @Override
    public void exitWhile_loop(roboParser.While_loopContext ctx) {
        System.err.println("exiting while loop");

    }

    @Override
    public void enterDo_while_loop(roboParser.Do_while_loopContext ctx) {
        System.err.println("entering Do_while_loop");

    }

    @Override
    public void exitDo_while_loop(roboParser.Do_while_loopContext ctx) {
        System.err.println("exiting Do_while_loop");

    }

    @Override
    public void enterBlock(roboParser.BlockContext ctx) {
        System.err.println("entering block");

    }

    @Override
    public void exitBlock(roboParser.BlockContext ctx) {
        System.err.println("exiting block");

    }

    @Override
    public void enterFormal_params(roboParser.Formal_paramsContext ctx) {
        System.err.println("entering Formal-params");

    }

    @Override
    public void exitFormal_params(roboParser.Formal_paramsContext ctx) {
        System.err.println("exiting Formal-params");

    }

    @Override
    public void enterParams(roboParser.ParamsContext ctx) {
        System.err.println("entering Params");

    }

    @Override
    public void exitParams(roboParser.ParamsContext ctx) {
        System.err.println("exiting Params");

    }

    @Override
    public void enterFuncExpr(roboParser.FuncExprContext ctx) {
        System.err.println("entering Function Call Expression");

    }

    @Override
    public void exitFuncExpr(roboParser.FuncExprContext ctx) {
        System.err.println("exiting Function Call Expression");

    }

    @Override
    public void enterTrueExpr(roboParser.TrueExprContext ctx) {
        System.err.println("entering true expression");

    }

    @Override
    public void exitTrueExpr(roboParser.TrueExprContext ctx) {
        System.err.println("exiting true expression");

    }

    @Override
    public void enterDecrExpr(roboParser.DecrExprContext ctx) {
        System.err.println("entering Decrement operator expression");

    }

    @Override
    public void exitDecrExpr(roboParser.DecrExprContext ctx) {
        System.err.println("exitingDecrement operator expression");

    }

    @Override
    public void enterCollExpr(roboParser.CollExprContext ctx) {
        System.err.println("entering Collection Expression");

    }

    @Override
    public void exitCollExpr(roboParser.CollExprContext ctx) {
        System.err.println("exitingCollection Expression");

    }

    @Override
    public void enterIncrExpr(roboParser.IncrExprContext ctx) {
        System.err.println("entering Increment-opreator Expression");

    }

    @Override
    public void exitIncrExpr(roboParser.IncrExprContext ctx) {
        System.err.println("exiting Increment-opreator Expression");

    }

    @Override
    public void enterParensExpr(roboParser.ParensExprContext ctx) {
        System.err.println("entering paranthesized Expression");

    }

    @Override
    public void exitParensExpr(roboParser.ParensExprContext ctx) {
        System.err.println("exiting paranthesized Expression");

    }

    @Override
    public void enterDigitExpr(roboParser.DigitExprContext ctx) {
        System.err.println("entering Digit Expression");

    }

    @Override
    public void exitDigitExpr(roboParser.DigitExprContext ctx) {
        System.err.println("exiting Digit Expression");

    }

    @Override
    public void enterStringExpr(roboParser.StringExprContext ctx) {
        System.err.println("entering String Expression");

    }

    @Override
    public void exitStringExpr(roboParser.StringExprContext ctx) {
        System.err.println("exiting String Expression");

    }

    @Override
    public void enterInfixExpr(roboParser.InfixExprContext ctx) {
        System.err.println("entering Infix Expression");

    }

    @Override
    public void exitInfixExpr(roboParser.InfixExprContext ctx) {
        System.err.println("exiting Infix Expression");

    }

    @Override
    public void enterNotExpr(roboParser.NotExprContext ctx) {
        System.err.println("entering Not operation Expression");

    }

    @Override
    public void exitNotExpr(roboParser.NotExprContext ctx) {
        System.err.println("exiting Not operation Expression");

    }

    @Override
    public void enterCaretExpr(roboParser.CaretExprContext ctx) {
        System.err.println("entering Carat Expression");

    }

    @Override
    public void exitCaretExpr(roboParser.CaretExprContext ctx) {
        System.err.println("exiting Carat Expression");

    }

    @Override
    public void enterFalseExpr(roboParser.FalseExprContext ctx) {
        System.err.println("entering False Expression");

    }

    @Override
    public void exitFalseExpr(roboParser.FalseExprContext ctx) {
        System.err.println("exiting False Expression");

    }

    @Override
    public void enterBoolExpr(roboParser.BoolExprContext ctx) {
        System.err.println("entering Bool Expression");

    }

    @Override
    public void exitBoolExpr(roboParser.BoolExprContext ctx) {
        System.err.println("exiting Bool Expression");


    }

    @Override
    public void enterIdExpr(roboParser.IdExprContext ctx) {
        System.err.println("entering ID Expression");

    }

    @Override
    public void exitIdExpr(roboParser.IdExprContext ctx) {
        System.err.println("exiting ID Expression");

    }

    @Override
    public void enterType(roboParser.TypeContext ctx) {
        System.err.println("entering type");

    }

    @Override
    public void exitType(roboParser.TypeContext ctx) {
        System.err.println("exiting type");

    }

    @Override
    public void enterReturn_stat(roboParser.Return_statContext ctx) {
        System.err.println("entering return statement");

    }

    @Override
    public void exitReturn_stat(roboParser.Return_statContext ctx) {
        System.err.println("exiting return statement");

    }

    @Override
    public void enterDecrement_operator(roboParser.Decrement_operatorContext ctx) {
        System.err.println("entering Decrement operator");

    }

    @Override
    public void exitDecrement_operator(roboParser.Decrement_operatorContext ctx) {
        System.err.println("exiting Decrement operator");

    }

    @Override
    public void enterIncrement_operator(roboParser.Increment_operatorContext ctx) {
        System.err.println("entering increment operator");

    }

    @Override
    public void exitIncrement_operator(roboParser.Increment_operatorContext ctx) {
        System.err.println("exiting increment operator");

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {
        System.err.println("terminal" + " " + terminalNode.getText());

    }

    //Not needed right now
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
