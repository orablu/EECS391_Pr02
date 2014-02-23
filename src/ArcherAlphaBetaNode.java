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
        return null;
    }

    protected AlphaBetaLeaf getLeafFromState(State state) {
        return null;
    }
}
