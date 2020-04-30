package AST.Nodes.Variables;

import AST.Nodes.Functions.FunctionCallNode;
import AST.Nodes.RoboNode;

public class DotOperationExprNode extends RoboNode {
    public IdentifierNode Id;
    public FunctionCallNode Method;
}
