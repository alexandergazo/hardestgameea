package ai;

import ai.config.AIPlayerConfig;
import game.Action;
import game.Board;
import game.elements.Player;

import java.util.ArrayList;

public class AIPlayer extends Player {
    private AIPlayerConfig cfg;
    private ArrayList<Action> moves;
    private Action currMove;
    private int currMoveIndex = 0;
    private int counter = -1;
    private boolean isOutOfMoves = false;

    AIPlayer(Board board, AIPlayerConfig playerConfig, int initialMoves) {
        super(board, playerConfig);
        cfg = playerConfig;
        moves = new ArrayList<>();
        for (int i = 0; i < initialMoves; i++) {
            moves.add(Action.rndAction());
        }
        getNextMove();
    }

    AIPlayer(AIPlayer player) {
        super(player.board, player.cfg);
        this.cfg = player.cfg;
        this.moves = player.moves;
        this.currMoveIndex = player.currMoveIndex;
        getNextMove();
    }

    boolean isOutOfMoves() {
        return isOutOfMoves;
    }

    ArrayList<Action> getMoves() {
        return moves;
    }

    void setMoves(ArrayList<Action> list) {
        moves = list;
    }

    /**
     * Loads the next move into the {@code currMove} field. For the next {@code MOVE_DURATION}
     * calls it returns the same move.
     */
    private void getNextMove() {
        counter = (counter + 1) % cfg.MOVE_DURATION;
        if (counter != 0) {
            return;
        }
        if (currMoveIndex == moves.size()) {
            isOutOfMoves = true;
            currMove = Action.None;
            return;
        }
        currMove = moves.get(currMoveIndex);
        currMoveIndex++;
    }

    /**
     * Adds a specified number of new random moves into the {@code moves} field.
     * @param count The specified number of moves.
     */
    void addRndMoves(int count) {
        for (int i = 0; i < count; i++) {
            moves.add(Action.rndAction());
        }
    }

    @Override
    public void reset() {
        super.reset();
        isOutOfMoves = false;
        finished = false;
        currMoveIndex = 0;
        counter = -1;
    }

    @Override
    public void update() {
        if (dead) return;
        switch (currMove) {
            case Up:
                if (isFree(x, y - 1)) y -= 1;
                break;
            case Down:
                if (isFree(x, y + 1)) y += 1;
                break;
            case Right:
                if (isFree(x + 1, y)) x += 1;
                break;
            case Left:
                if (isFree(x - 1, y)) x -= 1;
                break;
        }
        if (board.level.isInFinish(x, y, cfg.SIZE)) finished = true;
        getNextMove();
    }
}
