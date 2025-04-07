import java.util.Random;

public class Apple {
    private int x;
    private int y;
    private Random random;

    public Apple() {
        random = new Random();
        x = random.nextInt((int) GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE) * GamePanel.UNIT_SIZE;
        y = random.nextInt((int) GamePanel.SCREEN_HEIGHT / GamePanel.UNIT_SIZE) * GamePanel.UNIT_SIZE;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}