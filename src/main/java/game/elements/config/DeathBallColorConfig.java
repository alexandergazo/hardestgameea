package game.elements.config;

import java.awt.*;

/**
 * Represents the colors used in a {@code DeathBall} rendering.
 */
public class DeathBallColorConfig {
    public final Color FILL_COLOR;
    public final Color BORDER_COLOR;

    public DeathBallColorConfig(Color FILL_COLOR, Color BORDER_COLOR) {
        this.FILL_COLOR = FILL_COLOR;
        this.BORDER_COLOR = BORDER_COLOR;
    }
}
