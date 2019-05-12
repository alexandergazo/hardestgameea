package game.levels;

import game.levels.config.LevelConfig;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Level1 implements ILevel {

    private LevelConfig cfg;

    public static Level1 DefaultLevel1 = new Level1(LevelConfig.DefaultLevel1Config);

    public Level1(LevelConfig cfg) {
        this.cfg = cfg;
    }

    public LevelConfig getConfig() {
        return cfg;
    }

    public boolean isInFinish(int x, int y, int PLAYER_SIZE) {
        return new Rectangle(cfg.OFFSET_X + 15 * cfg.BLOCK_SIZE, cfg.OFFSET_Y, 3 * cfg.BLOCK_SIZE, 6 * cfg.BLOCK_SIZE).intersects(new Rectangle(x, y, PLAYER_SIZE, PLAYER_SIZE));
    }

    public double maxDistanceToFin() {
        return 25 * cfg.BLOCK_SIZE;
    }

    public double approxDistanceToFin(int x, int y, int PLAYER_SIZE) {
        int BLOCK_SIZE = cfg.BLOCK_SIZE;
        int OFFSET_X = cfg.OFFSET_X;
        int OFFSET_Y = cfg.OFFSET_Y;
        int cp1x = OFFSET_X + 3 * BLOCK_SIZE - PLAYER_SIZE;
        int cp1y = OFFSET_Y + 5 * BLOCK_SIZE;
        int cp2x = OFFSET_X + 4 * BLOCK_SIZE;
        int cp2y = OFFSET_Y + 5 * BLOCK_SIZE;
        int cp3x = OFFSET_X + 13 * BLOCK_SIZE;
        int cp3y = OFFSET_Y + BLOCK_SIZE;
        int cp4x = OFFSET_X + 15 * BLOCK_SIZE - PLAYER_SIZE;
        int cp4y = OFFSET_Y;
        double dist = 0;
        if (x > OFFSET_X && x < cp1x) {
            dist = Point.distance(x, y, cp1x, cp1y);
            dist += Point.distance(cp1x, cp1y, cp2x, cp2y);
            dist += Point.distance(cp2x, cp2y, cp3x, cp3y);
            dist += Point.distance(cp3x, cp3y, cp4x, cp4y);
            return dist;
        }
        if (x >= cp1x && x < OFFSET_X + 4 * BLOCK_SIZE) {
            dist = Point.distance(x, y, cp2x, cp2y);
            dist += Point.distance(cp2x, cp2y, cp3x, cp3y);
            dist += Point.distance(cp3x, cp3y, cp4x, cp4y);
            return dist;
        }
        if (x > OFFSET_X + 13 * BLOCK_SIZE && x < cp4x && y < OFFSET_Y + BLOCK_SIZE) {
            dist = cp4x - x;
            return dist;
        }
        if (x >= OFFSET_X + 4 * BLOCK_SIZE && x < OFFSET_X + 14 * BLOCK_SIZE) {
            dist = Point.distance(x, y, cp3x, cp3y);
            dist += Point.distance(cp3x, cp3y, cp4x, cp4y);
            return dist;
        }
        if (x >= OFFSET_X + 14 * BLOCK_SIZE && x < cp4x) {
            dist = Point.distance(x, y, cp4x, cp4y);
            return dist;
        }
        if (x >= cp4x) {
            dist = 0;
            return dist;
        }
        return dist;
    }

    public ArrayList<Polygon> getEnemiesPaths() {
        int BLOCK_SIZE = cfg.BLOCK_SIZE;
        int OFFSET_X = cfg.OFFSET_X;
        int OFFSET_Y = cfg.OFFSET_Y;
        ArrayList<Polygon> e = new ArrayList<>();
        int x1 = OFFSET_X + 4 * BLOCK_SIZE + BLOCK_SIZE / 4;
        int x2 = OFFSET_X + 13 * BLOCK_SIZE + BLOCK_SIZE / 4;
        int y1 = OFFSET_Y + BLOCK_SIZE + BLOCK_SIZE / 4;
        int y2 = OFFSET_Y + 2 * BLOCK_SIZE + BLOCK_SIZE / 4;
        int y3 = OFFSET_Y + 3 * BLOCK_SIZE + BLOCK_SIZE / 4;
        int y4 = OFFSET_Y + 4 * BLOCK_SIZE + BLOCK_SIZE / 4;
        e.add(new Polygon(new int[]{x1, x2}, new int[]{y1, y1}, 2));
        e.add(new Polygon(new int[]{x2, x1}, new int[]{y2, y2}, 2));
        e.add(new Polygon(new int[]{x1, x2}, new int[]{y3, y3}, 2));
        e.add(new Polygon(new int[]{x2, x1}, new int[]{y4, y4}, 2));
        return e;
    }

    public BufferedImage getLevelBackground() {
        int BLOCK_SIZE = cfg.BLOCK_SIZE;
        int OFFSET_X = cfg.OFFSET_X;
        int OFFSET_Y = cfg.OFFSET_Y;
        BufferedImage image = new BufferedImage(18 * BLOCK_SIZE + 2 * OFFSET_X, 6 * BLOCK_SIZE + 2 * OFFSET_Y, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        Color backgroundColor = cfg.levelColorConfig.BACKGROUND_COLOR;
        Color endAreaColor = cfg.levelColorConfig.END_AREA_COLOR;
        Color whiteFieldColor = cfg.levelColorConfig.WHITE_FIELD_COLOR;
        Color greyFieldColor = cfg.levelColorConfig.GREY_FIELD_COLOR;
        g2.setColor(backgroundColor);
        g2.fillRect(0, 0, 18 * BLOCK_SIZE + 2 * OFFSET_X, 6 * BLOCK_SIZE + 2 * OFFSET_Y);
        g2.setColor(endAreaColor);
        g2.fillRect(OFFSET_X, OFFSET_Y, 3 * BLOCK_SIZE, 6 * BLOCK_SIZE);
        g2.fillRect(OFFSET_X + (16 - 1) * BLOCK_SIZE, OFFSET_Y, 3 * BLOCK_SIZE, 6 * BLOCK_SIZE);
        BufferedImage checkBoard = new BufferedImage(10 * BLOCK_SIZE, 4 * BLOCK_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g3 = (Graphics2D) checkBoard.getGraphics();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 4; j++) {
                if ((i + j) % 2 == 0) g3.setColor(greyFieldColor);
                else g3.setColor(whiteFieldColor);
                g3.fillRect(i * BLOCK_SIZE, j * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
        g2.drawImage(checkBoard, OFFSET_X + 4 * BLOCK_SIZE, OFFSET_Y + BLOCK_SIZE, null);
        g2.setColor(whiteFieldColor);
        g2.fillRect(OFFSET_X + 3 * BLOCK_SIZE, OFFSET_Y + 5 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        g2.fillRect(OFFSET_X + 14 * BLOCK_SIZE, OFFSET_Y, BLOCK_SIZE, BLOCK_SIZE);
        g2.setColor(greyFieldColor);
        g2.fillRect(OFFSET_X + 4 * BLOCK_SIZE, OFFSET_Y + 5 * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        g2.fillRect(OFFSET_X + 13 * BLOCK_SIZE, OFFSET_Y, BLOCK_SIZE, BLOCK_SIZE);
        g2.setColor(cfg.levelColorConfig.COLLISION_COLOR);
        g2.setStroke(new BasicStroke(3));
        Polygon p = new Polygon();
        p.addPoint(OFFSET_X, OFFSET_Y);
        p.addPoint(OFFSET_X + 3 * BLOCK_SIZE, OFFSET_Y);
        p.addPoint(OFFSET_X + 3 * BLOCK_SIZE, OFFSET_Y + 5 * BLOCK_SIZE);
        p.addPoint(OFFSET_X + 4 * BLOCK_SIZE, OFFSET_Y + 5 * BLOCK_SIZE);
        p.addPoint(OFFSET_X + 4 * BLOCK_SIZE, OFFSET_Y + BLOCK_SIZE);
        p.addPoint(OFFSET_X + 13 * BLOCK_SIZE, OFFSET_Y + BLOCK_SIZE);
        p.addPoint(OFFSET_X + 13 * BLOCK_SIZE, OFFSET_Y);
        p.addPoint(OFFSET_X + 18 * BLOCK_SIZE, OFFSET_Y);
        p.addPoint(OFFSET_X + 18 * BLOCK_SIZE, OFFSET_Y + 6 * BLOCK_SIZE);
        p.addPoint(OFFSET_X + 15 * BLOCK_SIZE, OFFSET_Y + 6 * BLOCK_SIZE);
        p.addPoint(OFFSET_X + 15 * BLOCK_SIZE, OFFSET_Y + BLOCK_SIZE);
        p.addPoint(OFFSET_X + 14 * BLOCK_SIZE, OFFSET_Y + BLOCK_SIZE);
        p.addPoint(OFFSET_X + 14 * BLOCK_SIZE, OFFSET_Y + 5 * BLOCK_SIZE);
        p.addPoint(OFFSET_X + 5 * BLOCK_SIZE, OFFSET_Y + 5 * BLOCK_SIZE);
        p.addPoint(OFFSET_X + 5 * BLOCK_SIZE, OFFSET_Y + 6 * BLOCK_SIZE);
        p.addPoint(OFFSET_X, OFFSET_Y + 6 * BLOCK_SIZE);
        g2.drawPolygon(p);
        return image;
    }
}
