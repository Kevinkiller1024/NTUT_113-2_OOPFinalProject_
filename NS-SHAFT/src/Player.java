import java.awt.*;

public class Player {
    public int x, y, prevY;
    public int width = 30, height = 30;
    public double velocityY = 0;
    public int speed = 5;
    public boolean isAlive = true;
    public int health = 100; // 用百分比顯示

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.prevY = y;
    }

    public void update() {
        prevY = y;
        velocityY += 0.5;
        y += velocityY;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void takeDamage() {
        health -= 2;
        if (health <= 0) {
            health = 0;
            isAlive = false;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getPrevBounds() {
        return new Rectangle(x, prevY, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(255, 100, 100));
        g.fillRect(x, y, width, height);
    }
}
