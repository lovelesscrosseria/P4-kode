package ContexualAnalysis;

import java.util.HashMap;

public class RunTimeRobocodeMethods {
    private HashMap<String, FunctionSymbolTableNode> methods = new HashMap<String, FunctionSymbolTableNode>();

    public RunTimeRobocodeMethods() {
        generateRobocodeMethod("void", "ahead", "num");
        generateRobocodeMethod("void", "back","num");
        generateRobocodeMethod("void", "doNothing");
        generateRobocodeMethod("void", "fire", "num");
        generateRobocodeMethod("num", "health");
        generateRobocodeMethod("void", "changeStrategy", "text");
        generateRobocodeMethod("void", "scan");
        generateRobocodeMethod("void", "turnLeft", "num");
        generateRobocodeMethod("void", "turnRight", "num");
        generateRobocodeMethod("void", "turnGunLeft", "num");
        generateRobocodeMethod("void", "turnGunRight", "num");;
        generateRobocodeMethod("void", "turnRadarLeft", "num");
        generateRobocodeMethod("void", "turnRadarRight", "num");
        generateRobocodeMethod("void", "stop");
    }

    private void generateRobocodeMethod(String methodType, String methodId, String... paramTypes) {
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
