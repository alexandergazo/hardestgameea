package game.elements.config;

/**
 * Represents the constants needed for a {@code DeathBall} creation. Namely its size, the width of its border and its
 * color configuration represented by a {@code DeathBallColorConfig} object.
 */
public class DeathBallConfig {
    public final int SIZE;
    public final int BORDER_SIZE;
    public final DeathBallColorConfig deathBallColorConfig;

    public DeathBallConfig(int SIZE, int BORDER_SIZE, DeathBallColorConfig deathBallColorConfig) {
        this.SIZE = SIZE;
        this.BORDER_SIZE = BORDER_SIZE;
        this.deathBallColorConfig = deathBallColorConfig;
    }
}
