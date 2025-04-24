public class NormalPlatform extends Platform {
    public NormalPlatform(int x, int y) {
        super(x, y, 80, 10);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }
}