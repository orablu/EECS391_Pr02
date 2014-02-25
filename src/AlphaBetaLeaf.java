import java.util.ArrayList;
import java.util.List;

/**
 * A leaf node in a minimax tree that uses alpha beta pruning.
 *
 * @author
 */
public class AlphaBetaLeaf extends Node {
    private Integer weight;
    protected boolean isMaxNode;

    /**
     * Creates a new AlphaBetaNode.
     *
     * @param state The state used to generate this node and its children
     * @param isMaxNode True if this is a node that should be maximized, otherwise false
     */
    public AlphaBetaLeaf(State state, boolean isMaxNode) {
        super();
        this.weight = null;
        this.state = state;
        this.isMaxNode = isMaxNode;
    }

    /**
     * {@inheritDoc}
     * @see Node#getWeight()
     */
    public int getWeight() {
        if (this.weight == null) {
            this.weight = this.getWeightFromState();
        }
        return this.weight;
    }

    /**
     * {@inheritDoc}
     * @see Node#getBestPath()
     */
    public List<Node> getBestPath() {
        List<Node> bestPath = new ArrayList<>();
        bestPath.add(0, this);
        return bestPath;
    }

    /**
     * Gets the weight from the node's state.
     *
     * @return The weight of the leaf node
     */
    protected int getWeightFromState() {
        return state.getStateUtility();
    }
    
    /**
     * @return A string representing the node.
     */
    @Override
    public String toString() {
        String s = isMaxNode ? "Max" : "Min";
        return s + " Leaf with weight " + getWeight() + " and state: \n\t" + state + "\n";
    }
}
