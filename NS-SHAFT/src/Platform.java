import java.awt.*;

public class Platform {
    public int x, y;
    public static final int WIDTH = 100, HEIGHT = 10;

    public enum Type { NORMAL, SPIKE, BOUNCY, SLOW }

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

    public boolean isVisible() {
        return true;  // 永遠顯示
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void onStepped(Player player) {
        switch (type) {
            case SPIKE:
                player.takeDamage();
                break;
            case BOUNCY:
                player.velocityY = -10;
                break;
            case SLOW:
                // 不做位置調整，靠外部邏輯處理緩速效果
                break;
            default:
                break;
        }
    }

    public void update() {
        // 無需特殊更新
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1)); // 邊線寬度

        switch (type) {
            case SPIKE:
                g2.setColor(Color.RED);
                for (int i = 0; i < WIDTH; i += 10) {
                    int[] xPoints = {x + i, x + i + 5, x + i + 10};
                    int[] yPoints = {y + HEIGHT, y, y + HEIGHT};
                    g2.fillPolygon(xPoints, yPoints, 3);
                    g2.setColor(Color.WHITE);
                    g2.drawPolygon(xPoints, yPoints, 3);
                    g2.setColor(Color.RED); // 恢復顏色繼續下一個
                }
                break;
            case BOUNCY:
                g2.setColor(Color.GREEN);
                g2.fillRoundRect(x, y, WIDTH, HEIGHT, 10, 10);
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(x, y, WIDTH, HEIGHT, 10, 10);
                break;
            case SLOW:
                g2.setColor(new Color(150, 150, 255));
                g2.fillRoundRect(x, y, WIDTH, HEIGHT, 10, 10);
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(x, y, WIDTH, HEIGHT, 10, 10);
                break;
            default:
                g2.setColor(Color.BLACK);
                g2.fillRoundRect(x, y, WIDTH, HEIGHT, 10, 10);
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(x, y, WIDTH, HEIGHT, 10, 10);
        }
    }
}
