import java.util.List;

public abstract class AlphaBetaNode extends Node {
    protected boolean isMaxNode;
    protected State state;
    private Node bestNode;
    private Integer weight;

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

    private void setBestNode() {
        if (this.isMaxNode) {
            this.weight = this.bestNode.getWeight();
        } else {
            this.bestNode = this.getMinChild();
        }
        this.bestNode = this.getMaxChild();
    }

    private void generateChildren(List<State> possible) {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        while (!possible.isEmpty()) {
            // Create the node.
            State state = possible.remove(0);
            Node node = getChildFromState(state);
            this.children.add(node);

            // Alpha Beta pruning.
            int weight = node.getWeight();
            if (this.isMaxNode) {
                if (weight < alpha) {
                    break;
                } else {
                    beta = Math.min(weight, beta);
                }
            } else {
                if (weight > beta) {
                    break;
                } else {
                    alpha = Math.max(weight, alpha);
                }
            }
        }
    }

    // Populates this.possibleStates
    protected abstract List<State> generatePossibleStates();

    // Returns the created node.
    protected abstract Node getChildFromState(State state);
}
