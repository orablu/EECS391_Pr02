import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class State {
    private static final int W_FOOTMAN_HP       = 1;
    private static final int W_FOOTMAN_DISTANCE = -1;
    private static final int W_ARCHER_HP        = -10;
    private static final int W_FOOTMAN_ALIVE    = 10;
    private static final int W_ARCHER_ALIVE     = -100;

    public static final int X = 0;
    public static final int Y = 1;

    public static int[] Min, Max;
    public static void setupBoard(int minX, int maxX, int minY, int maxY) {
        Min = new int[2]; Min[X] = minX; Min[Y] = minY;
        Max = new int[2]; Max[X] = maxX; Max[Y] = maxY;
    }

    private List<Unit> footmen;
    private List<Unit> archers;
    
    // save the two actions that led to this state (one for each footman, or one for each archer)
    private StateAction prevAction1;
    private StateAction prevAction2;
    
    public State() {
        this(new ArrayList<Unit>(), new ArrayList<Unit>(), new StateAction(), new StateAction());
    }
    
    public State(List<Unit> footmen, List<Unit> archers) {
        this(footmen, archers, new StateAction(), new StateAction());
    }

    public State(List<Unit> footmen, List<Unit> archers, StateAction action1, StateAction action2) {
        this.footmen = new ArrayList<Unit>();
        this.footmen.addAll(footmen);
        this.archers = new ArrayList<Unit>();
        this.archers.addAll(archers);
        this.prevAction1 = action1;
        this.prevAction2 = action2;
    }

    public State(State state, StateAction action1, StateAction action2) {
        this();
        
        prevAction1 = action1;
        prevAction2 = action2;
        
        for (Unit footman : state.getFootmen()) {
            footmen.add(new Unit(footman));
        }
        
        for (Unit archer : state.getArchers()) {
            archers.add(new Unit(archer));
        }
    }

    /**
     * Gets all the units in the state (archers and footmen)
     * @return a list of the units
     */
    public List<Unit> getEntities() {
        List<Unit> entities = new ArrayList<>();
        entities.addAll(this.getFootmen());
        entities.addAll(this.getArchers());
        return entities;
    }

    /**
     * Gets the footmen in the state
     * @return a list of the footmen
     */
    public List<Unit> getFootmen() {
        return footmen;
    }
    
    /**
     * Gets the archers in the state
     * @return a list of the archers
     */
    public List<Unit> getArchers() {
        return archers;
    }

    /**
     * 
     * @return the sum of the footmen's health
     */
    public int getFootmenHealth() {
        int sum = 0;
        for (Unit footman : footmen) {
            sum += footman.getHP();
        }
        
        return sum;
    }

    /**
     * 
     * @return the sum of the archers' health
     */
    public int getArcherHealth() {
        int sum = 0;
        for (Unit archer : archers) {
            sum += archer.getHP();
        }
        return sum;
    }
    
    /**
     * Gets the first action used to create this state
     * @return the action
     */
    public StateAction getAction1() {
        return prevAction1;
    }
    /**
     * Gets the second action used to create this state
     * @return the action
     */
    public StateAction getAction2() {
        return prevAction2;
    }
    
    /**
     * Calculates the utility of a state
     * @return the utility
     */
    public int getStateUtility() {

        int footmanHP = 0;
        for (Unit footman : footmen) {
            footmanHP += footman.getHP();
        }
        
        int archerHP = 0;
        for (Unit archer : archers) {
            archerHP += archer.getHP();
        }
        
        int footmenAlive = footmen.size();
        int archersAlive = archers.size();
        
        int distance = 0;
        for (Unit footman : footmen) {
            distance += getNearestDistance(footman);
        }
        
        return (int) ((W_FOOTMAN_HP * footmanHP)
            + (W_FOOTMAN_DISTANCE * distance)
            + (W_ARCHER_HP * archerHP)
            + (W_FOOTMAN_ALIVE * footmenAlive)
            + (W_ARCHER_ALIVE * archersAlive));
        
    }

    /**
     * Calculates the nearest distance from the given unit to any archer
     * @param entity
     * @return the distance
     */
    private int getNearestDistance(Unit entity) {
        int x = entity.getXPosition();
        int y = entity.getYPosition();

        List<Integer> distances = new ArrayList<>();
        for (Unit archer : archers) {
            distances.add(Math.max(
                        Math.abs(x - archer.getXPosition()),
                        Math.abs(y - archer.getYPosition())));
        }
        return distances.isEmpty() ? 0 : Collections.min(distances);
    }
    
    /**
     * Applies actions to a state and calculates the state after the actions
     * @param action1
     * @param action2
     * @return the next state
     */
    public State getNextState(StateAction action1, StateAction action2) {
        State nextState = new State(this, action1, action2);
        nextState.applyAction(action1);
        nextState.applyAction(action2);
        return nextState;
    }

    /**
     * Applies a single action to a state
     * @param action
     */
    private void applyAction(StateAction action) {
        Unit unit = action.getEntity();
        
        switch(action.getType()) {
        case ATTACK: // uses the base attack of the unit and applies it to the target unit's hp
            int damage = unit.isFootman() ? Unit.FOOTMAN_DAMAGE : Unit.ARCHER_DAMAGE;
            Unit targetUnit = getUnitFromID(action.getTarget().getId());
            targetUnit.setHP(targetUnit.getHP() - damage);
            break;
        case MOVE: // moves the given unit in the specified direction
            moveUnit(action, unit);
            break;
        default:
            break;
        }
    }

    /**
     * Moves the given unit in a direction specified by the given action by 
     * altering the unit's position in the state.
     * @param action
     * @param unit
     */
    private void moveUnit(StateAction action, Unit unit) {
        Unit stateUnit = getUnitFromID(unit.getId());
        switch(action.getDirection()) {
        case EAST:
            stateUnit.setXPosition(stateUnit.getXPosition() + 1);
            break;
        case NORTH:
            stateUnit.setYPosition(stateUnit.getYPosition() - 1);
            break;
        case SOUTH:
            stateUnit.setYPosition(stateUnit.getYPosition() + 1);
            break;
        case WEST:
            stateUnit.setXPosition(stateUnit.getXPosition() - 1);
            break;
        default:
            Node.Log("Move action with no direction???", Node.Level.Critical);
            break;
        }
    }
    
    /**
     * Finds the unit in the state given the unit's global id
     * @param id
     * @return the unit
     */
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
    
    @Override
    public String toString() {
        String s = "Current state:\n";
        
        for (Unit footman : footmen) {
            s += ("\tFootman " + footman.getId() + " at: " + footman.getXPosition() + "," + footman.getYPosition() + "; with HP: " + footman.getHP() + "\n");
        }
        
        for (Unit archer : archers) {
             s += ("\tArcher " + archer.getId() + " at: " + archer.getXPosition() + "," + archer.getYPosition() + "; with HP: " + archer.getHP() + "\n");
        }
        String unit = prevAction1.getEntity() == null ? "" : " performed by unit " + prevAction1.getEntity().getId();
        s += ("\tPrevious move 1: " + prevAction1.getType() + unit + "\n");
        unit = prevAction2.getEntity() == null ? "" : " performed by unit " + prevAction2.getEntity().getId();
        s += ("\tPrevious move 2: " + prevAction2.getType() + unit + "\n");
        return s;
    }
}
