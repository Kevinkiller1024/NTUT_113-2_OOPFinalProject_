import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;
    private ArrayList<Platform> platforms;
    private int panelWidth = 550;
    private int panelHeight = 400;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private Random rand = new Random();

    public GamePanel() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);

        player = new Player(panelWidth / 2 - 15, panelHeight / 2);
        platforms = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            platforms.add(new Platform(rand.nextInt(panelWidth - 60), i * 80));
        }

        timer = new Timer(16, this);
    }

    public void startGame() {
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!player.isAlive) return;

        player.update();

        if (leftPressed) player.moveLeft();
        if (rightPressed) player.moveRight();

        // Screen scroll effect
        if (player.y < panelHeight / 2) {
            int dy = panelHeight / 2 - player.y;
            player.y = panelHeight / 2;
            for (Platform p : platforms) {
                p.y += dy;
            }
        }

        for (Platform p : platforms) {
            if (player.velocityY > 0 && player.getBounds().intersects(p.getBounds())) {
                player.jump();
            }
        }

        // Remove off-screen platforms and add new ones
        platforms.removeIf(p -> p.y > panelHeight);
        while (platforms.size() < 8) {
            int lastY = platforms.get(platforms.size() - 1).y;
            platforms.add(new Platform(rand.nextInt(panelWidth - 60), lastY - 80));
        }

        if (player.y > panelHeight) player.isAlive = false;

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Platform p : platforms) {
            p.draw(g);
        }

        player.draw(g);

        if (!player.isAlive) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 32));
            g.drawString("Game Over", panelWidth / 2 - 80, panelHeight / 2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
