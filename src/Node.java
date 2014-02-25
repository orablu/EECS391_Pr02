import java.util.ArrayList;
import java.util.List;

public abstract class Node {
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

    public Node() {
        this.children = new ArrayList<>();
    }

    public Node getChild(int index) {
        return this.children.get(index);
    }

    public Node[] getChildren() {
        return (Node[])this.children.toArray();
    }

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

    public State getState() {
    	return state;
    }

    public abstract int getWeight();
    public abstract List<Node> getBestPath();
}
