package ContexualAnalysis;

import java.util.HashMap;

public class FunctionSymbolTableNode {
    public String Id;
    public String Type;
    private HashMap<String, VariableSymbolTableNode> params = new HashMap<String, VariableSymbolTableNode>();
    private HashMap<String, VariableSymbolTableNode> localDecl = new HashMap<String, VariableSymbolTableNode>();

    public void addParam(VariableSymbolTableNode param) {
        this.params.put(param.Id, param);
    }

    public VariableSymbolTableNode getParam(String Id) {
        return this.params.getOrDefault(Id, null);
    }

    public void addLocalVariableDeclaration(VariableSymbolTableNode param) {
        this.localDecl.put(param.Id, param);
    }

    public VariableSymbolTableNode getLocalVariable(String Id) {
        return this.localDecl.getOrDefault(Id, null);
    }
}
