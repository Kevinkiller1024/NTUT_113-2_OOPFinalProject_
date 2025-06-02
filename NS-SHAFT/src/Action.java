import java.awt.image.BufferedImage;

public class Action {
    private BufferedImage[] frames;
    private int currentFrame = 0;
    private int counter = 0;
    private int delay;

    public Action(BufferedImage[] frames, int delay) {
        this.frames = frames;
        this.delay = delay;
    }

    public void update() {
        if (frames.length <= 1) return;
        counter++;
        if (counter >= delay) {
            currentFrame = (currentFrame + 1) % frames.length;
            counter = 0;
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }

    public void reset() {
        currentFrame = 0;
        counter = 0;
    }
}
