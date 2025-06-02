import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {
    public int x, y, prevY;
    public int width = 32, height = 32;
    public double velocityY = 0;
    public double speed = 5;
    public boolean isAlive = true;
    public int health = 100;

    private Action idleAnimation;
    private Action walkLeftAnimation;
    private Action walkRightAnimation;
    private Action currentAnimation;

    private boolean movingLeft = false;
    private boolean movingRight = false;

    public Player(int x, int y, BufferedImage[] idleFrames,
                  BufferedImage[] walkRightFrames,
                  BufferedImage[] walkLeftFrames) {
        this.x = x;
        this.y = y;
        this.prevY = y;
        this.idleAnimation = new Action(idleFrames, 5);
        this.walkRightAnimation = new Action(walkRightFrames, 5);
        this.walkLeftAnimation = new Action(walkLeftFrames, 5);
        this.currentAnimation = idleAnimation;
    }

    public void update() {
        prevY = y;
        velocityY += 0.5;
        y += velocityY;

        if (movingLeft) {
            currentAnimation = walkLeftAnimation;
        } else if (movingRight) {
            currentAnimation = walkRightAnimation;
        } else {
            currentAnimation = idleAnimation;
        }

        currentAnimation.update();
    }

    public void moveLeft() {
        x -= speed;
        movingLeft = true;
        movingRight = false;
    }

    public void moveRight() {
        x += speed;
        movingRight = true;
        movingLeft = false;
    }

    public void stopMoving() {
        movingLeft = false;
        movingRight = false;
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
        g.drawImage(currentAnimation.getCurrentFrame(), x, y, width, height, null);
    }
}