public class ScoreManager {
    private int score = 0;
    private long startTime;

    public ScoreManager() {
        startTime = System.currentTimeMillis();
    }

    public void reset() {
        score = 0;
        startTime = System.currentTimeMillis();
    }

    public void increase() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public int getScrollSpeed() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        return 2 + (int)(elapsed / 5);
    }
}
