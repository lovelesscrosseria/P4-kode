internal class EvaluateExpressionVisitor extends AstVisitor<double>
{
	@Override
    public double Visit(AdditionNode node)
    {
        return Visit(node.Left) + Visit(node.Right);
    }

	@Override
    public double Visit(SubtractionNode node)
    {
        return Visit(node.Left) - Visit(node.Right);
    }

	@Override
    public double Visit(MultiplicationNode node)
    {
        return Visit(node.Left) * Visit(node.Right);
    }

	@Override
    public double Visit(DivisionNode node)
    {
        return Visit(node.Left) / Visit(node.Right);
    }

	@Override
    public double Visit(NegateNode node)
    {
        return Visit(node.InnerNode);
    }

	@Override
    public double Visit(FunctionNode node)
    {
        return node.Function(Visit(node.Argument));
    }

	@Override
    public double Visit(NumberNode node)
    {
        return node.Value;
    }
}
