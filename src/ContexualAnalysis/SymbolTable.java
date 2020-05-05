package ContexualAnalysis;

import AST.Nodes.Variables.VariableDeclNode;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    private VariableHelper variables = new VariableHelper();
    private HashMap<String, FunctionSymbolTableNode> functions = new HashMap<String, FunctionSymbolTableNode>();
    private HashMap<String, StrategySymbolTableNode> strategies = new HashMap<String, StrategySymbolTableNode>();
    private HashMap<String, EventSymbolTableNode> events = new HashMap<String, EventSymbolTableNode>();
    private RunTimeRobocodeMethods roboCodeRuntimeMethods = new RunTimeRobocodeMethods();

    public void PutVariable(VariableSymbolTableNode variable) {
        this.variables.PutVariable(variable);
    }

    public VariableSymbolTableNode GetVariable(String Id) {
        return this.variables.GetVariable(Id);
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

    public void PutEvent(EventSymbolTableNode event) {
        this.events.put(event.Id, event);
    }
    public EventSymbolTableNode GetEvent(String Id) {
        return this.events.getOrDefault(Id, null);
    }

    public void EnterScope(ArrayList<VariableSymbolTableNode> variables) {
        this.variables.EnterScope(variables);
    }
    public void EnterScope() { this.variables.EnterScope(); }

    public void ExitScope() {
        this.variables.ExitScope();
    }

    public void PutLocalDeclaration(VariableSymbolTableNode variable) {
        this.variables.PutLocalDeclaration(variable);
    }
    public boolean IsVariableLocal(VariableSymbolTableNode variable) { return this.variables.IsVariableLocal(variable); }

    public void EnterFunction(MethodSymbolTableNode func) {
         var variables = new ArrayList<VariableSymbolTableNode>(func.getParams().values());
         variables.addAll(func.getLocalVariables().values());

         this.EnterScope(variables);
    }

    public void ExitFunction() {
        this.ExitScope();
    }

    public FunctionSymbolTableNode getRobocodeRuntimeMethod(String id) {
        return roboCodeRuntimeMethods.getRuntimeMethod(id);
    }

    public void clear() {
        this.functions.clear();
        this.strategies.clear();
        this.events.clear();
    }
}
