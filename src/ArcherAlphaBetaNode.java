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
        
        Map<Unit, List<Action>> actions = new HashMap<>();
        
        for (Unit archer : archers) {
        	actions.put(archer, new ArrayList<Action>());
        	
        	int x = archer.getXPosition();
        	int y = archer.getYPosition();
        	
        	for (Unit target : state.getFootmen()) {
        		Action action = new Action(archer, target);
        		actions.get(archer).add(action);
        	}
        	
        	if (isValidPosition(x + 1, y)) {
        		Action action = new Action(archer, Direction.EAST);
        		actions.get(archer).add(action);
        	}
        	
        	if (isValidPosition(x, y + 1)) {
        		Action action = new Action(archer, Direction.NORTH);
        		actions.get(archer).add(action);
        	}
        	
        	if (isValidPosition(x - 1, y)) {
        		Action action = new Action(archer, Direction.WEST);
        		actions.get(archer).add(action);
        	}
        	
        	if (isValidPosition(x, y - 1)) {
        		Action action = new Action(archer, Direction.SOUTH);
        		actions.get(archer).add(action);
        	}
        }
        
        for (Action archerOneAction : actions.get(archers.get(0))) {
        	if (archers.size() > 1) { // if there are two footmen left
        		for (Action archerTwoAction : actions.get(archers.get(1))) {
        			states.add(state.getNextState(archerOneAction, archerTwoAction));
        		}
        	} else { // if there is only one footman left, add an empty action
        		states.add(state.getNextState(archerOneAction, new Action()));
        	}
        }

        return states;
    }

    protected AlphaBetaNode getChildFromState(State state) {
        return new FootmanAlphaBetaNode(state, depth + 1);
    }

    protected AlphaBetaLeaf getLeafFromState(State state) {
        return new FootmanAlphaBetaLeaf(state);
    }

    protected void setActions() {
        action1 = new Action();
        action2 = new Action();
    }
}
