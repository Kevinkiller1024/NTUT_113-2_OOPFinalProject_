import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;

public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {
    private Timer timer;
    private Player player;
    private PlatformManager platformManager;
    private ScoreManager scoreManager;
    private int panelWidth = 470, panelHeight = 700;
    private HashSet<Platform> scored = new HashSet<>();
    private boolean leftPressed = false, rightPressed = false;

    public GamePanel() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        platformManager = new PlatformManager(panelWidth, panelHeight);
        scoreManager = new ScoreManager();
        timer = new Timer(16, this);

        initGame();
    }

    private void initGame() {
        player = new Player(panelWidth / 2 - 15, 100);
        platformManager.init(player);
        scoreManager.reset();
        scored.clear();
        player.isAlive = true;
        player.health = 100;
    }

    public void startGame() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!player.isAlive) return;

        player.update();

        if (leftPressed && player.x > 0) player.moveLeft();
        if (rightPressed && player.x + player.width < panelWidth) player.moveRight();

        int scrollSpeed = scoreManager.getScrollSpeed();
        platformManager.update(scrollSpeed);
        platformManager.checkCollision(player);

        for (Platform p : platformManager.getPlatforms()) {
            if (!scored.contains(p) && p.y + Platform.HEIGHT < player.y + player.height) {
                scored.add(p);
                scoreManager.increase();
            }
        }

        if (player.y > panelHeight || player.y < 0) player.isAlive = false;

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Platform p : platformManager.getPlatforms()) {
            p.draw(g);
        }

        player.draw(g);

        // 分數顯示
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.drawString("Score: " + scoreManager.getScore(), 10, 20);

        // HP Bar
        g.setColor(Color.GRAY);
        g.fillRect(360, 10, 100, 10);
        g.setColor(Color.RED);
        g.fillRect(360, 10, player.health, 10);
        g.setColor(Color.BLACK);
        g.drawRect(360, 10, 100, 10);

        if (!player.isAlive) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("Game Over", panelWidth / 2 - 80, panelHeight / 2);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Click to restart", panelWidth / 2 - 70, panelHeight / 2 + 40);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
        if (!player.isAlive && e.getKeyCode() == KeyEvent.VK_SPACE) initGame();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {
        if (!player.isAlive) initGame();
    }
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
