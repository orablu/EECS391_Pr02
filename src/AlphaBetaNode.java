import java.util.ArrayList;
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
        this.depth = depth;
        this.isMaxNode = isMaxNode;
        this.weight = null;
    }

    public int getWeight() {
        if (this.weight == null) {
            this.generateTree();
            this.weight = this.getBestNode().getWeight();
        }
        return this.weight;
    }

    public List<Action> getActions() {
        List<Action> actions = new ArrayList<>();
        if (state.getAction1().getType() != Action.Type.UNDEFINED) {
            actions.add(state.getAction1());
        }
        if (state.getAction2().getType() != Action.Type.UNDEFINED) {
            actions.add(state.getAction2());
        }
        return actions;
    }

    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    public Node getBestNode() {
        if (this.bestNode == null) {
            this.generateTree();
            this.setBestNode();
        }
        return this.bestNode;
    }

    public List<Node> getBestPath() {
        List<Node> bestPath = this.getBestNode().getBestPath();
        bestPath.add(0, this);
        return bestPath;
    }

    // Create the node, creating any children nodes and pruning as necessary.
    public void generateTree() {
        if (this.bestNode != null) {
            return;
        }
        List<State> possible = this.generatePossibleStates();
        this.generateChildren(possible);
        this.setBestNode();
    }

    private void setBestNode() {
        if (this.isMaxNode) {
            this.bestNode = this.getMaxChild();
        } else {
            this.bestNode = this.getMinChild();
        }
    }

    private void generateChildren(List<State> possible) {
        while (!possible.isEmpty()) {
            // Create the node.
            State state = possible.remove(0);
            Node node;
            if (depth < AlphaBetaNode.maxDepth) {
                node = getChildFromState(state);
            } else {
                node = getLeafFromState(state);
            }
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

    protected boolean isValidPosition(int x, int y) {
        // Check boundaries.
        if (x < State.Min[State.X] || y < State.Min[State.Y]) {
            return false;
        } else if (x >= State.Max[State.X] || y >= State.Max[State.Y]) {
            return false;
        }

        return !isAt(state.getEntities(), x, y);
    }

    protected boolean isAt(List<Unit> entities, int x, int y) {
        // Check if the coordinate is occupied.
        for (Unit e : entities) {
            if (x == e.getXPosition() && y == e.getYPosition()) {
                return false;
            }
        }
        return true;
    }

    // Populates this.possibleStates from this node's state.
    protected abstract List<State> generatePossibleStates();

    // Returns the node/leaf created from the given state.
    protected abstract AlphaBetaNode getChildFromState(State state);
    protected abstract AlphaBetaLeaf getLeafFromState(State state);
}
