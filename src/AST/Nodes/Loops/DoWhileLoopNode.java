package AST.Nodes.Loops;

import AST.Nodes.Functions.BlockNode;
import AST.Nodes.RoboNode;

import java.util.UUID;

public class DoWhileLoopNode extends RoboNode {
    public final String LoopId;
    public RoboNode Condition;
    public BlockNode Block;

    public DoWhileLoopNode() {
        this.LoopId = UUID.randomUUID().toString();
    }
}
