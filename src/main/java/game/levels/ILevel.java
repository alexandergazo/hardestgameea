package game.levels;

import game.levels.config.LevelConfig;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface ILevel {
    /**
     * Checks weather a player with the specified size and space is in a finish.
     * @param x The x parameter of the player's top left corner.
     * @param y The y parameter of the player's top left corner.
     * @param PLAYER_SIZE The size of the player.
     * @return Returns a {@code boolean} result of weather the specified player is in a finish.
     */
    boolean isInFinish(int x, int y, int PLAYER_SIZE);

    /**
     * @return Returns an upper bound of the possible distance to a finish.
     */
    double maxDistanceToFin();

    /**
     * Approximates the distance of a player to a finish.
     * @param x The x parameter of the player's top left corner.
     * @param y The y parameter of the player's top left corner.
     * @param PLAYER_SIZE The size of the player.
     * @return Returns a {@code double} value representing the approximate distance of the specified player
     * to a finish.
     */
    double approxDistanceToFin(int x, int y, int PLAYER_SIZE);

    /**
     * @return Returns an {@code ArrayList<Polygon>} of all enemies' (non-playable objects
     * which kill player) travel paths in level.
     */
    ArrayList<Polygon> getEnemiesPaths();

    /**
     * @return Returns a {@code BufferedImage} of the level's background.
     */
    BufferedImage getLevelBackground();

    LevelConfig getConfig();
}
