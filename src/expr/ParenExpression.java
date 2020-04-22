package expr;

import java.util.List;

public class ParenExpression<T> implements Expression {
    T indhold;

    public ParenExpression(T expr) {
        indhold = expr;
    }

    public T value() {
        return indhold;
    }

    @Override
    public List<Expression> GetChildren() {
        return null;
    }
}
