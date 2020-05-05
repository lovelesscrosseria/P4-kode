package ContexualAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class VariableHelper {
    private HashMap<String, VariableSymbolTableNode> globalVariables = new HashMap<String, VariableSymbolTableNode>();
    private HashMap<String, VariableSymbolTableNode> localDeclarations = new HashMap<String, VariableSymbolTableNode>();
    private Stack<ArrayList<VariableSymbolTableNode>> toRemove = new Stack<ArrayList<VariableSymbolTableNode>>();

    public void EnterScope(ArrayList<VariableSymbolTableNode> variables) {
        variables.forEach((x) -> this.localDeclarations.put(x.Id, x));

        this.toRemove.add(variables);
    }
    public void EnterScope() {
        this.toRemove.add(new ArrayList<VariableSymbolTableNode>());
    }

    public void ExitScope() {
        var itemsToRemove = this.toRemove.pop();
        for (var item : itemsToRemove) {
            this.localDeclarations.remove(item.Id);
        }
    }

    public void PutVariable(VariableSymbolTableNode variable) {
        this.globalVariables.put(variable.Id, variable);
    }

    public void PutLocalDeclaration(VariableSymbolTableNode variable) {
        this.localDeclarations.put(variable.Id, variable);
        this.toRemove.peek().add(variable);
    }

    public boolean IsVariableLocal(VariableSymbolTableNode variable) {
        return this.localDeclarations.containsKey(variable.Id);
    }

    public VariableSymbolTableNode GetVariable(String Id) {
        return this.localDeclarations.getOrDefault(Id, this.globalVariables.getOrDefault(Id, null));
    }

    public void clear() {
        this.globalVariables.clear();
        this.localDeclarations.clear();
        this.toRemove.clear();
    }
}
