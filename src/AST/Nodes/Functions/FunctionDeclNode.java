package AST.Nodes.Functions;

import AST.Nodes.RoboNode;
import AST.Nodes.Variables.IdentifierNode;
import AST.Nodes.Variables.TypeNode;

import java.util.ArrayList;

public class  FunctionDeclNode extends RoboNode {
    public TypeNode Type;
    public IdentifierNode Id;
    public ArrayList<FormalParamNode> Params;
    public BlockNode block;

    public FunctionDeclNode(ArrayList<FormalParamNode> params) {
        this.Params = params;
    }
    public FunctionDeclNode() {

    }
}
