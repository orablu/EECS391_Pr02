import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ArcherAlphaBetaLeaf extends AlphaBetaLeaf {
    public ArcherAlphaBetaLeaf(State state) {
        super(state, false);
    }

    protected void setActions() {
        action1 = new Action();
        action2 = new Action();
    }

    protected int getWeightFromState() {
        return -state.getFootmenHealth();
    }

    private int getNearestDistance(Unit entity) {
        int x = entity.getXPosition();
        int y = entity.getYPosition();
        List<Unit> archers = state.getArchers();
        List<Integer> distances = new ArrayList<>();
        for (Unit archer : archers) {
            distances.add(Math.max(
                        Math.abs(x - archer.getXPosition()),
                        Math.abs(y - archer.getYPosition())));
        }
        return Collections.min(distances);
    }
}
