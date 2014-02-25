import java.util.ArrayList;
import java.util.List;

/**
 * A node in a tree.
 *
 * @author wkr3, jxb532
 */
public abstract class Node {
    ///
    /// Logging utility
    ///

    public static enum Level {
        Critical(0), Essential(1), High(2), Moderate(3), Low(4), Trivial(5);
        private final int value;
        private Level(int value) { this.value = value; }
        public int getValue() { return value; }
    }
    public static Level Logging = Level.Essential;
    public static void Log(String text) { Log(text, Level.Essential); }
    public static void Log(String text, Level level) {
        if (level.getValue() <= Logging.getValue()) {
            System.out.println(text);
        }
    }


    protected List<Node> children;
    protected State state;

    /**
     * Creates a new node.
     */
    public Node() {
        this.children = new ArrayList<>();
    }

    /**
     * Gets the child at the given index.
     *
     * @param index
     * @return The child at the given index
     */
    public Node getChild(int index) {
        return this.children.get(index);
    }

    /**
     * Gets the node's children.
     *
     * @return The node's children
     */
    public Node[] getChildren() {
        return (Node[])this.children.toArray();
    }

    /**
     * Gets the node's child with the max weight.
     *
     * @return The child with the max weight
     */
    public Node getMaxChild() {
        int max = Integer.MIN_VALUE;
        Node maxNode = null;
        for (Node n : this.children) {
            int weight = n.getWeight();
            if (weight > max) {
                max = weight;
                maxNode = n;
            }
        }
        return maxNode;
    }

    /**
     * Gets the node's child with the min weight.
     *
     * @return The child with the min weight
     */
    public Node getMinChild() {
        int min = Integer.MAX_VALUE;
        Node minNode = null;
        for (Node n : this.children) {
            int weight = n.getWeight();
            if (weight < min) {
                min = weight;
                minNode = n;
            }
        }
        return minNode;
    }

    /**
     * Gets the state used to generate the node and its children.
     *
     * @return The state used to generate the node and its children
     */
    public State getState() {
        return state;
    }

    /**
     * Gets the weight of the node.
     *
     * @return The weight of the node
     */
    public abstract int getWeight();

    /**
     * Gets the best path through the node's children to a leaf.
     *
     * @return The best path, in order of root to leaf
     */
    public abstract List<Node> getBestPath();
}
