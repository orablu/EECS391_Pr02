import edu.cwru.sepia.util.Direction;

public class Action {
    public enum Type {
        Undefined,
        Attack,
        Move,
        Stay
    };

    private Type type;
    private Unit entity;
    private Unit target;
    private Direction direction;

    public Action() {
        this.type = Type.Undefined;
        this.entity = null;
        this.target = null;
        this.direction = null;
    }

	public Action(Unit entity) {
         this();
         this.type = Type.Stay;
         this.entity = entity;
	}

    public Action(Unit entity, Unit target) {
        this();
        this.type = Type.Attack;
        this.entity = entity;
        this.target = target;
    }

    public Action(Unit entity, Direction direction) {
        this();
        this.type = Type.Move;
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
}
