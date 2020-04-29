package ContexualAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class VariableHelper {
    public HashMap<String, VariableSymbolTableNode> localDeclarations = new HashMap<String, VariableSymbolTableNode>();
    private Stack<ArrayList<VariableSymbolTableNode>> toRemove = new Stack<ArrayList<VariableSymbolTableNode>>();

    public void AddLocalDeclaration(VariableSymbolTableNode variable) {

    }

    public void EnterScope(ArrayList<VariableSymbolTableNode> variables) {

    }

    public void ExitScope() {
        // stack.pop
            // remove each toRemove from localDeclarations
    }
}
