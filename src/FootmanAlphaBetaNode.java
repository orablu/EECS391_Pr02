import java.util.List;
import java.util.ArrayList;
import edu.cwru.sepia.environment.model.state.Unit.UnitView;

public class FootmanAlphaBetaNode extends AlphaBetaNode {
    public FootmanAlphaBetaNode(State state) {
        this(state, 0);
    }

    public FootmanAlphaBetaNode(State state, int depth) {
        super(state, depth, true);
    }

    protected List<State> generatePossibleStates() {
        List<State> states = new ArrayList<>();

        // TODO: Do some shit

        return states;
    }

    protected AlphaBetaNode getChildFromState(State state) {
        return new ArcherAlphaBetaNode(state, depth + 1);
    }

    protected AlphaBetaLeaf getLeafFromState(State state) {
        return new ArcherAlphaBetaLeaf(state);
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

    private boolean isAdjacent(List<UnitView> entities, int x, int y) {
        // Check if the coordinate is occupied.
        for (UnitView e : entities) {
            if (x == e.getXPosition() && y == e.getYPosition()) {
                return false;
            }
        }

        // The position is valid.
        return true;
    }
}
