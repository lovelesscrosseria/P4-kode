package expr;

public abstract class LogicExpression implements Expression {
    public abstract boolean value() throws Exception;
}
