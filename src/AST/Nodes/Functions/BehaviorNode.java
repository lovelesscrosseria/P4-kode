package AST.Nodes.Functions;

import AST.Nodes.RoboNode;
import AST.Nodes.Variables.IdentifierNode;

import java.util.ArrayList;

public class BehaviorNode extends RoboNode {
    public IdentifierNode Id;
    public ArrayList<FormalParamNode> Params;
    public BlockNode Block;
}
