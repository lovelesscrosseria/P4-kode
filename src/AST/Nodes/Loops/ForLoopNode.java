package AST.Nodes.Loops;

import AST.Nodes.Functions.BlockNode;
import AST.Nodes.RoboNode;

public class ForLoopNode extends RoboNode {
    public RoboNode Init;
    public RoboNode Condition;
    public RoboNode Increment;
    public BlockNode Block;
}
