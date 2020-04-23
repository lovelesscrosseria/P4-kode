package AST;

import AST.Nodes.Functions.BlockNode;
import AST.Nodes.Functions.FormalParamNode;
import AST.Nodes.Functions.FunctionDeclNode;
import AST.Nodes.Infix.*;
import AST.Nodes.RoboNode;
import AST.Nodes.Variables.AssignmentNode;
import AST.Nodes.Variables.IdentifierNode;
import AST.Nodes.Variables.TypeNode;
import AST.Nodes.Variables.VariableDeclNode;

public class PrintAst extends AstVisitor<RoboNode>{
    @Override
    public RoboNode visit(AssignmentNode node) {
        System.out.print("( ");
        visit(node.Id);
        System.out.print(" = ");
        visit(node.Value);
        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(StringExprNode node) {
        System.out.print("\" " + node.value + "\"");

        return null;
    }

    @Override
    public RoboNode visit(BlockNode node) {
        System.out.print("{ ");
        for (var stat : node.statements) {
            visit(stat);
        }
        System.out.print(" }");
        return null;
    }

    @Override
    public RoboNode visit(FormalParamNode node) {
        visit(node.Type);
        System.out.print(' ');
        visit(node.Id);

        return null;
    }

    @Override
    public RoboNode visit(FunctionDeclNode node) {
        visit(node.Type);
        System.out.print(' ');
        visit(node.Id);
        System.out.print('(');

        for (int i = 0; i < node.Params.size(); i++) {
            visit(node.Params.get(i));

            if (i + 1 != node.Params.size()) {
                System.out.print(", ");
            }
        }
        System.out.print(") ");
        visit(node.block);

        return null;
    }

    @Override
    public RoboNode visit(VariableDeclNode node) {
        System.out.print("( ");
        visit(node.Type);
        System.out.print(" ");
        visit(node.Id);
        if (node.Value != null) {
            System.out.print(" = ");
            visit(node.Value);
            System.out.print(" )");
        }

        return null;
    }

    @Override
    public RoboNode visit(TypeNode node) {
        System.out.print(node.Type);

        return null;
    }

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
