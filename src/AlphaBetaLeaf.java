import java.util.List;
import java.util.ArrayList;

public abstract class AlphaBetaLeaf extends Node {
    private Integer weight;

    public AlphaBetaLeaf(State state, boolean isMaxNode) {
        super();
        weight = null;
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

    protected abstract int getWeightFromState();
}
