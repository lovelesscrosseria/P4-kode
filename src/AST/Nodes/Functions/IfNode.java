package AST.Nodes.Functions;

import AST.Nodes.RoboNode;

import java.util.ArrayList;

public class IfNode extends RoboNode {
    public RoboNode expr;
    public BlockNode block;
    public ArrayList<ElseIfNode> ifElseNodes = new ArrayList<ElseIfNode>();
    public ElseNode elseBlock;
}
