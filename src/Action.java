import edu.cwru.sepia.environment.model.state.Unit.UnitView;
import edu.cwru.sepia.util.Direction;

public class Action {
    public enum Type {
        Undefined,
        Attack,
        Move,
        Stay
    };

    private Type type;
    private UnitView entity;
    private UnitView target;
    private Direction direction;

    public Action() {
        this.type = Type.Undefined;
        this.entity = null;
        this.target = null;
        this.direction = null;
    }

	public Action(UnitView entity) {
         this();
         this.type = Type.Stay;
         this.entity = entity;
	}

    public Action(UnitView entity, UnitView target) {
        this();
        this.type = Type.Attack;
        this.entity = entity;
        this.target = target;
    }

    public Action(UnitView entity, Direction direction) {
        this();
        this.type = Type.Move;
        this.entity = entity;
        this.direction = direction;
    }

    public Type getType() {
        return this.type;
    }

    public UnitView getEntity() {
        return this.entity;
    }

    public UnitView getTarget() {
        return this.target;
    }

    public Direction getDirection() {
        return this.direction;
    }
}
