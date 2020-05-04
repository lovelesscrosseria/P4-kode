package AST.Nodes.Infix;

import AST.Nodes.RoboNode;
import AST.Nodes.Variables.TypeNode;

public class StringExprNode extends RoboNode {
    public String value;

    public StringExprNode() {
        this.Type = new TypeNode();
        this.Type.Type = "text";
    }
}
