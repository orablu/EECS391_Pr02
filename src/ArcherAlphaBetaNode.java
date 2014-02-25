import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cwru.sepia.util.Direction;

/**
 * An minimax/alpha/beta node for the archers controlled by the enemy agent.
 *
 * @author wkr3, jxb532
 */
public class ArcherAlphaBetaNode extends AlphaBetaNode {
    /**
     * Creates a new node from the given state.
     *
     * @param state The state used to generate the node
     */
    public ArcherAlphaBetaNode(State state) {
        this(state, 0);
    }

    /**
     * Creates a new node from the given state at the given depth.
     *
     * @param state The state used to generate the node
     * @param depth The current dpth of the banch
     */
    public ArcherAlphaBetaNode(State state, int depth) {
        super(state, depth, false);
    }

    /**
     * {@inheritDoc}
     * @see AlphaBetaNode#generatePossibleStates()
     */
    protected List<State> generatePossibleStates() {
        Log("GENERATING ARCHER STATES", Level.Low);
        List<State> states = new ArrayList<>();

        List<Unit> archers = state.getArchers();
        
        Map<Unit, List<StateAction>> actions = new HashMap<>();
        
        for (Unit archer : archers) {
            actions.put(archer, new ArrayList<StateAction>());
            
            int x = archer.getXPosition();
            int y = archer.getYPosition();
            
            List<Unit> targetsInRange = targetsInRange(x, y, Unit.ARCHER_RANGE);
            for (Unit target : targetsInRange) {
                StateAction action = new StateAction(archer, target);
                actions.get(archer).add(action);
            }
            
            if (isValidPosition(x + 1, y)) {
                StateAction action = new StateAction(archer, Direction.EAST);
                actions.get(archer).add(action);
            }
            
            if (isValidPosition(x, y - 1)) {
                StateAction action = new StateAction(archer, Direction.NORTH);
                actions.get(archer).add(action);
            }
            
            if (isValidPosition(x - 1, y)) {
                StateAction action = new StateAction(archer, Direction.WEST);
                actions.get(archer).add(action);
            }
            
            if (isValidPosition(x, y + 1)) {
                StateAction action = new StateAction(archer, Direction.SOUTH);
                actions.get(archer).add(action);
            }
            
            StateAction action = new StateAction(archer);
            actions.get(archer).add(action);
        }
        
        for (Unit archer : archers) {
            Log("Generated " + actions.get(archer).size() + " actions for Archer " + archer.getId(), Level.Low);
        }
        
        for (StateAction archerOneAction : actions.get(archers.get(0))) {
            if (archers.size() > 1) { // if there are two footmen left
                for (StateAction archerTwoAction : actions.get(archers.get(1))) {
                    states.add(state.getNextState(archerOneAction, archerTwoAction));
                }
            } else { // if there is only one footman left, add an empty action
                states.add(state.getNextState(archerOneAction, new StateAction()));
            }
        }

        return states;
    }

    /**
     * {@inheritDoc}
     * @see AlphaBetaNode#getChildFromState(State)
     */
    protected AlphaBetaNode getChildFromState(State state) {
        return new FootmanAlphaBetaNode(state, depth + 1);
    }

    /**
     * {@inheritDoc}
     * @see AlphaBetaNode#getLeafFromState(State)
     */
    protected AlphaBetaLeaf getLeafFromState(State state) {
        return new AlphaBetaLeaf(state, true);
    }
    
    /**
     * Returns all targets in range of the given coordinate.
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param range The maximum distance a target can be from the coordinate
     * @return A list containing all adjacent targets
     */
    private List<Unit> targetsInRange(int x, int y, int range) {
        List<Unit> targets = new ArrayList<>();
        List<Unit> allTargets = state.getFootmen();
        for (int i = x - range; i <= x + range; i++) {
            for (int j = y - range; j <= y + range; j++) {
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
