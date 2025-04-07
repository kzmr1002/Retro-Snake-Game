public class Snake {
    protected int x[];
    protected int y[];
    protected int bodyparts;

    public Snake() {
        x = new int[GamePanel.GAME_UNITS];
        y = new int[GamePanel.GAME_UNITS];
        bodyparts = 6;
    }

}