import java.util.function.Function;

abstract class ExpressionNode
{

}

abstract class InfixExpressionNode extends ExpressionNode
{
	private ExpressionNode Left; 
	private ExpressionNode Right;

	public ExpressionNode GetLeft()
	{
		return Left;
	}
	public void SetLeft(ExpressionNode left)
	{
		this.Left = left;
	}
	public ExpressionNode GetRight()
	{
		return Right;
	}
	public void SetRight(ExpressionNode right)
	{
		this.Right = right;
	}
}

class AdditionNode extends InfixExpressionNode {  }

class SubtractNode extends InfixExpressionNode {  }

class MultiplicationNode extends InfixExpressionNode {  }

class DivisonNode extends InfixExpressionNode {  }

class FuncNode extends InfixExpressionNode
{
	public Function<Double, Double> Function;
	public ExpressionNode Argument;

	public Function GetFunction()
	{
		return Function;
	}
	public void SetFunction(Function<Double, Double> function)
	{
		this.Function = function;
	}
	public ExpressionNode GetArgument()
	{
		return Argument;
	}
	public void Argument(ExpressionNode argument)
	{
		this.Argument = argument;
	}
}
