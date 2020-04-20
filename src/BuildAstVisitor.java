import GrammarOut.roboLexer;
import GrammarOut.roboParser;

public class BuildAstVisitor extends roboBaseVisitor<ExpressionNode>
{

    @Override
	public ExpressionNode visitProgram(roboParser.ProgramContext context)
	{
		return visit(context);
	}

	public ExpressionNode visitDigitExpr(roboParser.DigitExprContext context)
	{
		double value = Double.Parse(context.value, NumberStyles.AllowDecimalPoint | NumberStyles.AllowExponent);

		NumberNode numberNode = new NumberNode();
		numberNode.SetValue(value);

        return new NumberNode( numberNode );
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
    	}
	}

	@Override
    public ExpressionNode VisitFuncExpr(roboParser.FuncExprContext context)
    {
        var functionName = context.func.Text;

        var func = typeof(robo)
            .GetMethods(BindingFlags.Public | BindingFlags.Static)
            .Where(m -> m.ReturnType == typeof(Double))
            .Where(m -> m.GetParameters().Select(p -> p.ParameterType).SequenceEqual(new double[] { typeof(Double) }))
            .FirstOrDefault(m -> m.Name.Equals(functionName, StringComparison.OrdinalIgnoreCase));

        if (func == null)
            throw new NotSupportedException(string.Format("Function {0} is not supported", functionName));

        Function = (Func<Double, Double>)func;
        Argument = Visit(context.expr());

		return new FunctionNode( Argument, Function );

        node.Left = visit(context.left);
        node.Right = visit(context.right);

        return node;
    }
}
