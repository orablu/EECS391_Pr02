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
}
