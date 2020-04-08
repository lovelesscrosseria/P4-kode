import java.util.function.Function;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

class BuildAstVisitor extends roboBaseVisitor<ExpressionNode>
{
	@Override
	public ExpressionNode VisitCompileUnit(roboParser.CompileUnitContext context)
	{
		return Vist(context.expr());
	}

	@Override
	public ExpressionNode VisitNumberExpr(roboParser.NumberExprContext context)
	{
		return new NumberNode()
		{
			ExpressionNode Value = Double.Parse(context.value.Text, NumberStyles.AllowDecimalPoint | NumberStyles.AllowExponent);
		};
	}

	@Override
	public ExpressionNode VisitParensExpr(MathParser.ParensExprContext context)
	{
		return Visit(context.expr());
	}

	@Override
	public ExpressionNode VisitInfixExpr(MathParser.InfixExprContext context)
	{
		InfixExpressionNode node;

		switch (context.op.Type)
		{
			case MathLexer.OP._ADD:
				node = new AdditionNode();
				break;

			case MathLexer.OP._SUB:
				node = new SubtractNode();
				break;

			case MathLexer.OP._MUL:
				node = new MultiplicationNode();
				break;

			case MathLexer.OP._DIV:
				node = new DivisonNode();
				break;

			default:
				throw new Exception();
		}

		node.Left = Visit(context.left);
		node.Right = Visit(context.right);
	}
	
	public override ExpressionNode VisitUnaryExpr(MathParser.UnaryExprContext context)
    {
        switch (context.op.Type)
        {
            case MathLexer.OP_ADD:
                return Visit(context.expr());

            case MathLexer.OP_SUB:
                return new NegateNode
                {
                    InnerNode = Visit(context.expr())
                };

            default:
                throw new NotSupportedException();
        }
    }

	@Override
    public ExpressionNode VisitFuncExpr(MathParser.FuncExprContext context)
    {
        var functionName = context.func.Text;

        var func = typeof(Math)
            .GetMethods(BindingFlags.Public | BindingFlags.Static)
            .Where(m -> m.ReturnType == typeof(double))
            .Where(m -> m.GetParameters().Select(p -> p.ParameterType).SequenceEqual(new[] { typeof(double) }))
            .FirstOrDefault(m -> m.Name.Equals(functionName, StringComparison.OrdinalIgnoreCase));

        if (func == null)
            throw new NotSupportedException(string.Format("Function {0} is not supported", functionName));

        return new FunctionNode
        {
            Function = (Func<double, double>)func.CreateDelegate(typeof(Func<double, double>)),
            Argument = Visit(context.expr())
        };
    }
}
