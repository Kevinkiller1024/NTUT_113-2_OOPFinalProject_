import java.awt.*;
import java.util.*;

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
            Platform.Type type = randomType();
            platforms.add(new Platform(newX, newY, type));
            lastY = newY;
        }
    }

    public void update(int scrollSpeed, Player player) {
        for (Platform p : platforms) {
            p.y -= scrollSpeed;
            p.update();
        }

        platforms.removeIf(p -> p.y + Platform.HEIGHT < 0);

        while (platforms.size() < 10) {
            Platform last = platforms.get(platforms.size() - 1);
            int offsetY = SPACING + rand.nextInt(40) - 20;
            int newY = last.y + offsetY;
            int newX = rand.nextInt(panelWidth - Platform.WIDTH);
            Platform.Type type = randomType();
            platforms.add(new Platform(newX, newY, type));
        }

        // 讓玩家踩上 SLOW 平台時緩速
        for (Platform p : platforms) {
            if (p.getType() == Platform.Type.SLOW) {
                Rectangle plat = p.getBounds();
                Rectangle playerBounds = player.getBounds();

                boolean onPlatform =
                        player.velocityY >= 0 &&
                                playerBounds.y + playerBounds.height <= plat.y + 15 &&
                                playerBounds.y + playerBounds.height >= plat.y - 15 &&
                                playerBounds.x + playerBounds.width > plat.x &&
                                playerBounds.x < plat.x + Platform.WIDTH;

                if (onPlatform) {
                    // 緩速垂直速度，讓玩家慢慢穿過平台
                    player.velocityY *= 0.2;
                }
            }
        }
    }

    private Platform.Type randomType() {
        double r = rand.nextDouble();
        if (r < 0.1) return Platform.Type.SPIKE;
        if (r < 0.2) return Platform.Type.BOUNCY;
        if (r < 0.3) return Platform.Type.SLOW;
        return Platform.Type.NORMAL;
    }

    public boolean checkCollision(Player player) {
        Rectangle currBounds = player.getBounds();

        for (Platform p : platforms) {
            if (!p.isVisible()) continue;
            Rectangle plat = p.getBounds();

            boolean onPlatform =
                    player.velocityY >= 0 &&
                            currBounds.y + currBounds.height <= plat.y + 15 &&
                            currBounds.y + currBounds.height >= plat.y - 15 &&
                            currBounds.x + currBounds.width > plat.x &&
                            currBounds.x < plat.x + Platform.WIDTH;

            if (onPlatform && p.getType() != Platform.Type.SLOW) {
                player.y = plat.y - player.height;
                player.velocityY = 0;
                p.onStepped(player);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Platform> getPlatforms() {
        return platforms;
    }
}
