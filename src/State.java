public class State {
    public static final int ID = 0;
    public static final int X = 1;
    public static final int Y = 2;
    public static final int HP = 3;

    private int[][] footmen;
    private int[][] archers;

    public static int[] createEntity(int id, int hp, int x, int y) {
        return new int[] { id, x, y, hp };
    }

    public State() {
        this(
                new int[] { 0, 0, 0, 0 },
                new int[] { 0, 0, 0, 0 },
                new int[] { 0, 0, 0, 0 },
                new int[] { 0, 0, 0, 0 });
    }

    public State(int[] footman1, int[] footman2, int[] archer1, int[] archer2) {
        footmen = new int[][] { footman1, footman2 };
        archers = new int[][] { archer1, archer2 };
    }

    public int[] getFootman(int index) {
        return footmen[index];
    }

    public int getFootmenHealth() {
        return footmen[0][HP] + footmen[1][HP];
    }

    public int getArcherHealth() {
        return archers[0][HP] + archers[1][HP];
    }

    public int[] getArcher(int index) {
        return archers[index];
    }

    public int getWeight() {
        return this.getFootmenHealth() - this.getArcherHealth();
    }
}
