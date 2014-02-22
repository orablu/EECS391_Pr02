
public class FootmanAlphaBetaLeaf extends AlphaBetaLeaf {
    public FootmanAlphaBetaLeaf(State state, boolean isMaxNode) {
        super(state, isMaxNode);
    }

    protected int getWeightFromState() {
        return 0;
    }
}
