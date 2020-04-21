
internal abstract class AstVisitor<T>
{	
	abstract T Visit(AdditionNode node);
	abstract T Visit(SubtractionNode node);
	abstract T Visit(MultiplicationNode node);
	abstract T Visit(DivisonNode node);
	abstract T Visit(NegateNode node);
	abstract T Visit(FunctionNode node);
	abstract T Visit(NumberNode node);

	public T Visit(ExpressionNode node)
	{
		return Visit((dynamic)node);
	}
}
