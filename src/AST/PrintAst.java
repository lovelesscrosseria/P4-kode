package AST;

import AST.Nodes.Functions.*;
import AST.Nodes.Infix.*;
import AST.Nodes.RoboNode;
import AST.Nodes.Variables.*;

public class PrintAst extends AstVisitor<RoboNode>{
    @Override
    public RoboNode visit(EventNode node) {
        System.out.print("( Event ");
        visit(node.Id);
        visit(node.Block);
        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(StrategyNode node) {
        System.out.print("( strategy ");
        visit(node.Id);
        System.out.print(" { ");
        for (var behavior : node.behaviorNodes) {
            visit(behavior);
        }

        System.out.print(" } )");
        return null;
    }

    @Override
    public RoboNode visit(BehaviorNode node) {
        System.out.print("( behavior ");
        visit(node.Id);
        System.out.print('(');

        for (int i = 0; i < node.Params.size(); i++) {
            visit(node.Params.get(i));

            if (i + 1 != node.Params.size()) {
                System.out.print(", ");
            }
        }
        System.out.print(")");
        visit(node.Block);
        System.out.print(" )");
        return null;
    }

    @Override
    public RoboNode visit(ListDeclNode node) {
        System.out.print("( list<");
        visit(node.Type);
        System.out.print("> ");
        visit(node.Id);
        if (node.nodes.size() > 0) {
            System.out.print(" = { ");
            for (int i = 0; i < node.nodes.size(); i++) {
                visit(node.nodes.get(i));

                if (i + 1 != node.nodes.size()) {
                    System.out.print(", ");
                }
            }

            System.out.print(" }");
        }

        return null;
    }

    @Override
    public RoboNode visit(IncrementOperatorNode node) {
        System.out.print("( ");
        visit(node.Id);
        System.out.print("++ )");

        return null;
    }

    @Override
    public RoboNode visit(DecrementOperatorNode node) {
        System.out.print("( ");
        visit(node.Id);
        System.out.print("-- )");

        return null;
    }

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
        System.out.print(" " + node.value + "");

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
