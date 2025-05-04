import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(CardLayout cardLayout, JPanel parent, GamePanel gamePanel) {
        setLayout(new GridBagLayout());
        JButton startButton = new JButton("開始遊戲");
        JButton exitButton = new JButton("結束遊戲");

        startButton.addActionListener(e -> {
            gamePanel.startGame(); // << 在按下時開始遊戲
            cardLayout.show(parent, "game");
            gamePanel.requestFocusInWindow(); // 確保鍵盤事件能正常運作
        });

        exitButton.addActionListener(e -> System.exit(0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(startButton, gbc);

        gbc.gridy = 1;
        add(exitButton, gbc);
    }
}

