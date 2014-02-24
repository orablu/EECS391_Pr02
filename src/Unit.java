import edu.cwru.sepia.environment.model.state.Unit.UnitView;


public class Unit {
	public static int ARCHER_RANGE;
	public static int ARCHER_DAMAGE;
	public static int FOOTMAN_DAMAGE;
	
	private int hp;
	private int id;
	private int xPosition;
	private int yPosition;
	private boolean isFootman;
	
	public static void setupUnit(int archerRange, int archerDamage, int footmanDamage) {
		ARCHER_RANGE = archerRange;
		ARCHER_DAMAGE = archerDamage;
		FOOTMAN_DAMAGE = footmanDamage;
	}

	public Unit(UnitView unit) {
		hp = unit.getHP();
		id = unit.getID();
		xPosition = unit.getXPosition();
		yPosition = unit.getYPosition();
		isFootman = unit.getTemplateView().getName().equalsIgnoreCase("Footman");
	}
	
	public Unit(Unit footman) {
		hp = footman.getHP();
		id = footman.getId();
		xPosition = footman.getXPosition();
		yPosition = footman.getYPosition();
	}

	public int getHP() {
		return hp;
	}
	
	public void setHP(int hp) {
		this.hp = hp;
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
	
	public boolean isFootman() {
		return isFootman;
	}

}
