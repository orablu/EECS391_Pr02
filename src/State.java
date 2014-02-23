import java.util.ArrayList;
import java.util.List;

public class State {
    public static final int X = 0;
    public static final int Y = 1;

    public static int[] Min, Max;
    public static void setupBoard(int minX, int maxX, int minY, int maxY) {
        Min = new int[2]; Min[X] = minX; Min[Y] = minY;
        Max = new int[2]; Max[X] = maxX; Max[Y] = maxY;
    }

    private List<Unit> footmen;
    private List<Unit> archers;
    
    public State() {
        this(new ArrayList<Unit>(), new ArrayList<Unit>());
    }

    public State(List<Unit> footmen, List<Unit> archers) {
        this.footmen = new ArrayList<Unit>();
        this.footmen.addAll(footmen);
        this.archers = new ArrayList<Unit>();
        this.archers.addAll(archers);
    }

    public State(State state) {
		this();
		
		for (Unit footman : state.getFootmen()) {
			footmen.add(new Unit(footman));
		}
		
		for (Unit archer : state.getArchers()) {
			archers.add(new Unit(archer));
		}
	}

	public List<Unit> getEntities() {
        List<Unit> entities = new ArrayList<>();
        entities.addAll(this.getFootmen());
        entities.addAll(this.getArchers());
        return entities;
    }

    public List<Unit> getFootmen() {
        return footmen;
    }
    
    public List<Unit> getArchers() {
        return archers;
    }

    public int getFootmenHealth() {
        int sum = 0;
        for (Unit footman : footmen) {
        	sum += footman.getHP();
        }
        
        return sum;
    }

    public int getArcherHealth() {
        int sum = 0;
        for (Unit archer : archers) {
        	sum += archer.getHP();
        }
        return sum;
    }
    
    public State getNextState(Action action1, Action action2) {
    	State nextState = new State(this);
    	nextState.applyAction(action1);
    	nextState.applyAction(action2);
    	return nextState;
    }

	private void applyAction(Action action) {
		Unit unit = action.getEntity();
		
		switch(action.getType()) {
		case ATTACK:
			int damage = unit.getTemplateView().getBasicAttack();
			Unit stateUnit = getUnitFromID(unit.getId());
			stateUnit.setHP(stateUnit.getHP() - damage);
			break;
		case MOVE:
			moveUnit(action, unit);
			break;
		default:
			break;
		}
	}

	private void moveUnit(Action action, Unit unit) {
		Unit stateUnit = getUnitFromID(unit.getId());
		switch(action.getDirection()) {
		case EAST:
			stateUnit.setXPosition(stateUnit.getXPosition() + 1);
			break;
		case NORTH:
			stateUnit.setYPosition(stateUnit.getYPosition() + 1);
			break;
		case SOUTH:
			stateUnit.setYPosition(stateUnit.getYPosition() - 1);
			break;
		case WEST:
			stateUnit.setXPosition(stateUnit.getXPosition() - 1);
			break;
		default:
			System.out.println("Move action with no direction???");
			break;
		}
	}
	
	private Unit getUnitFromID(int id) {
		for (Unit footman : footmen) {
			if (footman.getId() == id) {
				return footman;
			}
		}
		
		for (Unit archer : archers) {
			if (archer.getId() == id) {
				return archer;
			}
		}
		
		return null;
	}
}
