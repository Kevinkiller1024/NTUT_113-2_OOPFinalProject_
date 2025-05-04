import java.awt.*;

public class Platform {
    public int x, y;
    public static final int WIDTH = 100, HEIGHT = 10;
    public enum Type { NORMAL, SPIKE }
    private Type type;

    public Platform(int x, int y) {
        this(x, y, Type.NORMAL);
    }

    public Platform(int x, int y, Type type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void draw(Graphics g) {
        if (type == Type.SPIKE) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y, WIDTH, HEIGHT);
            g.setColor(Color.RED);
            for (int i = 0; i < WIDTH; i += 10) {
                int[] xPoints = {x + i, x + i + 5, x + i + 10};
                int[] yPoints = {y + HEIGHT, y, y + HEIGHT};
                g.fillPolygon(xPoints, yPoints, 3);
            }
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, WIDTH, HEIGHT);
        }
    }
}
