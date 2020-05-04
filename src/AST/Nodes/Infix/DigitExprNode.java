package AST.Nodes.Infix;

import AST.Nodes.RoboNode;
import AST.Nodes.Variables.TypeNode;

public class DigitExprNode extends RoboNode {
    public double value;

    public DigitExprNode() {
        this.Type = new TypeNode();
        this.Type.Type = "num";
    }
}
