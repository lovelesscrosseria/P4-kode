package AST;

import AST.Nodes.ProgramNode;
import AST.Nodes.RoboNode;
import ContexualAnalysis.SymbolTable;

import java.util.ArrayList;

public class AST extends RoboNode {
    public static SymbolTable symbolTable = new SymbolTable();
    public static ArrayList<String> errors = new ArrayList<String>();

    //private ArrayList<RoboNode> nodes;
    private ProgramNode rootNode;
    public AST(ProgramNode node) {
        this.rootNode = node;
    }

    public void visit(AstVisitor<RoboNode> v) {
        /*for (var child : nodes) {
            v.visit(child);
        }*/
        v.visit(this.rootNode);
    };
}
