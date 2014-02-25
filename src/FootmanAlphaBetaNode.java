import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cwru.sepia.util.Direction;

public class FootmanAlphaBetaNode extends AlphaBetaNode {
    public FootmanAlphaBetaNode(State state) {
        this(state, 0);
    }

    public FootmanAlphaBetaNode(State state, int depth) {
        super(state, depth, true);
    }

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

        return states;
    }

    protected AlphaBetaNode getChildFromState(State state) {
        return new ArcherAlphaBetaNode(state, depth + 1);
    }

    protected AlphaBetaLeaf getLeafFromState(State state) {
        return new AlphaBetaLeaf(state, false);
    }

    private boolean targetAt(int x, int y) {
        return isAt(state.getArchers(), x, y);
    }

    private boolean allyAt(int x, int y) {
        return isAt(state.getFootmen(), x, y);
    }

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
