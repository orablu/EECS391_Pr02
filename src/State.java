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
    
    // TODO
    public State getNextState(Action action1, Action action2) {
    	return null;
    }
}
