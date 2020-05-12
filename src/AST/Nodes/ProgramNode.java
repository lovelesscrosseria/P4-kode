package AST.Nodes;

import java.util.ArrayList;

public class ProgramNode extends RoboNode {
    public ArrayList<RoboNode> nodes = new ArrayList<RoboNode>();

    public ProgramNode(ArrayList<RoboNode> nodes) {
        this.nodes = nodes;
    }
}
