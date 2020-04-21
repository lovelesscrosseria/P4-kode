import java.util.function.Function;

public class ExpressionNode
{

}

class InfixExpressionNode extends ExpressionNode
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

class NegateNode extends ExpressionNode
{
    public ExpressionNode InnerNode;

    public ExpressionNode GetInnerNode()
    {
        return InnerNode;
    }

    public void SetInnerNode(ExpressionNode innerNode)
    {
        this.InnerNode = innerNode;
    }
}

class FuncNode extends ExpressionNode
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

class NumberNode extends ExpressionNode
{
    public double Value;

    public double GetValue()
    {
        return Value;
    }

    public void SetValue(double value)
    {
        this.Value = value;
    }
}
