import java.util.ArrayList;
import java.util.List;

public abstract class AlphaBetaNode extends Node {
    private static int alpha, beta;
    private static int maxDepth;

    protected boolean isMaxNode;
    protected int depth;
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
        Log("GENERATING TREE", Level.Low);
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
    	Log("GENERATING CHILDREN", Level.Moderate);
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

    /**
     * 
     * @param x
     * @param y
     * @return True if the coordinates can be moved to
     */
    protected boolean isValidPosition(int x, int y) {
        // Check boundaries.
    	Log("Checking if " + x + "," + y + " is valid position", Level.Moderate);
        if (x < State.Min[State.X] || y < State.Min[State.Y]) {
        	Log("\tToo small for bounds " + State.Min[State.X] + "," + State.Min[State.Y], Level.High);
            return false;
        } else if (x >= State.Max[State.X] || y >= State.Max[State.Y]) {
        	Log("\tToo big for bounds " + State.Max[State.X] + "," + State.Max[State.Y], Level.High);
            return false;
        }

        Log("\tIn bounds, checking if anything is there", Level.Moderate);
        return !isAt(state.getEntities(), x, y);
    }

    /**
     * 
     * @param entities
     * @param x
     * @param y
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
    
    @Override
    public String toString() {
    	String s = "Node with state: \n\t" + state + "\n";
    	s += "Depth: " + depth + "\n";
    	return s;
    }

    // Populates this.possibleStates from this node's state.
    protected abstract List<State> generatePossibleStates();

    // Returns the node/leaf created from the given state.
    protected abstract AlphaBetaNode getChildFromState(State state);
    protected abstract AlphaBetaLeaf getLeafFromState(State state);
}
