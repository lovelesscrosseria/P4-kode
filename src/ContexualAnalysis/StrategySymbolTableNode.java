package ContexualAnalysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class StrategySymbolTableNode extends SymbolTableNode {
    public String Id;
    private HashMap<String, BehaviorSymbolTableNode> behaviors = new HashMap<String, BehaviorSymbolTableNode>();

    public void addBehavior(BehaviorSymbolTableNode behavior) {
        this.behaviors.put(behavior.Id, behavior);
    }

    public BehaviorSymbolTableNode getBehavior(String Id) {
        return this.behaviors.getOrDefault(Id, null);
    }

    public HashMap<String, BehaviorSymbolTableNode> getBehaviors() {
        return this.behaviors;
    }
}
