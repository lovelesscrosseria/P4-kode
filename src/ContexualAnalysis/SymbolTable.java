package ContexualAnalysis;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    private HashMap<String, VariableSymbolTableNode> variables = new HashMap<String, VariableSymbolTableNode>();
    private HashMap<String, FunctionSymbolTableNode> functions = new HashMap<String, FunctionSymbolTableNode>();
    private HashMap<String, StrategySymbolTableNode> strategies = new HashMap<String, StrategySymbolTableNode>();

    public void PutVariable(VariableSymbolTableNode variable) {
        this.variables.put(variable.Id, variable);

    }

    public VariableSymbolTableNode GetVariable(String Id) {
        return this.variables.getOrDefault(Id, null);
    }

    public void PutStrategy(StrategySymbolTableNode strategy) {
        this.strategies.put(strategy.Id, strategy);

    }

    public StrategySymbolTableNode GetStrategy(String Id) {
        return this.strategies.getOrDefault(Id, null);
    }

    public void PutFunction(FunctionSymbolTableNode func) {
        this.functions.put(func.Id, func);

    }
    public FunctionSymbolTableNode GetFunction(String Id) {
        return this.functions.getOrDefault(Id, null);
    }

    public void clear() {
        this.variables.clear();
        this.functions.clear();
        this.strategies.clear();
    }

}
