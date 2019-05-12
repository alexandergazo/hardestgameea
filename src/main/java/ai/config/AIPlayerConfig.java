package ai.config;

import game.elements.config.PlayerColorConfig;
import game.elements.config.PlayerConfig;

public class AIPlayerConfig extends PlayerConfig {
    public final int MOVE_DURATION;

    public AIPlayerConfig(
            int SIZE,
            int BORDER_SIZE,
            int SPAWN_X,
            int SPAWN_Y,
            PlayerColorConfig playerColorConfig,
            int MOVE_DURATION
    ) {
        super(SIZE, BORDER_SIZE, SPAWN_X, SPAWN_Y, playerColorConfig);
        this.MOVE_DURATION = MOVE_DURATION;
    }
}