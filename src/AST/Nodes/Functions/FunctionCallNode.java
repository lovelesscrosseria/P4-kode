package AST.Nodes.Functions;

import AST.Nodes.RoboNode;
import AST.Nodes.Variables.IdentifierNode;

import java.util.ArrayList;

public class FunctionCallNode extends RoboNode {
    public IdentifierNode Method;
    public ArrayList<ParamNode> Params = new ArrayList<ParamNode>();
}
