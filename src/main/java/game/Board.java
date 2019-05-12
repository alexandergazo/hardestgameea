package game;

import ai.AIPlayer;
import ai.Generation;
import game.elements.DeathBall;
import game.elements.Element;
import game.elements.Player;
import game.levels.ILevel;
import game.levels.config.LevelConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Board extends JPanel implements Runnable, ActionListener {
    /**
     * Decides the mode of the application. In case its value is {@code true} the evolution algorithm will be computed,
     * otherwise the player mode will be entered, which means, that the user themselves can play the game.
     */
    private static final boolean PLAYER_MODE = false;

    private int DELAY = 0;
    private BufferedImage backgroundImage;

    private Player player;
    private Generation generation;
    private ArrayList<Element> enemiesList;

    public ILevel level;
    public LevelConfig levelConfig;

    public Board(ILevel level) {
        this.level = level;
        levelConfig = level.getConfig();
        initBoard();
    }

    public boolean isFree(int x, int y) {
        return backgroundImage.getRGB(x, y) != levelConfig.levelColorConfig.COLLISION_COLOR.getRGB();
    }

    private void initBoard() {
        backgroundImage = level.getLevelBackground();
        setPreferredSize(new Dimension(backgroundImage.getWidth(), backgroundImage.getHeight()));

        if (PLAYER_MODE) {
            DELAY = 10;
            player = new Player(this, levelConfig.playerConfig);
        } else {
            DELAY = 0;
            generation = new Generation(this, 1000, 5);
        }

        loadEnemies();
    }

    /**
     * Reverts the level into its starting position.
     */
    private void reset() {
        loadEnemies();
    }

    private void drawLevel(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
    }

    private void drawObjects(Graphics g) {
        drawLevel(g);
        if (generation != null) {
            g.drawString(String.valueOf(generation.getGenerationNumber()), levelConfig.OFFSET_X, levelConfig.OFFSET_Y);
        }
        if (generation != null && generation.isFinished()) {
            return;
        }
        //if (generation != null && generation.getGenerationNumber() < 600) return;
        if (player != null) {
            player.render(g);
        }
        if (generation != null) {
            generation.render(g);
        }
        for (Element e : enemiesList) {
            e.render(g);
        }
    }

    private void update() {
        for (Element e : enemiesList) {
            e.update();
        }
        if (player != null) {
            if (player.isFinished() || player.isDead()) {
                player.reset();
                reset();
                return;
            }
            player.update();
            checkPlayerCollision();
        }
        if (generation != null) {
            if (generation.isFinished()) {
                reset();
            }
            AIPlayer winner = generation.getWinner();
            if (winner != null) {
                DELAY = 10;
                for (AIPlayer player : generation) {
                    player.reset();
                }
                generation = null;
                player = winner;
                player.reset();
                reset();
                return;
            }
            generation.update();
            checkGenCollisions();
        }
    }

    private void loadEnemies() {
        ArrayList<Element> e = new ArrayList<>();
        ArrayList<Polygon> paths = level.getEnemiesPaths();
        for (Polygon path : paths) {
            e.add(new DeathBall(
                    path,
                    levelConfig.BLOCK_SIZE * 3 + levelConfig.BLOCK_SIZE / 3,
                    levelConfig.deathBallConfig)
            );
        }
        enemiesList = e;
    }

    /**
     * For each player in generation checks for collisions with enemies and notifies the player.
     * {@code generation} field must not be null.
     */
    private void checkGenCollisions() {
        for (AIPlayer player : generation) {
            Rectangle rec = player.getBounds();
            for (Element e : enemiesList) {
                Rectangle rec2 = ((DeathBall) e).getBounds();
                if (rec2.intersects(rec)) player.die();
            }
        }
    }

    /**
     * Checks the human-controlled {@code Player} object for collisions with enemies and notifies
     * the {@code Player} object.
     * {@code player} field must not be null.
     */
    private void checkPlayerCollision() {
        Rectangle playerRec = player.getBounds();
        for (Element e : enemiesList) {
            Rectangle shape = ((DeathBall) e).getBounds();
            if (shape.intersects(playerRec)) player.die();
        }
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            update();
            repaint();
            /**/
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 0;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {

                String msg = String.format("Thread interrupted: %s", e.getMessage());

                JOptionPane.showMessageDialog(this, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
            /**/
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();

        Thread renderer = new Thread(this);
        renderer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }

    @Override
    public void paintComponent(Graphics g) {
        drawObjects(g);
    }

    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }
}
