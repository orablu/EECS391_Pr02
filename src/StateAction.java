import edu.cwru.sepia.util.Direction;

/**
 * An action taken in a state.
 *
 * @author wkr3, jxb532
 */
public class StateAction {
    /**
     * The type of action that is taken.
     */
    public static enum Type {
        UNDEFINED,
        ATTACK,
        MOVE,
        STAY
    };

    private Type type;
    private Unit entity;
    private Unit target;
    private Direction direction;

    /**
     * Creates a new StateAction.
     */
    public StateAction() {
        this.type = Type.UNDEFINED;
        this.entity = null;
        this.target = null;
        this.direction = null;
    }

    /**
     * Creates a new stay action for the given entity.
     *
     * @param entity The entity that takes the stay action
     */
    public StateAction(Unit entity) {
         this();
         this.type = Type.STAY;
         this.entity = entity;
    }

    /**
     * Creates a new attack action for the given enttiy on the given target.
     *
     * @param entity The entity that takes the attack action
     * @param target The target the entity is attacking
     */
    public StateAction(Unit entity, Unit target) {
        this();
        this.type = Type.ATTACK;
        this.entity = entity;
        this.target = target;
    }

    /**
     * Creates a new move action for the given entity in the given direction.
     *
     * @param entity The entity that takes the move action
     * @param direction The direction the entity moves in
     */
    public StateAction(Unit entity, Direction direction) {
        this();
        this.type = Type.MOVE;
        this.entity = entity;
        this.direction = direction;
    }

    /**
     * Gets the type of action.
     *
     * @return The type of action taken
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Gets the entity that takes the action, if any.
     *
     * @return The entity that takes this action
     */
    public Unit getEntity() {
        return this.entity;
    }

    /**
     * Gets the target of this action, if any.
     *
     * @return The target of this action
     */
    public Unit getTarget() {
        return this.target;
    }

    /**
     * Gets the direction of this action, if any.
     *
     * @return The direction of this action
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * @return A string representing the node.
     */
    @Override
    public String toString() {
        switch (this.type) {
            case ATTACK:
                return String.format("%d attacks %d",
                        entity.getId(), target.getId());
            case MOVE:
                return String.format("%d moves %s",
                        entity.getId(), direction.toString());
            case STAY:
                return String.format("%d stays",
                        entity.getId());
            default:
                return new String();
        }
    }
}
