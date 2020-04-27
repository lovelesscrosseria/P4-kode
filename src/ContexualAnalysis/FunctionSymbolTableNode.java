package ContexualAnalysis;

import java.util.ArrayList;

public class FunctionSymbolTableNode {
    public String Id;
    public String Type;
    private ArrayList<VariableSymbolTableNode> params = new ArrayList<VariableSymbolTableNode>();
    private ArrayList<VariableSymbolTableNode> localDecl = new ArrayList<VariableSymbolTableNode>();

    public void addParam(VariableSymbolTableNode param) {
        this.params.add(param);
    }

    public ArrayList<VariableSymbolTableNode> getParams() {
        return this.params;
    }

    public void addLocalVariableDeclaration(VariableSymbolTableNode param) {
        this.localDecl.add(param);
    }

    public ArrayList<VariableSymbolTableNode> getLocalVariables() {
        return this.localDecl;
    }
}
