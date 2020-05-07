package AST.Nodes.Loops;

import AST.Nodes.Functions.BlockNode;
import AST.Nodes.RoboNode;

import java.util.UUID;

public class WhileLoopNode extends RoboNode {
    public final String LoopId;
    public RoboNode Condition;
    public BlockNode Block;

    public WhileLoopNode() {
        this.LoopId = UUID.randomUUID().toString();
    }
}
