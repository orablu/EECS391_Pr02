import edu.cwru.sepia.environment.model.state.Unit.UnitView;
import edu.cwru.sepia.environment.model.state.UnitTemplate.UnitTemplateView;


public class Unit {
	private int hp;
	private int id;
	private int xPosition;
	private int yPosition;
	private UnitTemplateView templateView;

	public Unit(UnitView unit) {
		hp = unit.getHP();
		id = unit.getID();
		xPosition = unit.getXPosition();
		yPosition = unit.getYPosition();
		templateView = unit.getTemplateView();
	}
	
	public Unit(Unit footman) {
		hp = footman.getHP();
		id = footman.getId();
		xPosition = footman.getXPosition();
		yPosition = footman.getYPosition();
		templateView = footman.getTemplateView();
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
	
	public UnitTemplateView getTemplateView() {
		return templateView;
	}
}
