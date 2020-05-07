package ContexualAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public abstract class MethodSymbolTableNode extends SymbolTableNode  {
    public String Id;
    public String Type = "";
    private LinkedHashMap<String, VariableSymbolTableNode> params = new LinkedHashMap<String, VariableSymbolTableNode>();
    private HashMap<String, VariableSymbolTableNode> localDecl = new HashMap<String, VariableSymbolTableNode>();

    public void addParam(VariableSymbolTableNode param) {
        this.params.put(param.Id, param);
    }

    public VariableSymbolTableNode getParam(String Id) {
        return this.params.getOrDefault(Id, null);
    }

    public HashMap<String, VariableSymbolTableNode> getParams() {
        return this.params;
    }

    public HashMap<String, VariableSymbolTableNode> getLocalVariables() {
        return this.localDecl;
    }

    public void addLocalVariableDeclaration(VariableSymbolTableNode param) {
        this.localDecl.put(param.Id, param);
    }

    public VariableSymbolTableNode getLocalVariable(String Id) {
        return this.localDecl.getOrDefault(Id, null);
    }

    public int getNumberOfParams() {
        return this.params.size();
    }
    public ArrayList<VariableSymbolTableNode> GetParams() {
        var paramList = new ArrayList<VariableSymbolTableNode>();

        params.forEach((k, v) -> paramList.add(v));

        return paramList;
    }

    public void enterBlock() {
        this.localDecl.forEach((k, v) -> v.scopeHealth += 1);
    }

    public void exitBlock() {
        this.localDecl.forEach((k, v) -> v.scopeHealth -= 1);

    }
}
