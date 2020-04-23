package AST.Nodes.Variables;

import AST.Nodes.RoboNode;

import java.util.ArrayList;

public class ListDeclNode extends RoboNode {
    public ArrayList<RoboNode> nodes = new ArrayList<RoboNode>();
    public TypeNode Type;
    public IdentifierNode Id;
}
