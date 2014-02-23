
public class ArcherAlphaBetaLeaf extends AlphaBetaLeaf {
    public ArcherAlphaBetaLeaf(State state, boolean isMaxNode) {
        super(state, isMaxNode);
    }

    protected int getWeightFromState() {
        return 0;
    }
}
