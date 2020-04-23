package AST;

import AST.Nodes.RoboNode;

import java.util.ArrayList;

public class AST extends RoboNode {
    ArrayList<RoboNode> nodes;

    public AST(ArrayList<RoboNode> list) {
        nodes = list;
    }

    public void visit(AstVisitor<RoboNode> v) {
        for (var child : nodes) {
            v.visit(child);
        }
    };
}
