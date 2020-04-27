package expr;

import java.util.ArrayList;
import java.util.List;

public class NumBoolExpression extends LogicExpression{
    private final NumExpression left;
    private final NumExpression right;
    private final SymbolExpression symbol;

    public NumBoolExpression(NumExpression left, NumExpression right, SymbolExpression symbol){
            this.left=left;
            this.right=right;
            this.symbol=symbol;
    }

    @Override
    public boolean value() throws Exception {
        switch (symbol.toString()){
            case "<=":
                return left.value() <= right.value();
            case ">=":
                return left.value() >= right.value();
            case ">":
                return left.value() > right.value();
            case "<":
                return left.value() < right.value();
            case "!=":
                return left.value() != right.value();
            case "==":
                return left.value() == right.value();
            default:
                throw new Exception("bool wrong symbol");
        }
    }

    @Override
    public String toString() {

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
