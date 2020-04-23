package AST.Nodes.Variables;

import AST.Nodes.RoboNode;

public class VariableDeclNode extends RoboNode {
    public TypeNode Type;
    public IdentifierNode Id;
    public RoboNode Value;
}
