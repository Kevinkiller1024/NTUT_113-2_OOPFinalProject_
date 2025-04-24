public abstract class Platform {
    protected int x, y, width, height;

    public Platform(int x, int y, int width, int height) {
        this.x = x; this.y = y; this.width = width; this.height = height;
    }

    public abstract void draw(Graphics g);

    public boolean isSteppedOn(Player player) {
        Rectangle playerRect = player.getBounds();
        Rectangle platformRect = new Rectangle(x, y, width, height);
        return playerRect.intersects(platformRect)
                && player.getBottomY() <= y + 10
                && player.getVy() >= 0;
    }

    public int getY() { return y; }
    public int getHeight() { return height; }
}