import java.util.List;
import java.util.ArrayList;

public abstract class Node {
    protected List<Node> children;
    protected Action action1;
    protected Action action2;

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

    public abstract int getWeight();
    public abstract List<Node> getBestPath();
}
