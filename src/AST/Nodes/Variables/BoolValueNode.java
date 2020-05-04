package AST.Nodes.Variables;

import AST.Nodes.RoboNode;

public class BoolValueNode extends RoboNode{
    public String Value;

    public BoolValueNode() {
        this.Type = new TypeNode();
        this.Type.Type = "bool";
    }
}
