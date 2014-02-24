import java.util.List;
import java.util.ArrayList;

public abstract class AlphaBetaLeaf extends Node {
    private Integer weight;
    protected State state;
    protected boolean isMaxNode;

    public AlphaBetaLeaf(State state, boolean isMaxNode) {
        super();
        this.weight = null;
        this.state = state;
        this.isMaxNode = isMaxNode;
    }

    public int getWeight() {
        if (this.weight == null) {
            this.weight = this.getWeightFromState();
        }
        return this.weight;
    }

    public List<Node> getBestPath() {
        List<Node> bestPath = new ArrayList<>();
        bestPath.add(0, this);
        return bestPath;
    }
    
    public State getState() {
    	return state;
    }

    protected abstract int getWeightFromState();
}
