package expr;

import java.util.List;

public class Bool extends LogicExpression {
    private final boolean b;

    public Bool(String b){
        this.b= Boolean.parseBoolean(b);
    }

    @Override
    public boolean value() {
        return b;
    }

    @Override
    public String toString()
    {
        return String.valueOf(b);
    }

    @Override
    public List<Expression> GetChildren() {
        return null;
    }
}