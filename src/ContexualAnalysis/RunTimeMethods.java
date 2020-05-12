package ContexualAnalysis;

import java.util.HashMap;

public class RunTimeMethods {
    private HashMap<String, FunctionSymbolTableNode> methods = new HashMap<String, FunctionSymbolTableNode>();

    public RunTimeMethods() {
        generateRuntimeMethod("void", "print", "text");
    }

    private void generateRuntimeMethod(String methodType, String methodId, String... paramTypes) {
        var method = new FunctionSymbolTableNode();
        method.Type = methodType;
        method.Id = methodId;

        for (var paramType : paramTypes) {
            var numberParam = new VariableSymbolTableNode();
            numberParam.Type = paramType;
            method.addParam(numberParam);
        }

        methods.put(method.Id, method);
    }

    public FunctionSymbolTableNode getRuntimeMethod(String id) {
        return this.methods.getOrDefault(id, null);
    }
}
