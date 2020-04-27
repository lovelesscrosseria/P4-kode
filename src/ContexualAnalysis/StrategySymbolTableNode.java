package ContexualAnalysis;

import java.util.ArrayList;

public class StrategySymbolTableNode {
    public String Id;
    private ArrayList<BehaviorSymbolTableNode> behaviors = new ArrayList<BehaviorSymbolTableNode>();

    public void addBehavior(BehaviorSymbolTableNode behavior) {
        this.behaviors.add(behavior);
    }
}
