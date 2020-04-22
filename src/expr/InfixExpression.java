package expr;

import java.util.ArrayList;
import java.util.List;

public class InfixExpression extends NumExpression {
    private final NumExpression left;
    private final NumExpression right;
    private final SymbolExpression symbol;


    public InfixExpression(NumExpression left, NumExpression right, SymbolExpression type)
    {
        this.left=left;
        this.right=right;
        this.symbol =type;
    }

    @Override
    public double value() throws Exception {
        switch (symbol.toString()){
            case "+":
                return left.value() + right.value();
            case "-":
                return left.value() - right.value();
            case "*":
                return left.value() * right.value();
            case "/":
                return left.value() / right.value();
            case "%":
                return left.value() % right.value();
            default:
                throw new Exception("infix wrong type");
        }
    }

    @Override
    public String toString()
    {
        return "("+left+")"+ symbol.toString() +"("+right+")";
    }

    @Override
    public List<Expression> GetChildren() {
        List<Expression> ret = new ArrayList<Expression>();
        ret.add(left);
        ret.add(symbol);
        ret.add(right);
        return ret;
    }
}
