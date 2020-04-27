package AST;

import AST.Nodes.RoboNode;
import ContexualAnalysis.SymbolTable;

import java.util.ArrayList;

public class AST extends RoboNode {
    public static SymbolTable symbolTable = new SymbolTable();
    public static ArrayList<String> errors = new ArrayList<String>();

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
