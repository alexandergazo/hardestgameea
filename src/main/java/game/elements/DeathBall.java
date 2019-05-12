package game.elements;

import game.elements.config.DeathBallConfig;

import java.awt.*;

public class DeathBall extends Element {
    private int x1, x2;
    private float fx, fy, dx, dy;
    private final DeathBallConfig cfg;

    /**
     * Creates a new {@code DeathBall} object. This object travels back and forth
     * along a specified line with a speed specified by a travel duration.
     * @param x1 The x parameter of a spawning location.
     * @param y1 The y parameter of a spawning location.
     * @param x2 The x parameter of a turning point.
     * @param y2 The y parameter of a turning point.
     * @param travelDuration The time in milliseconds it takes for the {@code DeathBall} to travel from the
     *                       spawning point to the turning point.
     * @param deathBallConfig Represents the looks and the size of the {@code DeathBall}.
     */
    public DeathBall(int x1, int y1, int x2, int y2, int travelDuration, DeathBallConfig deathBallConfig) {
        fx = x1;
        fy = y1;
        x = (int) fx;
        y = (int) fx;
        this.x1 = Math.min(x1, x2);
        this.x2 = Math.max(x1, x2);
        dx = (float) (x2 - x1) / travelDuration;
        dy = (float) (y2 - y1) / travelDuration;
        this.cfg = deathBallConfig;
    }

    /**
     * Creates a new {@code DeathBall} object. This object travels back and forth
     * along a specified line with a speed specified by a travel duration.
     * @param path The line specified by a {@code Polygon} object.
     * @param travelDuration The time in milliseconds it takes for the {@code DeathBall} to travel from the
     *                       spawning point to the turning point.
     * @param deathBallConfig Represents the looks and the size of the {@code DeathBall}.
     */
    public DeathBall(Polygon path, int travelDuration, DeathBallConfig deathBallConfig) {
        this(path.xpoints[0], path.ypoints[0], path.xpoints[1], path.ypoints[1], travelDuration, deathBallConfig);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, cfg.SIZE, cfg.SIZE);
    }

    public void update() {
        fx += dx;
        fy += dy;
        x = (int) fx;
        y = (int) fy;
        if (x + dx / 2 < x1) {
            fx = x1;
            dx *= -1;
            dy *= -1;
        } else if (x - dx / 2 > x2) {
            fx = x2;
            dx *= -1;
            dy *= -1;
        }
    }

    public void render(Graphics g) {
        g.setColor(cfg.deathBallColorConfig.BORDER_COLOR);
        g.fillOval(x, y, cfg.SIZE, cfg.SIZE);
        g.setColor(cfg.deathBallColorConfig.FILL_COLOR);
        g.fillOval(
                x + cfg.BORDER_SIZE / 2,
                y + cfg.BORDER_SIZE / 2,
                cfg.SIZE - cfg.BORDER_SIZE,
                cfg.SIZE - cfg.BORDER_SIZE
        );
    }
}
