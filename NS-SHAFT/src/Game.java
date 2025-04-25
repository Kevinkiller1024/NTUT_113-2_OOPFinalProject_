import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("NS-SHAFT in Java");
        GamePanel panel = new GamePanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel.startGame();
    }
}