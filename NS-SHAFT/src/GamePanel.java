import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements ActionListener, KeyListener, MouseListener {
    private Timer timer;
    private Player player;
    private PlatformManager platformManager;
    private ScoreManager scoreManager;
    private int panelWidth = 470, panelHeight = 700;
    private HashSet<Platform> scored = new HashSet<>();
    private boolean leftPressed = false, rightPressed = false;
    private boolean isNewHighScore = false;
    private BufferedImage backgroundImage = null;

    public GamePanel() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        platformManager = new PlatformManager(panelWidth, panelHeight);
        scoreManager = new ScoreManager();
        timer = new Timer(16, this);
    }

    private void loadBackground(String path) {
        BufferedImage rawImage = ImageLoader.loadImage(path);
        if (rawImage != null) {
            backgroundImage = new BufferedImage(rawImage.getWidth(), rawImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = backgroundImage.createGraphics();
            g2d.drawImage(rawImage, 0, 0, null);
            g2d.setColor(new Color(0, 0, 0, 100)); // 半透明黑色，視覺更聚焦
            g2d.fillRect(0, 0, rawImage.getWidth(), rawImage.getHeight());
            g2d.dispose();
        } else {
            System.out.println("背景圖片載入失敗: " + path);
        }
    }


    private void initGame() {
        loadBackground("/images/Gamebackground.png");  // 請確認路徑正確

        BufferedImage spriteSheet = ImageLoader.loadImage("/images/Player.png");

        BufferedImage[] idleFrames = new BufferedImage[4];
        BufferedImage[] walkRightFrames = new BufferedImage[4];
        BufferedImage[] walkLeftFrames = new BufferedImage[4];

        for (int i = 0; i < 4; i++) {
            idleFrames[i] = spriteSheet.getSubimage(i * 16, 0 * 16, 16, 16);
            walkRightFrames[i] = spriteSheet.getSubimage(i * 16, 2 * 16, 16, 16);
            walkLeftFrames[i] = spriteSheet.getSubimage(i * 16, 3 * 16, 16, 16);
        }

        player = new Player(panelWidth / 2 - 16, 250, idleFrames, walkRightFrames, walkLeftFrames);
        platformManager.init(player);
        scoreManager.reset();
        scored.clear();
        player.isAlive = true;
        player.health = 100;
        isNewHighScore = false;
        timer.start();
    }

    public void startGame() {
        initGame();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!player.isAlive) return;

        player.update();

        int scrollSpeed = scoreManager.getScrollSpeed();
        player.speed = Math.min(10.0, 3.0 + scrollSpeed * 0.5);

        if (leftPressed && player.x > 0) player.moveLeft();
        if (rightPressed && player.x + player.width < panelWidth) player.moveRight();

        platformManager.update(scrollSpeed, player);
        platformManager.checkCollision(player);

        for (Platform p : platformManager.getPlatforms()) {
            if (!scored.contains(p) && p.y + Platform.HEIGHT < player.y + player.height) {
                scored.add(p);
                scoreManager.increase();
            }
        }

        if (player.y > panelHeight || player.y < 0) {
            player.isAlive = false;
            if (scoreManager.shouldUpdateHighScore()) {
                scoreManager.updateHighScoreIfNeeded();
                isNewHighScore = true;
            }
        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, panelWidth, panelHeight, null);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, panelWidth, panelHeight);
        }

        for (Platform p : platformManager.getPlatforms()) {
            p.draw(g);
        }
        player.draw(g);

        Graphics2D g2 = (Graphics2D) g;

        // 半透明底板，包覆分數和血條區域
        g2.setColor(new Color(150, 150, 150, 150));
        g2.fillRoundRect(5, 5, 170, 45, 15, 15);

        // 白字黑描邊分數字體
        String scoreText = "Score: " + scoreManager.getScore();
        String highScoreText = "High Score: " + scoreManager.getHighScore();

        drawOutlinedText(g2, scoreText, 10, 20, Color.WHITE, Color.BLACK);
        drawOutlinedText(g2, highScoreText, 10, 40, Color.WHITE, Color.BLACK);

        // 血條外框+底色
        g2.setColor(Color.BLACK);
        g2.drawRect(360, 10, 100, 10);
        g2.setColor(new Color(50, 50, 50)); // 深灰底
        g2.fillRect(360, 10, 100, 10);

        // 鮮紅血條
        g2.setColor(Color.RED);
        g2.fillRect(360, 10, player.health, 10);

        if (!player.isAlive) {
            g2.setFont(new Font("Arial", Font.BOLD, 32));
            String gameOver = "Game Over";
            int goWidth = g2.getFontMetrics().stringWidth(gameOver);
            int goHeight = g2.getFontMetrics().getHeight();

            g2.setFont(new Font("Arial", Font.PLAIN, 18));
            String restartText = "Click to restart";
            int rtWidth = g2.getFontMetrics().stringWidth(restartText);
            int rtHeight = g2.getFontMetrics().getHeight();

            String newHighScoreText = isNewHighScore ? "New High Score!" : "";
            int nhsWidth = 0, nhsHeight = 0;
            if (isNewHighScore) {
                nhsWidth = g2.getFontMetrics().stringWidth(newHighScoreText);
                nhsHeight = g2.getFontMetrics().getHeight();
            }

            int maxWidth = Math.max(goWidth, Math.max(rtWidth, nhsWidth));
            int paddingX = 20;
            int paddingY = 15;

            int boxX = panelWidth / 2 - (maxWidth / 2) - paddingX;
            int boxY = panelHeight / 2 - goHeight - paddingY;
            int boxWidth = maxWidth + paddingX * 2;
            int boxHeight = goHeight + rtHeight + nhsHeight + paddingY * 2 + 20; // 加多點空間分隔三行

            // 畫灰底包覆框
            g2.setColor(new Color(100, 100, 100, 200)); // 半透明灰色
            g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 15, 15);

            // 畫 Game Over
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 32));
            g2.drawString(gameOver, panelWidth / 2 - goWidth / 2, panelHeight / 2);

            // 畫 Click to restart
            g2.setFont(new Font("Arial", Font.PLAIN, 18));
            g2.drawString(restartText, panelWidth / 2 - rtWidth / 2, panelHeight / 2 + rtHeight + 10);

            // 畫 New High Score!（若有）
            if (isNewHighScore) {
                g2.setColor(Color.ORANGE);
                g2.drawString(newHighScoreText, panelWidth / 2 - nhsWidth / 2, panelHeight / 2 + rtHeight + nhsHeight + 20);
            }
        }

    }

    private void drawOutlinedText(Graphics2D g2, String text, int x, int y, Color fill, Color outline) {
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(outline);
        // 黑色描邊，4方向微偏移
        g2.drawString(text, x - 1, y - 1);
        g2.drawString(text, x - 1, y + 1);
        g2.drawString(text, x + 1, y - 1);
        g2.drawString(text, x + 1, y + 1);
        // 白色填充
        g2.setColor(fill);
        g2.drawString(text, x, y);
    }


    @Override public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) leftPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) rightPressed = true;
        if (!player.isAlive && e.getKeyCode() == KeyEvent.VK_SPACE) initGame();
    }

    @Override public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            leftPressed = false;
            player.stopMoving();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            rightPressed = false;
            player.stopMoving();
        }
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
