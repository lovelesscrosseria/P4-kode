package AST.Nodes.Functions;

import AST.Nodes.RoboNode;
import AST.Nodes.Variables.IdentifierNode;

import java.util.ArrayList;

public class StrategyNode extends RoboNode {
    public ArrayList<BehaviorNode> behaviorNodes = new ArrayList<BehaviorNode>();
    public IdentifierNode Id;
}
