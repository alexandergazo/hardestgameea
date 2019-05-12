import game.Board;
import game.levels.Level1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JFrame {

    private Board board;

    public Main() {
        init();
    }

    private void init() {
        board = new Board(Level1.DefaultLevel1);
        add(board);

        setResizable(false);
        pack();

        addKeyListener(new TAdapter());

        setTitle("World's hardest game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            board.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            board.keyPressed(e);
        }
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new Main();
            ex.setVisible(true);
        });
    }
}