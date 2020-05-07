package ContexualAnalysis;

import java.util.HashMap;

public class DictionaryVariableSymbolTableNode extends VariableSymbolTableNode {
    public String KeyType;
    public String ValueType;
    public HashMap<String, DictionaryValue> Nodes = new HashMap<String, DictionaryValue>();
}
