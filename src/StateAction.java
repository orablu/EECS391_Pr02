import edu.cwru.sepia.util.Direction;

public class StateAction {
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

    public StateAction() {
        this.type = Type.UNDEFINED;
        this.entity = null;
        this.target = null;
        this.direction = null;
    }

	public StateAction(Unit entity) {
         this();
         this.type = Type.STAY;
         this.entity = entity;
	}

    public StateAction(Unit entity, Unit target) {
        this();
        this.type = Type.ATTACK;
        this.entity = entity;
        this.target = target;
    }

    public StateAction(Unit entity, Direction direction) {
        this();
        this.type = Type.MOVE;
        this.entity = entity;
        this.direction = direction;
    }

    public Type getType() {
        return this.type;
    }

    public Unit getEntity() {
        return this.entity;
    }

    public Unit getTarget() {
        return this.target;
    }

    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public String toString() {
        switch (this.type) {
            case ATTACK:
                return String.format("%d attack %d",
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
