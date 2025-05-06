import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("NS-SHAFT in Java");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        GamePanel gamePanel = new GamePanel();
        MainMenuPanel menuPanel = new MainMenuPanel(cardLayout, mainPanel, gamePanel);

        mainPanel.add(menuPanel, "menu");
        mainPanel.add(gamePanel, "game");

        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        cardLayout.show(mainPanel, "menu");
    }
}