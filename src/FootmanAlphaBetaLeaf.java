public class FootmanAlphaBetaLeaf extends AlphaBetaLeaf {
    public FootmanAlphaBetaLeaf(State state) {
        super(state, true);
    }

    protected int getWeightFromState() {
        return -state.getArcherHealth();
    }
}
