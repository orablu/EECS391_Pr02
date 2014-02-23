import java.util.List;
import java.util.ArrayList;

public class FootmanAlphaBetaNode extends AlphaBetaNode {
    public FootmanAlphaBetaNode(State state) {
        this(state, 0, true);
    }

    public FootmanAlphaBetaNode(State state, int depth, boolean isMaxNode) {
        super(state, depth, isMaxNode);
    }

    protected List<State> generatePossibleStates() {
        List<State> states = new ArrayList<>();

        return states;
    }

    protected AlphaBetaNode getChildFromState(State state) {
        return null;
    }

    protected AlphaBetaLeaf getLeafFromState(State state) {
        return null;
    }

    private boolean isValidPosition(int x, int y) {
        // Check boundaries.
        if (x < State.Min[State.X] || y < State.Min[State.Y]) {
            return false;
        } else if (x >= State.Max[State.X] || y >= State.Max[State.Y]) {
            return false;
        }

        return !targetAdjacent(x, y) && !allyAdjacent(x, y);
    }

    private boolean targetAdjacent(int x, int y) {
        return isAdjacent(state.getArchers(), x, y);
    }

    private boolean allyAdjacent(int x, int y) {
        return isAdjacent(state.getFootmen(), x, y);
    }

    private boolean isAdjacent(List<int[]> entities, int x, int y) {
        // Check if the coordinate is occupied.
        for (int[] e : entities) {
            if (x == e[State.X] && y == e[State.Y]) {
                return false;
            }
        }

        // The position is valid.
        return true;
    }
}
