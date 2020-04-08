import java.util.function.Function;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

class BuildAstVisitor extends roboBaseVisitor<ExpressionNode>
{
    @Override
	public ExpressionNode visitProgram(roboParser.ProgramContext context)
	{
		return visit(context);
	}

	public ExpressionNode visitDigitExpr(roboParser.DigitExprContext context)
	{
        NumberNode node = new NumberNode();

		return new NumberNode()
		{
			node.Value = Double.Parse(context.value.Text, NumberStyles.AllowDecimalPoint | NumberStyles.AllowExponent);
		};
	}

    @Override
    public ExpressionNode visitInfixExpr(roboParser.InfixExprContext context)
    {
        InfixExpressionNode node;

        switch (context.op.Type)
        {
            case roboLexer.ADD_OP:
                node = new AdditionNode();
                break;

            case roboLexer.SUB_OP:
                node = new SubtractionNode();
                break;

            case roboLexer.MUL_OP:
                node = new MultiplicationNode();
                break;

            case roboLexer.DIV_OP:
                node = new DivisionNode();
                break;

            default:
                throw new NotSupportedException();
        }

        node.Left = visit(context.left);
        node.Right = visit(context.right);

        return node;
    }
}
