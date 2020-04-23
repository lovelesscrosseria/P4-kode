package AST;

import AST.Nodes.Infix.*;
import AST.Nodes.RoboNode;

abstract class AstVisitor<T> {
    public abstract T visit(AdditionExprNode node);
    public abstract T visit(DivisionExprNode node);
    public abstract T visit(DigitExprNode node);
    public abstract T visit(ModuloExprNode node);
    public abstract T visit(MultiplicationExprNode node);
    public abstract T visit(SubtractionExprNode node);


    public T visit(RoboNode node) {
        if (node instanceof AdditionExprNode) {
            return visit((AdditionExprNode) node);
        } else if (node instanceof DivisionExprNode) {
            return visit((DivisionExprNode) node);
        } else if (node instanceof InfixExprNode) {
            if (node instanceof ModuloExprNode) {
                return visit((ModuloExprNode) node);
            } else if (node instanceof MultiplicationExprNode) {
                return visit((MultiplicationExprNode) node);
            } else if (node instanceof SubtractionExprNode) {
                return visit((SubtractionExprNode) node);
            }
        } else if (node instanceof DigitExprNode) {
            return visit((DigitExprNode) node);
        }

        return null;
    }
}
