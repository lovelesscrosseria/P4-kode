package AST;

import AST.Nodes.Bool.*;
import AST.Nodes.Functions.BlockNode;
import AST.Nodes.Functions.FormalParamNode;
import AST.Nodes.Functions.FunctionDeclNode;
import AST.Nodes.Functions.*;
import AST.Nodes.Infix.*;
import AST.Nodes.Loops.DoWhileLoopNode;
import AST.Nodes.Loops.ForLoopNode;
import AST.Nodes.Loops.WhileLoopNode;
import AST.Nodes.RoboNode;
import AST.Nodes.Variables.*;

public class PrintAst extends AstVisitor<RoboNode>{
    @Override
    public RoboNode visit(DecrementOperatorExprNode node) {
        System.out.print("( ");
        visit(node.Id);
        System.out.print("-- )");
        return null;
    }

    @Override
    public RoboNode visit(ReturnNode node) {
        System.out.print("( return ");
        visit(node.Value);
        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(RoboCodeMethodExprNode node) {
        System.out.print("( robot.");
        visit(node.Method);
        System.out.print(")");

        return null;
    }

    @Override
    public RoboNode visit(FunctionCallExprNode node) {
        System.out.print("( ");
        visit(node.Method);
        System.out.print("(");

        for (int i = 0; i < node.Params.size(); i++) {
            visit(node.Params.get(i));

            if (i + 1 != node.Params.size()) {
                System.out.print(", ");
            }
        }
        System.out.print(") )");

        return null;
    }

    @Override
    public RoboNode visit(DoWhileLoopNode node) {
        System.out.print("( do ");
        visit(node.Block);
        System.out.print(" while ( ");
        visit(node.Condition);
        System.out.print(" )");
        return null;
    }

    @Override
    public RoboNode visit(WhileLoopNode node) {
        System.out.print("while ( ");
        visit(node.Condition);
        System.out.print(" )");
        visit(node.Block);

        return null;
    }

    @Override
    public RoboNode visit(ForLoopNode node) {
        System.out.print("( for(");
        visit(node.Init);
        System.out.print("; ");
        visit(node.Condition);
        System.out.print("; ");
        visit(node.Increment);
        System.out.print(" )");
        visit(node.Block);
        System.out.print(" )");
        return null;
    }


    @Override
    public RoboNode visit(DictionaryDeclNode node) {
        System.out.print("( ");
        System.out.print("dictionary<");
        visit(node.key);
        System.out.print(',');
        visit(node.value);
        System.out.print("> ");
        visit(node.Id);

        if (node.Nodes.size() > 0) {
            System.out.print("{ ");
            for (var value : node.Nodes) {
                visit(value);
            }
            System.out.print(" }");
        }

        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(DictionaryValueNode node) {
        System.out.print("( { ");
        visit(node.Key);
        System.out.print(",");
        visit(node.Value);
        System.out.print(" }");

        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(RoboCodeMethodNode node) {
        System.out.print("( robot.");
        visit(node.Method);
        System.out.print(") ");
        return null;
    }

    @Override
    public RoboNode visit(FunctionCallNode node) {
        System.out.print("( ");
        visit(node.Method);
        System.out.print("(");

        for (int i = 0; i < node.Params.size(); i++) {
            visit(node.Params.get(i));

            if (i + 1 != node.Params.size()) {
                System.out.print(", ");
            }
        }
        System.out.print(") )");

        return null;
    }

    @Override
    public RoboNode visit(ParamNode node) {
        System.out.print("( ");
        visit(node.Value);
        System.out.print(" )");

        return null;
    }

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
    public RoboNode visit(AndExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print(" && ");
        visit(node.Right);
        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(OrExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print(" || ");
        visit(node.Right);
        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(EqualExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print(" == ");
        visit(node.Right);
        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(NotEqualExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print(" != ");
        visit(node.Right);
        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(GreatEqualExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print(" >= ");
        visit(node.Right);
        System.out.print(" )");

        return null;
    }

    @Override
    public RoboNode visit(GreaterExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print(" > ");
        visit(node.Right);
        System.out.print(" )");
        return null;
    }

    @Override
    public RoboNode visit(LessEqualExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print(" <= ");
        visit(node.Right);
        System.out.print(" )");
        return null;
    }

    @Override
    public RoboNode visit(LessExprNode node) {
        System.out.print("( ");
        visit(node.Left);
        System.out.print(" < ");
        visit(node.Right);
        System.out.print(" )");
        return null;
    }

    @Override
    public RoboNode visit(BoolValueNode node) {
        System.out.print(node.Value);

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
        System.out.print(" + ");
        visit(node.Right);
        System.out.print(" )");

        return node;
    }
}
