import java.util.List;
import java.util.ArrayList;

public abstract class AlphaBetaNode extends Node {
    private State state;
    private boolean isMaxNode;
    private Node bestNode;
    Integer weight;

    public AlphaBetaNode(State state, boolean isMaxNode) {
        super();
        this.state = state;
        this.isMaxNode = isMaxNode;

        // Create the node, creating any children nodes and pruning as necessary.
        this.weight = Integer.MAX_VALUE;
        List<State> possible = this.generatePossibleStates();
        this.generateChildren(possible);
        this.setBestNode();
    }

    public int getWeight() {
        if (this.weight == null) {
            this.weight = this.bestNode.getWeight();
        }
        return this.weight;
    }

    public State getState() {
        return this.state;
    }

    private void setBestNode() {
        if (this.isMaxNode) {
            this.weight = this.bestNode.getWeight();
        } else {
            this.bestNode = this.getMinChild();
        }
        this.bestNode = this.getMaxChild();
    }

    // Populates this.possibleStates
    private abstract List<State> generatePossibleStates();

    private void generateChildren(List<State> possible) {
        // TODO: This is where the minimax happens.
    }

    // Returns whether or not this node warrants a/b pruning.
    private boolean addChildFromState(State state) {
        // TODO: This is where a child node is created and analyzed.
        return false;
    }

    private void prune() {
        // TODO: This prunes the tree if necessary
    }
}
