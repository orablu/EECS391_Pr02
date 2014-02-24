import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cwru.sepia.util.Direction;

public class ArcherAlphaBetaNode extends AlphaBetaNode {
    public ArcherAlphaBetaNode(State state) {
        this(state, 0);
    }

    public ArcherAlphaBetaNode(State state, int depth) {
        super(state, depth, false);
    }

    protected List<State> generatePossibleStates() {
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
        	
        	if (isValidPosition(x, y + 1)) {
        		StateAction action = new StateAction(archer, Direction.NORTH);
        		actions.get(archer).add(action);
        	}
        	
        	if (isValidPosition(x - 1, y)) {
        		StateAction action = new StateAction(archer, Direction.WEST);
        		actions.get(archer).add(action);
        	}
        	
        	if (isValidPosition(x, y - 1)) {
        		StateAction action = new StateAction(archer, Direction.SOUTH);
        		actions.get(archer).add(action);
        	}
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

    protected AlphaBetaNode getChildFromState(State state) {
        return new FootmanAlphaBetaNode(state, depth + 1);
    }

    protected AlphaBetaLeaf getLeafFromState(State state) {
        return new AlphaBetaLeaf(state, true);
    }

    @Override
    public List<Node> getBestPath() {
        List<Node> bestPath = this.getBestNode().getBestPath();
        return bestPath;
    }
    
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
