package AST.Nodes.Functions;

import AST.Nodes.RoboNode;
import AST.Nodes.Variables.IdentifierNode;
import AST.Nodes.Variables.TypeNode;

public class FormalParamNode extends RoboNode {
    public TypeNode Type;
    public IdentifierNode Id;
}
