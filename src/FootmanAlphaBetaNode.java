import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cwru.sepia.util.Direction;

/**
 * An minimax/alpha/beta node for the footmen controlled by our agent.
 *
 * @author wkr3, jxb532
 */
public class FootmanAlphaBetaNode extends AlphaBetaNode {
    /**
     * Creates a new node from the given state.
     *
     * @param state The state used to generate the node
     */
    public FootmanAlphaBetaNode(State state) {
        this(state, 0);
    }

    /**
     * Creates a new node from the given state at the given depth.
     *
     * @param state The state used to generate the node
     * @param depth The current dpth of the banch
     */
    public FootmanAlphaBetaNode(State state, int depth) {
        super(state, depth, true);
    }

    /**
     * {@inheritDoc}
     * @see AlphaBetaNode#generatePossibleStates()
     */
    protected List<State> generatePossibleStates() {
        Log("GENERATING FOOTMAN STATES", Level.Low);
        List<State> states = new ArrayList<>();

        List<Unit> footmen = state.getFootmen();
        
        Map<Unit, List<StateAction>> actions = new HashMap<>();
        
        for (Unit footman : footmen) {
            actions.put(footman, new ArrayList<StateAction>());
            
            int x = footman.getXPosition();
            int y = footman.getYPosition();
            
            List<Unit> targetsAdjacent = targetsAdjacent(x, y);
            for (Unit target : targetsAdjacent) {
                StateAction action = new StateAction(footman, target);
                actions.get(footman).add(action);
            }
            
            if (isValidPosition(x + 1, y)) {
                StateAction action = new StateAction(footman, Direction.EAST);
                actions.get(footman).add(action);
            }
            
            if (isValidPosition(x, y - 1)) {
                StateAction action = new StateAction(footman, Direction.NORTH);
                actions.get(footman).add(action);
            }
            
            if (isValidPosition(x - 1, y)) {
                StateAction action = new StateAction(footman, Direction.WEST);
                actions.get(footman).add(action);
            }
            
            if (isValidPosition(x, y + 1)) {
                StateAction action = new StateAction(footman, Direction.SOUTH);
                actions.get(footman).add(action);
            }
        }
        
        for (Unit footman : footmen) {
            Log("Generated " + actions.get(footman).size() + " actions for Footman " + footman.getId(), Level.Low);
        }
        
        for (StateAction fmanOneAction : actions.get(footmen.get(0))) {
            if (footmen.size() > 1) { // if there are two footmen left
                for (StateAction fmanTwoAction : actions.get(footmen.get(1))) {
                    states.add(state.getNextState(fmanOneAction, fmanTwoAction));
                }
            } else { // if there is only one footman left, add an empty action
                states.add(state.getNextState(fmanOneAction, new StateAction()));
            }
        }
        
//        Collections.sort(states, new Comparator<State>() {
//
//			@Override
//			public int compare(State o1, State o2) {
//				int util1 = o1.getStateUtility();
//				int util2 = o2.getStateUtility();
//				return util1 < util2 ? 1 : util1 == util2 ? 0 : -1;
//			}
//		});

        return states;
    }

    /**
     * {@inheritDoc}
     * @see AlphaBetaNode#getChildFromState(State)
     */
    protected AlphaBetaNode getChildFromState(State state) {
        return new ArcherAlphaBetaNode(state, depth + 1);
    }

    /**
     * {@inheritDoc}
     * @see AlphaBetaNode#getLeafFromState(State)
     */
    protected AlphaBetaLeaf getLeafFromState(State state) {
        return new AlphaBetaLeaf(state, false);
    }

    /**
     * Returns all targets adjacent to the given coordinate.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return A list containing all adjacent targets
     */
    private List<Unit> targetsAdjacent(int x, int y) {
        List<Unit> targets = new ArrayList<>();
        List<Unit> allTargets = state.getArchers();
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                for (Unit target : allTargets) {
                    if (target.getXPosition() == i && target.getYPosition() == j) {
                        targets.add(target);
                    }
                }
            }
        }
        return targets;
    }
}
