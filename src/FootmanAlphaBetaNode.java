import java.util.List;

public class FootmanAlphaBetaNode extends AlphaBetaNode {
    public FootmanAlphaBetaNode(State state) {
        this(state, 0, true);
    }

    public FootmanAlphaBetaNode(State state, int depth, boolean isMaxNode) {
        super(state, depth, isMaxNode);
    }

    protected List<State> generatePossibleStates() {
        return null;
    }

    protected AlphaBetaNode getChildFromState(State state) {
        return null;
    }

    protected AlphaBetaLeaf getLeafFromState(State state) {
        return null;
    }
}
