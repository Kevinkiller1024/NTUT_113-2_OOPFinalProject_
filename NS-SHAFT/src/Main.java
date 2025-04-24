import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("NS-Shaft Clone");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(400, 600);

        GamePanel panel = new GamePanel();
        frame.add(Panel);
        frame.setVisible(true);
        panel.startGame();
    }
}