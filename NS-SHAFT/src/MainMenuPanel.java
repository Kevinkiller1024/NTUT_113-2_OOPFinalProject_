import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class MainMenuPanel extends JPanel {
    private BufferedImage backgroundImage;
    private JButton startButton, exitButton;

    public MainMenuPanel(CardLayout cardLayout, JPanel parent, GamePanel gamePanel) {
        setLayout(null);
        setPreferredSize(new Dimension(470, 700));

        // 嘗試讀取背景圖片
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/images/Mainbackground.png"));
        } catch (Exception e) {
            System.out.println("背景圖片載入失敗，使用漸層背景");
            backgroundImage = null;
        }

        JLabel titleLabel = new JLabel("NS-SHAFT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微軟正黑體", Font.BOLD, 48)); //中文字型
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(60, 100, 350, 60);
        add(titleLabel);

        startButton = createButton("開始遊戲");
        startButton.setBounds(135, 250, 200, 50);
        startButton.addActionListener(e -> {
            gamePanel.startGame();
            cardLayout.show(parent, "game");
            gamePanel.requestFocusInWindow();
        });
        add(startButton);

        exitButton = createButton("結束遊戲");
        exitButton.setBounds(135, 320, 200, 50);
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);

        button.setFont(new Font("微軟正黑體", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                AbstractButton b = (AbstractButton) c;

                int width = b.getWidth();
                int height = b.getHeight();

                // 按下、滑過、正常 顏色
                Color startColor = b.getModel().isPressed() ? new Color(30, 90, 160)
                        : b.getModel().isRollover() ? new Color(70, 140, 200)
                        : new Color(50, 120, 180);
                Color endColor = startColor.brighter();

                // 漸層背景
                g2.setPaint(new GradientPaint(0, 0, startColor, 0, height, endColor));
                g2.fillRoundRect(0, 0, width, height, 20, 20);

                // 繪製文字
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(b.getText());
                int textHeight = fm.getAscent();
                g2.setColor(Color.WHITE);
                g2.drawString(b.getText(), (width - textWidth) / 2, (height + textHeight) / 2 - 4);
                g2.dispose();
            }
        });

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, new Color(25, 25, 112), 0, getHeight(), new Color(100, 149, 237));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
