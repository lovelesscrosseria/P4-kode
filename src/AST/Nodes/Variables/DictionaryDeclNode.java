package AST.Nodes.Variables;

import AST.Nodes.RoboNode;

import java.util.ArrayList;

public class DictionaryDeclNode extends RoboNode {
    public TypeNode key;
    public TypeNode value;
    public IdentifierNode Id;
    public ArrayList<DictionaryValueNode> Nodes = new ArrayList<DictionaryValueNode>();
}
