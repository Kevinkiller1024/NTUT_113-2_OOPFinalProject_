import java.util.*;
import java.awt.*;

public class PlatformManager {
    private ArrayList<Platform> platforms;
    private Random rand = new Random();
    private int panelWidth, panelHeight;
    private static final int SPACING = 70;

    public PlatformManager(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        platforms = new ArrayList<>();
    }

    public void init(Player player) {
        platforms.clear();
        platforms.add(new Platform(player.x, player.y + player.height + 5));

        int lastY = player.y + player.height + 5;

        for (int i = 1; i < 10; i++) {
            int offsetY = SPACING + rand.nextInt(40) - 20;
            int newY = lastY + offsetY;
            int newX = rand.nextInt(panelWidth - Platform.WIDTH);
            Platform.Type type = rand.nextDouble() < 0.15 ? Platform.Type.SPIKE : Platform.Type.NORMAL;
            platforms.add(new Platform(newX, newY, type));
            lastY = newY;
        }
    }

    public void update(int scrollSpeed) {
        for (Platform p : platforms) {
            p.y -= scrollSpeed;
        }

        platforms.removeIf(p -> p.y + Platform.HEIGHT < 0);

        while (platforms.size() < 10) {
            Platform last = platforms.get(platforms.size() - 1);
            int offsetY = SPACING + rand.nextInt(40) - 20;
            int newY = last.y + offsetY;
            int newX = rand.nextInt(panelWidth - Platform.WIDTH);
            Platform.Type type = rand.nextDouble() < 0.15 ? Platform.Type.SPIKE : Platform.Type.NORMAL;
            platforms.add(new Platform(newX, newY, type));
        }
    }

    public boolean checkCollision(Player player) {
        Rectangle currBounds = player.getBounds();

        for (Platform p : platforms) {
            Rectangle plat = p.getBounds();

            boolean onPlatform =
                    player.velocityY >= 0 &&
                            currBounds.y + currBounds.height <= plat.y + 15 &&
                            currBounds.y + currBounds.height >= plat.y - 15 &&
                            currBounds.x + currBounds.width > plat.x &&
                            currBounds.x < plat.x + Platform.WIDTH;

            if (onPlatform) {
                player.y = plat.y - player.height;
                player.velocityY = 0;

                if (p.getType() == Platform.Type.SPIKE) {
                    player.takeDamage();
                }

                return true;
            }
        }
        return false;
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
}
