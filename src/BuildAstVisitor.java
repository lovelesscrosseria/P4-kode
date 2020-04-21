import GrammarOut.roboLexer;
import GrammarOut.roboParser;

public class BuildAstVisitor extends roboBaseVisitor<ExpressionNode>
{
    @Override
	public ExpressionNode visitProgram(roboParser.ProgramContext context)
	{
		return visit(context);
	}

	@Override
	public ExpressionNode visitDigitExpr(roboParser.DigitExprContext context)
	{
		String str = String.valueOf(context.value);
		double value = Double.parseDouble(str);

		NumberNode numberNode = new NumberNode();
		numberNode.SetValue(value);

		return numberNode;
	}

    @Override
    public ExpressionNode visitInfixExpr(roboParser.InfixExprContext context)
    {
        InfixExpressionNode node;

        switch(context.op.Type)
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
                node = new DivisonNode();
                break;
    	}
        var left = visit(context.left);
        var right = visit(context.right);

        node.SetLeft(left);
        node.SetRight(right);

        return node;
	}

	@Override
    public ExpressionNode visitFuncExpr(roboParser.FuncExprContext context)
    {
        var functionName = context.func.Text;

        var func = typeOf(robo)
            .GetMethods(BindingFlags.Public | BindingFlags.Static)
            .Where(m -> m.ReturnType == instanceOf(Double))
            .Where(m -> m.GetParameters().Select(p -> p.ParameterType).SequenceEqual(new double[] { typeOf(Double) }))
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
