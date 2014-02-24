public class ArcherAlphaBetaLeaf extends AlphaBetaLeaf {
    public ArcherAlphaBetaLeaf(State state) {
        super(state, false);
    }

    protected int getWeightFromState() {
        return -state.getFootmenHealth();
    }
}
