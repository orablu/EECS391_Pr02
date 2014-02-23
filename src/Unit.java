import edu.cwru.sepia.environment.model.state.Unit.UnitView;


public class Unit {
	private int hp;
	private int id;
	private int xPosition;
	private int yPosition;

	public Unit(UnitView unit) {
		hp = unit.getHP();
		id = unit.getID();
		xPosition = unit.getXPosition();
		yPosition = unit.getYPosition();
	}
	
	public int getHP() {
		return hp;
	}

	public int getId() {
		return id;
	}

	public int getXPosition() {
		return xPosition;
	}

	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	
}
