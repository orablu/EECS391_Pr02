import java.util.ArrayList;
import java.util.List;

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
        List<UnitView> footmen = state.getFootmen();
        List<UnitView> archers = state.getArchers();
        
        for (UnitView footman : footmen) {
        	if (targetAdjacent(footman.getXPosition(), footman.getYPosition())) {
        		//states.add enum.attack
        	}
        }

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

        return !isAt(state.getEntities(), x, y);
    }

    private boolean targetAt(int x, int y) {
        return isAt(state.getArchers(), x, y);
    }

    private boolean allyAt(int x, int y) {
        return isAt(state.getFootmen(), x, y);
    }

    private boolean isAt(List<UnitView> entities, int x, int y) {
        // Check if the coordinate is occupied.
        for (UnitView e : entities) {
            if (x == e.getXPosition() && y == e.getYPosition()) {
                return false;
            }
        }
        return true;
    }

    private List<UnitView> targetsAdjacent(int x, int y) {
        List<UnitView> targets = state.getArchers();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                for (UnitView target : targets) {
                    if (target.getXPosition() == i && target.getYPosition() == j) {
                        targets.add(target);
                    }
                }
            }
        }
        return targets;
    }
}
