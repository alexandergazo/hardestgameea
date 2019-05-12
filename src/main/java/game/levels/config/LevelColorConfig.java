package game.levels.config;

import java.awt.*;

/**
 * Represents the colors used in a level creation.
 */
public class LevelColorConfig {
    public final Color COLLISION_COLOR;
    public final Color BACKGROUND_COLOR;
    public final Color END_AREA_COLOR;
    public final Color WHITE_FIELD_COLOR;
    public final Color GREY_FIELD_COLOR;

    public LevelColorConfig(Color COLLISION_COLOR, Color BACKGROUND_COLOR, Color END_AREA_COLOR, Color WHITE_FIELD_COLOR, Color GREY_FIELD_COLOR) {
        this.BACKGROUND_COLOR = BACKGROUND_COLOR;
        this.COLLISION_COLOR = COLLISION_COLOR;
        this.END_AREA_COLOR = END_AREA_COLOR;
        this.WHITE_FIELD_COLOR = WHITE_FIELD_COLOR;
        this.GREY_FIELD_COLOR = GREY_FIELD_COLOR;
    }
}
