package game.levels.config;

import ai.config.AIPlayerConfig;
import game.elements.config.DeathBallColorConfig;
import game.elements.config.DeathBallConfig;
import game.elements.config.PlayerColorConfig;
import game.elements.config.PlayerConfig;

import java.awt.*;

/**
 * Represents the constants needed for a level creation. Namely the size of the level
 * margin, a player spawning position, a color scheme and an overall offset of the level
 * from the top-left corner of a image. It also contains a {@code DeathBallConfig} object
 * describing the looks and the size of {@code DeathBall} objects in the level and an
 * object {@code PlayerConfig} with a similar function.
 */
public class LevelConfig {
    public final int BLOCK_SIZE;
    public final int OFFSET_X;
    public final int OFFSET_Y;
    public final LevelColorConfig levelColorConfig;
    public final DeathBallConfig deathBallConfig;
    public final PlayerConfig playerConfig;
    public final AIPlayerConfig aiPlayerConfig;

    private static final Color COLLISION_COLOR = Color.BLACK;

    private static final LevelColorConfig DefaultLevel1ColorConfig =
            new LevelColorConfig(
                    COLLISION_COLOR,
                    new Color(182, 183, 231),
                    new Color(182, 255, 162),
                    new Color(248, 249, 231),
                    new Color(231, 232, 231)
            );

    private static final DeathBallColorConfig DefaultLevel1DeathBallColorConfig =
            new DeathBallColorConfig(
                    Color.CYAN,
                    COLLISION_COLOR
            );

    private static final PlayerColorConfig DefaultLevel1PlayerColorConfig =
            new PlayerColorConfig(
                    Color.RED,
                    COLLISION_COLOR
            );

    private static final DeathBallConfig DefaultLevel1DeathBallConfig =
            new DeathBallConfig(
                    16,
                    6,
                    DefaultLevel1DeathBallColorConfig
            );

    private static final PlayerConfig DefaultLevel1PlayerConfig =
            new PlayerConfig(
                    16,
                    4,
                    60,
                    90,
                    DefaultLevel1PlayerColorConfig
            );

    private static final AIPlayerConfig DefaultLevel1AIPlayerConfig =
            new AIPlayerConfig(
                    16,
                    4,
                    60,
                    90,
                    DefaultLevel1PlayerColorConfig,
                    10
            );

    public static final LevelConfig DefaultLevel1Config =
            new LevelConfig(
                    30,
                    30,
                    30,
                    DefaultLevel1ColorConfig,
                    DefaultLevel1DeathBallConfig,
                    DefaultLevel1PlayerConfig,
                    DefaultLevel1AIPlayerConfig
            );

    public LevelConfig(
            int BLOCK_SIZE,
            int OFFSET_X,
            int OFFSET_Y,
            LevelColorConfig levelColorConfig,
            DeathBallConfig deathBallConfig,
            PlayerConfig playerConfig,
            AIPlayerConfig aiPlayerConfig
    ) {
        this.BLOCK_SIZE = BLOCK_SIZE;
        this.OFFSET_X = OFFSET_X;
        this.OFFSET_Y = OFFSET_Y;
        this.levelColorConfig = levelColorConfig;
        this.deathBallConfig = deathBallConfig;
        this.playerConfig = playerConfig;
        this.aiPlayerConfig = aiPlayerConfig;
    }
}
