import java.awt.*;

public class Player {
    public int x, y;
    public int width = 30, height = 30;
    public double velocityY = 0;
    public int speed = 10;
    public boolean isAlive = true;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        velocityY += 0.5; // gravity
        y += velocityY;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void jump() {
        velocityY = -10;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}