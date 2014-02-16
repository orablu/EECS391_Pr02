public abstract class AlphaBetaLeaf extends Node {
    private Integer weight;

    public AlphaBetaLeaf(State state, boolean isMaxNode) {
        super();
        weight = null;
    }

    public int getWeight() {
        if (this.weight == null) {
            this.weight = this.getWeightFromState();
        }
        return this.weight;
    }

    protected abstract int getWeightFromState();
}
