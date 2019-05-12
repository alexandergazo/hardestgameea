package game.elements.config;

/**
 * Represents the constants needed for a {@code Player} creation. Namely its size, the width of its border and its
 * color configuration represented by a {@code PlayerColorConfig} object.
 */
public class PlayerConfig {
    public final int SIZE;
    public final int BORDER_SIZE;
    public final int SPAWN_X;
    public final int SPAWN_Y;
    public final PlayerColorConfig playerColorConfig;

    public PlayerConfig(int SIZE, int BORDER_SIZE, int SPAWN_X, int SPAWN_Y, PlayerColorConfig playerColorConfig) {
        this.SIZE = SIZE;
        this.BORDER_SIZE = BORDER_SIZE;
        this.playerColorConfig = playerColorConfig;
        this.SPAWN_X = SPAWN_X;
        this.SPAWN_Y = SPAWN_Y;
    }
}
