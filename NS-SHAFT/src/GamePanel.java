public class GamePanel extends JPanel implements KeyListener {
    private Player player;
    private ArrayList<Platform> platforms;
    private boolean leftPressed = false, rightPressed = false;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        player = new Player(200, 300);
        platforms = new ArrayList<>();
        generateInitialPlatforms();
    }

    public void startGame() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() {
                update();
                repaint();
            }
        }, 0, 16); // 每秒 60 次
    }

    private void update() {
        if (leftPressed) player.moveLeft();
        if (rightPressed) player.moveRight();
        player.applyGravity();
        player.update();

        for (Platform p : platforms) {
            if (p.isSteppedOn(player)) {
                player.landOn(p.getY());
                break;
            }
        }
    }

    private void generateInitialPlatforms() {
        for (int i = 0; i < 10; i++) {
            platforms.add(new NormalPlatform((int)(Math.random() * 320 + 40), i * 60 + 100));
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Platform p : platforms) p.draw(g);
        player.draw(g);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    public void keyTyped(KeyEvent e) {}
}