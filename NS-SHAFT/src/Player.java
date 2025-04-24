public class Player {
    private int x, y, width = 30, height = 30;
    private double vy = 0;
    private final double GRAVITY = 0.5;
    private final int MOVE_SPEED = 5;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() { x -= MOVE_SPEED; }
    public void moveRight() { x += MOVE_SPEED; }
    public void applyGravity() { vy += GRAVITY; }
    public void update() { y += vy; }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getBottomY() {
        return y + height;
    }

    public double getVy() {
        return vy;
    }

    public void landOn(int platformY) {
        vy = 0;
        y = platformY - height;
    }
}