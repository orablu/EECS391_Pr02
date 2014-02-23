import java.util.List;

public class ArcherAlphaBetaNode extends AlphaBetaNode {
    public ArcherAlphaBetaNode(State state) {
        this(state, 0);
    }

    public ArcherAlphaBetaNode(State state, int depth) {
        super(state, depth, false);
    }

    protected List<State> generatePossibleStates() {
        return null;
    }

    protected AlphaBetaNode getChildFromState(State state) {
        return new FootmanAlphaBetaNode(state, depth + 1);
    }

    protected AlphaBetaLeaf getLeafFromState(State state) {
        return new FootmanAlphaBetaLeaf(state);
    }
}
