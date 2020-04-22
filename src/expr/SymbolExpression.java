package expr;

import java.util.List;

public class SymbolExpression implements Expression {
    public final String symbol;

    public SymbolExpression (String value){
        this.symbol=value;
    }

    public String value() {
        return symbol;
    }

    @Override
    public String toString()
    {
        return symbol;
    }

    @Override
    public List<Expression> GetChildren() {
        return null;
    }
}
