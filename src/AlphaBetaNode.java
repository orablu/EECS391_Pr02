import java.util.ArrayList;
import java.util.List;

/**
 * A node in a minimax tree that uses alpha beta pruning.
 *
 * @author wkr3, jxb532
 */
public abstract class AlphaBetaNode extends Node {
    private static int alpha, beta;
    private static int maxDepth;

    protected boolean isMaxNode;
    protected int depth;
    private Node bestNode;
    private Integer weight;

    /**
     * Sets up the search parameters to begin a new search.
     *
     * @param depth The maximum depth of any branch
     */
    public static void setupSearch(int depth) {
        AlphaBetaNode.alpha = Integer.MIN_VALUE;
        AlphaBetaNode.beta = Integer.MAX_VALUE;
        AlphaBetaNode.maxDepth = depth;
    }

    /**
     * Creates a new AlphaBetaNode.
     *
     * @param state The state used to generate this node and its children
     * @param depth The current depth of the node
     * @param isMaxNode True if this is a node that should be maximized, otherwise false
     */
    public AlphaBetaNode(State state, int depth, boolean isMaxNode) {
        super();
        this.state = state;
        this.depth = depth;
        this.isMaxNode = isMaxNode;
        this.weight = null;
    }

    /**
     * {@inheritDoc}
     * @see Node#getWeight()
     */
    public int getWeight() {
        if (this.weight == null) {
            this.generateTree();
            this.weight = this.getBestNode().getWeight();
        }
        return this.weight;
    }

    /**
     * Gets the actions taken to get to this node.
     *
     * @return A list containing all actions taken to create this node
     */
    public List<StateAction> getActions() {
        List<StateAction> actions = new ArrayList<>();
        if (state.getAction1().getType() != StateAction.Type.UNDEFINED) {
            actions.add(state.getAction1());
        }
        if (state.getAction2().getType() != StateAction.Type.UNDEFINED) {
            actions.add(state.getAction2());
        }
        return actions;
    }

    /**
     * Gets the node's best child, based on weights.
     *
     * @return The best node found
     */
    public Node getBestNode() {
        if (this.bestNode == null) {
            this.generateTree();
            this.setBestNode();
        }
        return this.bestNode;
    }

    /**
     * {@inheritDoc}
     * @see Node#getBestPath()
     */
    public List<Node> getBestPath() {
        List<Node> bestPath = this.getBestNode().getBestPath();
        bestPath.add(0, this);
        return bestPath;
    }

    /**
     * Create the node, creating any child nodes and pruning as necessary.
     */
    public void generateTree() {
        if (this.bestNode != null) {
            return;
        }
        Log("GENERATING TREE", Level.Low);
        List<State> possible = this.generatePossibleStates();
        this.generateChildren(possible);
        this.setBestNode();
    }

    /**
     * Sets the best node out of this node's children.
     *
     */
    private void setBestNode() {
        if (this.isMaxNode) {
            this.bestNode = this.getMaxChild();
        } else {
            this.bestNode = this.getMinChild();
        }
        
        String s = isMaxNode ? "Max" : "Min";
        Log("Set best " + s + " node with weight of: " + bestNode.getWeight(), Level.Low);
    }

    /**
     * Generates all the children for this node, stopping if pruning is necessary.
     *
     * @param possible The list of possible states to generate children from
     */
    private void generateChildren(List<State> possible) {
        Log("GENERATING CHILDREN", Level.Low);
        Log("Using " + possible.size() + " states", Level.Low);
        while (!possible.isEmpty()) {
            // Create the node.
            State state = possible.remove(0);
            Node node;
            if (depth < AlphaBetaNode.maxDepth) {
                node = getChildFromState(state);
            } else {
                node = getLeafFromState(state);
            }
            Log("\nGENERATING CHILD: " + node, Level.Trivial);
            this.children.add(node);

            // Alpha Beta pruning.
            int weight = node.getWeight();
            if (this.isMaxNode) {
                if (weight < alpha) {
                    Log("---PRUNING!---", Level.Low);
                    break;
                } else {
                    beta = Math.min(weight, beta);
                }
            } else {
                if (weight > beta) {
                    Log("---PRUNING!---", Level.Low);
                    break;
                } else {
                    alpha = Math.max(weight, alpha);
                }
            }
        }
    }

    /**
     * Returns whether the given position is valid to move to.
     * 
     * @param x The x coordinate
     * @param y The y coordinate
     * @return True if the coordinates can be moved to
     */
    protected boolean isValidPosition(int x, int y) {
        // Check boundaries.
        Log("Checking if " + x + "," + y + " is valid position", Level.Low);
        if (x < State.Min[State.X] || y < State.Min[State.Y]) {
            Log("\tToo small for bounds " + State.Min[State.X] + "," + State.Min[State.Y], Level.High);
            return false;
        } else if (x >= State.Max[State.X] || y >= State.Max[State.Y]) {
            Log("\tToo big for bounds " + State.Max[State.X] + "," + State.Max[State.Y], Level.High);
            return false;
        }

        Log("\tIn bounds, checking if anything is there", Level.Low);
        return !isAt(state.getEntities(), x, y);
    }

    /**
     * Returns whether any of the given entities are at the given position.
     * 
     * @param entities The entities to check for
     * @param x The x coordinate
     * @param y The y coordinate
     * @return True if there is an entity at the given coordinates
     */
    protected boolean isAt(List<Unit> entities, int x, int y) {
        // Check if the coordinate is occupied.
        for (Unit e : entities) {
            if (x == e.getXPosition() && y == e.getYPosition()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return A string representing the node.
     */
    @Override
    public String toString() {
        String mm = isMaxNode ? "Max" : "Min";
        String s = mm + " Node with state: \n\t" + state + "\n";
        s += "Depth: " + depth + "\n";
        return s;
    }

    /**
     * Populates this.possibleStates from this node's state.
     *
     * @return The generated states
     */
    protected abstract List<State> generatePossibleStates();

    /**
     * Creates a node from the given state.
     *
     * @param state The state to generate the node from
     * @return A new node gnerated from the given state
     */
    protected abstract AlphaBetaNode getChildFromState(State state);

    /**
     * Creates a leaf node from the given state.
     *
     * @param state The state to generate the leaf node from
     * @return A new leaf node gnerated from the given state
     */
    protected abstract AlphaBetaLeaf getLeafFromState(State state);
}
