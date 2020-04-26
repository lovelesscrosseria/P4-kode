package AST;

import AST.Nodes.Functions.*;
import AST.Nodes.Infix.*;
import AST.Nodes.RoboNode;
import AST.Nodes.Variables.*;
import jdk.jfr.Event;

import java.util.function.Function;

abstract class AstVisitor<T> {
    public abstract T visit(AdditionExprNode node);
    public abstract T visit(DivisionExprNode node);
    public abstract T visit(DigitExprNode node);
    public abstract T visit(ModuloExprNode node);
    public abstract T visit(MultiplicationExprNode node);
    public abstract T visit(SubtractionExprNode node);
    public abstract T visit(IdentifierNode node);
    public abstract T visit(CaretExprNode node);
    public abstract T visit(TypeNode node);
    public abstract T visit(VariableDeclNode node);
    public abstract T visit(FormalParamNode node);
    public abstract T visit(FunctionDeclNode node);
    public abstract T visit(BlockNode node);
    public abstract T visit(StringExprNode node);
    public abstract T visit(AssignmentNode node);
    public abstract T visit(DecrementOperatorNode node);
    public abstract T visit(IncrementOperatorNode node);
    public abstract T visit(ListDeclNode node);
    public abstract T visit(StrategyNode node);
    public abstract T visit(BehaviorNode node);
    public abstract T visit(EventNode node);
    public abstract T visit(FunctionCallNode node);
    public abstract T visit(ParamNode node);



   // public abstract T visit(CaretExprNode node);
   // public abstract T visit(CaretExprNode node);
   // public abstract T visit(CaretExprNode node);
   // public abstract T visit(CaretExprNode node);
   // public abstract T visit(CaretExprNode node);
   // public abstract T visit(CaretExprNode node);
   // public abstract T visit(CaretExprNode node);


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
            } else if (node instanceof CaretExprNode) {
                return visit((CaretExprNode) node);
            }
        } else if (node instanceof DigitExprNode) {
            return visit((DigitExprNode) node);
        } else if (node instanceof IdentifierNode) {
            return visit((IdentifierNode) node);
        } else if (node instanceof TypeNode) {
            return visit((TypeNode) node);
        } else if (node instanceof VariableDeclNode) {
            return visit((VariableDeclNode) node);
        }  else if (node instanceof FormalParamNode) {
            return visit((FormalParamNode) node);
        }  else if (node instanceof FunctionDeclNode) {
            return visit((FunctionDeclNode) node);
        } else if (node instanceof BlockNode) {
            return visit((BlockNode) node);
        } else if (node instanceof StringExprNode) {
            return visit((StringExprNode) node);
        } else if (node instanceof AssignmentNode) {
            return visit((AssignmentNode) node);
        } else if (node instanceof DecrementOperatorNode) {
            return visit((DecrementOperatorNode) node);
        } else if (node instanceof IncrementOperatorNode) {
            return visit((IncrementOperatorNode) node);
        } else if (node instanceof ListDeclNode) {
            return visit((ListDeclNode) node);
        } else if (node instanceof StrategyNode) {
            return visit((StrategyNode) node);
        } else if (node instanceof BehaviorNode) {
            return visit((BehaviorNode) node);
        } else if (node instanceof EventNode) {
            return visit((EventNode) node);
        } else if (node instanceof FunctionCallNode) {
            return visit((FunctionCallNode) node);
        } else if (node instanceof ParamNode) {
            return visit((ParamNode) node);
        }

        return null;
    }
}
