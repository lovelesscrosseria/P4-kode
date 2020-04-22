package expr;

import java.util.List;

public class Number extends NumExpression {
    private final double n;

    public Number(double n)
    {
        this.n=n;
    }

    @Override
    public double value() {
        return n;
    }

    @Override
    public String toString()
    {
        return String.valueOf(n);
    }

    @Override
    public List<Expression> GetChildren() {
        return null;
    }
}
