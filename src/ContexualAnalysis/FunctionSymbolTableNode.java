package ContexualAnalysis;

import java.util.ArrayList;

public class FunctionSymbolTableNode {
    public String Id;
    private ArrayList<VariableSymbolTableNode> params = new ArrayList<VariableSymbolTableNode>();
    private ArrayList<VariableSymbolTableNode> localDecl = new ArrayList<VariableSymbolTableNode>();

    public void addParam(VariableSymbolTableNode param) {
        this.params.add(param);
    }

    public ArrayList<VariableSymbolTableNode> getParams() {
        return this.params;
    }

    public void addLocalVarDeclaration(VariableSymbolTableNode param) {
        this.localDecl.add(param);
    }

    public ArrayList<VariableSymbolTableNode> getLocalVarDeclaration() {
        return this.localDecl;
    }
}
