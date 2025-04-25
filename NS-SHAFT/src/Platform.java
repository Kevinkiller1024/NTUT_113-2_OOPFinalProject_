import java.awt.*;
import java.util.Random;

public class Platform {
    public int x, y;
    public int width = 60, height = 10;

    public Platform(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }
}