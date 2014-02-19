import java.util.List;

public abstract class AlphaBetaNode extends Node {
    private static int alpha, beta;
    private static int maxDepth;

    protected boolean isMaxNode;
    protected int depth;
    protected State state;
    private Node bestNode;
    private Integer weight;

    public static void setupSearch(int depth) {
        AlphaBetaNode.alpha = Integer.MIN_VALUE;
        AlphaBetaNode.beta = Integer.MAX_VALUE;
        AlphaBetaNode.maxDepth = depth;
    }

    public AlphaBetaNode(State state, int depth, boolean isMaxNode) {
        super();
        this.state = state;
        this.isMaxNode = isMaxNode;

        // Create the node, creating any children nodes and pruning as necessary.
        this.weight = Integer.MAX_VALUE;
        List<State> possible = this.generatePossibleStates();
        if (depth < AlphaBetaNode.maxDepth) {
            this.generateChildren(possible);
            this.setBestNode();
        } else {
            this.bestNode = null;
        }
    }

    public int getWeight() {
        if (this.weight == null) {
            if (this.bestNode != null) {
                this.weight = this.bestNode.getWeight();
            } else {
                this.weight = this.isMaxNode ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
        }
        return this.weight;
    }

    private void setBestNode() {
        if (this.isMaxNode) {
            this.bestNode = this.getMaxChild();
        } else {
            this.bestNode = this.getMinChild();
        }
        this.weight = this.bestNode.getWeight();
    }

    private void generateChildren(List<State> possible) {
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

    // Populates this.possibleStates from this node's state.
    protected abstract List<State> generatePossibleStates();

    // Returns the node created from the given state.
    protected abstract Node getChildFromState(State state);
}
