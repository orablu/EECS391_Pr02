import java.util.ArrayList;
import java.util.List;

import edu.cwru.sepia.environment.model.state.Unit.UnitView;

public class State {
    public static final int X = 0;
    public static final int Y = 1;
    public static final int ID = 2;
    public static final int HP = 3;

    public static int[] Min, Max;
    public static void setupBoard(int minX, int maxX, int minY, int maxY) {
        Min = new int[2]; Min[X] = minX; Min[Y] = minY;
        Max = new int[2]; Max[X] = maxX; Max[Y] = maxY;
    }

    private List<UnitView> footmen;
    private List<UnitView> archers;
    
    public State() {
        this(new ArrayList<UnitView>(), new ArrayList<UnitView>());
    }

    public State(List<UnitView> footmen, List<UnitView> archers) {
        this.footmen = new ArrayList<UnitView>();
        this.footmen.addAll(footmen);
        this.archers = new ArrayList<UnitView>();
        this.archers.addAll(archers);
    }

    public List<UnitView> getFootmen() {
        return footmen;
    }
    
    public List<UnitView> getArchers() {
        return archers;
    }

    public int getFootmenHealth() {
        int sum = 0;
        for (UnitView footman : footmen) {
        	sum += footman.getHP();
        }
        
        return sum;
    }

    public int getArcherHealth() {
        int sum = 0;
        for (UnitView archer : archers) {
        	sum += archer.getHP();
        }
        
        return sum;
    }
}
