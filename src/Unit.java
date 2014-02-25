import edu.cwru.sepia.environment.model.state.Unit.UnitView;

/**
 * A structure containing the basic information necessary about units for the minimax search.
 *
 * @author wkr3, jxb532
 */
public class Unit {
    public static int ARCHER_RANGE;
    public static int ARCHER_DAMAGE;
    public static int FOOTMAN_DAMAGE;
    
    private int hp;
    private int id;
    private int xPosition;
    private int yPosition;
    private boolean isFootman;
    
    /**
     * Sets up the unit parameters to begin using units.
     *
     * @param archerRange An archer's range.
     * @param archerDamage An archer's base damage
     * @param footmanDamage A footman's base damage
     */
    public static void setupUnit(int archerRange, int archerDamage, int footmanDamage) {
        ARCHER_RANGE = archerRange;
        ARCHER_DAMAGE = archerDamage;
        FOOTMAN_DAMAGE = footmanDamage;
    }

    /**
     * Creates a new unit from the given UnitView.
     *
     * @param unit The UnitView to extract information from
     */
    public Unit(UnitView unit) {
        hp = unit.getHP();
        id = unit.getID();
        xPosition = unit.getXPosition();
        yPosition = unit.getYPosition();
        isFootman = unit.getTemplateView().getName().equalsIgnoreCase("Footman");
    }
    
    /**
     * Creates a new unit from an already existing unit.
     *
     * @param unit The unit to copy
     */
    public Unit(Unit unit) {
        hp = unit.getHP();
        id = unit.getId();
        xPosition = unit.getXPosition();
        yPosition = unit.getYPosition();
        isFootman = unit.isFootman();
    }

    /**
     * Gets the unit's HP.
     *
     * @return The unit's HP
     */
    public int getHP() {
        return hp;
    }
    
    /**
     * Sets the unit's HP.
     *
     * @param hp The new value for the HP
     */
    public void setHP(int hp) {
        this.hp = hp;
    }

    /**
     * Gets the unit's ID.
     *
     * @return The unit's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the unit's x-coordinate.
     *
     * @return The unit's x-coordinate
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Sets the unit's x-coordinate.
     *
     * @param xPosition The new x-coordinate
     */
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Gets the unit's y-coordinate.
     *
     * @return The unit's y-coordinate
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * Sets the unit's y-coordinate.
     *
     * @param yPosition The new y-coordinate
     */
    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }
    
    /**
     * Returns whether the unit is a footman or not.
     *
     * @return True if the unit is a footman, false otherwise
     */
    public boolean isFootman() {
        return isFootman;
    }
}
