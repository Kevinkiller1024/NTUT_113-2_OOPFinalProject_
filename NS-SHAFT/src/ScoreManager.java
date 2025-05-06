import java.io.*;

public class ScoreManager {
    private int score = 0;
    private int highScore = 0;
    private static final String FILE_NAME = "highscore.dat";

    public ScoreManager() {
        loadHighScore();
    }

    public void reset() {
        score = 0;
    }

    public void increase() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    public boolean shouldUpdateHighScore() {
        return score > highScore;
    }

    public void updateHighScoreIfNeeded() {
        if (shouldUpdateHighScore()) {
            highScore = score;
            saveHighScore();
        }
    }

    public int getScrollSpeed() {
        return 3 + (score / 30);
    }

    private void loadHighScore() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(FILE_NAME))) {
            highScore = dis.readInt();
        } catch (IOException e) {
            highScore = 0;
        }
    }

    private void saveHighScore() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_NAME))) {
            dos.writeInt(highScore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
