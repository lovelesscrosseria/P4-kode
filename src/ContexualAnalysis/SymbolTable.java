package ContexualAnalysis;

import java.util.ArrayList;

public class SymbolTable {
    private ArrayList<VariableSymbolTableNode> variables = new ArrayList<VariableSymbolTableNode>();
    private ArrayList<FunctionSymbolTableNode> functions = new ArrayList<FunctionSymbolTableNode>();
    private ArrayList<StrategySymbolTableNode> strategies = new ArrayList<StrategySymbolTableNode>();

    public void PutVariable(VariableSymbolTableNode variable) {
        this.variables.add(variable);

    }

    public ArrayList<VariableSymbolTableNode> GetVariables() {
        return this.variables;
    }

    public void PutStrategy(StrategySymbolTableNode strategy) {
        this.strategies.add(strategy);

    }

    public ArrayList<StrategySymbolTableNode> GetStrategies() {
        return this.strategies;
    }

    public void PutFunction(FunctionSymbolTableNode func) {
        this.functions.add(func);

    }

    public ArrayList<FunctionSymbolTableNode> Getfunctions() {
        return this.functions;
    }

    public void clear() {
        this.variables.clear();
        this.functions.clear();
        this.strategies.clear();
    }

}
