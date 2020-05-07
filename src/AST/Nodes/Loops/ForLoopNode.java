package AST.Nodes.Loops;

import AST.Nodes.Functions.BlockNode;
import AST.Nodes.RoboNode;

import java.util.UUID;

public class ForLoopNode extends RoboNode {
    public final String LoopId;
    public RoboNode Init;
    public RoboNode Condition;
    public RoboNode Increment;
    public BlockNode Block;

    public ForLoopNode() {
        this.LoopId = UUID.randomUUID().toString();
    }
}
