package ContexualAnalysis;

import java.util.HashMap;

public class DotOperationMethods {
    private HashMap<String, HashMap<String, FunctionSymbolTableNode>> methods = new HashMap<String, HashMap<String, FunctionSymbolTableNode>>();

    public DotOperationMethods() {
        generateDotOperationMethod("ScannedRobotEvent", "num", "getBearing");
        generateDotOperationMethod("ScannedRobotEvent","num", "getBearingRadians");
        generateDotOperationMethod("ScannedRobotEvent","num", "getDistance");
        generateDotOperationMethod("ScannedRobotEvent","num", "getHealth");
        generateDotOperationMethod("ScannedRobotEvent","num", "getHeading");
        generateDotOperationMethod("ScannedRobotEvent","num", "getHeadingRadians");
        generateDotOperationMethod("ScannedRobotEvent","text", "getName");
        generateDotOperationMethod("ScannedRobotEvent","num", "getVelocity");
        generateDotOperationMethod("ScannedRobotEvent","num", "isSentryRobot");

        // list, dictionary
        generateDotOperationMethod("list", "num", "length");
        generateDotOperationMethod("list","sameAsDef", "get", "num");
        generateDotOperationMethod("list","void", "add", "sameAsDef");

        //List<num> myList
        generateDotOperationMethod("dictionary", "num", "length");
        generateDotOperationMethod("dictionary", "sameAsDef", "get", "sameAsDef");
        generateDotOperationMethod("dictionary", "sameAsDef", "add", "sameAsDef", "sameAsDef");
    }

    private void generateDotOperationMethod(String varType, String methodType, String methodId, String... paramTypes) {
        var method = new FunctionSymbolTableNode();
        method.Type = methodType;
        method.Id = methodId;

        for (int i = 0; i < paramTypes.length; i++) {
            var numberParam = new VariableSymbolTableNode();
            numberParam.Id = "param" + i;
            numberParam.Type = paramTypes[i];
            method.addParam(numberParam);
        }

        if (!methods.containsKey(varType)) {
            methods.put(varType, new HashMap<String, FunctionSymbolTableNode>());
        }
        methods.get(varType).put(methodId, method);
    }

    public FunctionSymbolTableNode getDotOperationMethod(String type, String id) {
        if (this.methods.containsKey(type)) {
            var typeMethods = this.methods.get(type);
            if (typeMethods.containsKey(id)) {
                return typeMethods.get(id);
            }
        }
        return null;
    }

    public FunctionSymbolTableNode getDotOperationMethod(String id, int numberOfParams) {
        for (var method : this.methods.values()) {
            if (method.containsKey(id) && method.get(id).getNumberOfParams() == numberOfParams) {
                return method.get(id);
            }
        }

        return null;
    }
}
