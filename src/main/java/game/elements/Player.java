package game.elements;

import game.Board;
import game.elements.config.PlayerConfig;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Element {
    final PlayerConfig cfg;

    private boolean left, right, up, down;
    protected Board board;
    protected boolean dead = false;
    protected boolean finished = false;

    public Player(Board board, PlayerConfig playerConfig) {
        this.board = board;
        this.x = playerConfig.SPAWN_X;
        this.y = playerConfig.SPAWN_Y;
        cfg = playerConfig;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isDead() {
        return dead;
    }

    public void die() {
        dead = true;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, cfg.SIZE, cfg.SIZE);
    }

    /**
     * Sets the {@code Player}'s coordinates to the location first specified in the constructor and marks it not dead.
     */
    public void reset() {
        x = cfg.SPAWN_X;
        y = cfg.SPAWN_Y;
        dead = false;
    }

    /**
     * Checks whether the specified position on the board is without obstacles.
     * @param x The x parameter of the position.
     * @param y The y parameter of the position.
     * @return Returns the {@code boolean} result of whether the specified position on the board is obstacle-free.
     */
    protected boolean isFree(int x, int y) {
        return board.isFree(x, y)
                && board.isFree(x + cfg.SIZE - 1, y)
                && board.isFree(x, y + cfg.SIZE - 1)
                && board.isFree(x + cfg.SIZE - 1, y + cfg.SIZE - 1);
    }

    /**
     * Calculates the {@code Player}'s fitness. This fulfills the role of an error function.
     * @return Returns the {@code double} value of the {@code Player}'s fitness.
     */
    public double getFitness() {
        double dist = board.level.approxDistanceToFin(x, y, cfg.SIZE);
        double maxDist = board.level.maxDistanceToFin();
        int bonus = 1;
        if (!dead && x > 4 * 30 && y < 6 * 30) bonus = 100000;
        return bonus * Math.pow(maxDist - dist, 2) / maxDist;
    }

    /* alternative fitness function used in earlier versions of the program
    public double getFitness2() {
        double dist = board.level.approxDistanceToFin(x, y, cfg.SIZE);
        double maxDist = board.level.maxDistanceToFin();
        int bonus = 1;
        if (!dead && x > 4 * 30 && y < 6 * 30) bonus = 100;
        return bonus * Math.pow(maxDist - dist, 4) / 1000000;
    }
    */

    @Override
    public void render(Graphics g) {
        if (dead) return;
        g.setColor(cfg.playerColorConfig.BORDER_COLOR);
        g.fillRect(x, y, cfg.SIZE, cfg.SIZE);
        g.setColor(cfg.playerColorConfig.FILL_COLOR);
        g.fillRect(
                x + cfg.BORDER_SIZE / 2,
                y + cfg.BORDER_SIZE / 2,
                cfg.SIZE - cfg.BORDER_SIZE,
                cfg.SIZE - cfg.BORDER_SIZE
        );
    }

    @Override
    public void update() {
        if (dead) return;
        if (up && !down && isFree(x, y - 1)) y -= 1;
        if (right && !left && isFree(x + 1, y)) x += 1;
        if (down && !up && isFree(x, y + 1)) y += 1;
        if (left && !right && isFree(x - 1, y)) x -= 1;
    }

    @Override
    public String toString() {
        return "AIPlayer;" + this.hashCode() + " Fitness: " + this.getFitness();
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            left = true;
        }

        if (key == KeyEvent.VK_RIGHT) {
            right = true;
        }

        if (key == KeyEvent.VK_UP) {
            up = true;
        }

        if (key == KeyEvent.VK_DOWN) {
            down = true;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            left = false;
        }

        if (key == KeyEvent.VK_RIGHT) {
            right = false;
        }

        if (key == KeyEvent.VK_UP) {
            up = false;
        }

        if (key == KeyEvent.VK_DOWN) {
            down = false;
        }
    }

}
