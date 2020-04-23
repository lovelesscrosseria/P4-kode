package AST;

import AST.Nodes.Infix.*;
import AST.Nodes.RoboNode;
import AST.Nodes.Variables.IdentifierNode;

public class PrintAst extends AstVisitor<RoboNode>{
    @Override
    public RoboNode visit(IdentifierNode node) {
        System.out.print(node.Id);

        return null;
    }

    @Override
    public RoboNode visit(CaretExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print('^');
        visit(node.Right);
        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(DivisionExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print('/');
        visit(node.Right);
        System.out.print(" )");

        return node;
    }

    @Override
    public RoboNode visit(DigitExprNode node) {
        System.out.print(node.value);

        return null;
    }

    @Override
    public RoboNode visit(ModuloExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print('%');
        visit(node.Right);
        System.out.print(" )");
        return null;
    }

    @Override
    public RoboNode visit(MultiplicationExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print('*');
        visit(node.Right);
        System.out.print(" )");
        return null;
    }

    @Override
    public RoboNode visit(SubtractionExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print('-');
        visit(node.Right);
        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(AdditionExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print('+');
        visit(node.Right);
        System.out.print(" )");

        return node;
    }
}
