package ContexualAnalysis;

public class DotOperationMethod extends FunctionSymbolTableNode {

    public DotOperationMethod(String methodType, String methodId, String... paramTypes) {
        var method = new FunctionSymbolTableNode();
        this.Type = methodType;
        this.Id = methodId;

        for (var paramType : paramTypes) {
            var numberParam = new VariableSymbolTableNode();
            numberParam.Type = paramType;
            this.addParam(numberParam);
        }
    }
}
