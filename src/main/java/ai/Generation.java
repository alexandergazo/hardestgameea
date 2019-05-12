package ai;

import game.Action;
import game.Board;
import game.elements.Element;
import game.elements.Player;

import java.awt.*;
import java.util.*;

public class Generation extends Element implements Iterable<AIPlayer> {

    private final double MUTATION_RATE = 0.01;
    private final int INCREMENT_RATE = 10;
    private final int INCREMENT_SIZE = 5;
    private final double CROSSOVER_PROBABILITY = 0.5;

    private AIPlayer[] generation;
    private AIPlayer winner = null;
    private boolean finished = false;
    private int generationNumber = 0;

    private Random random = new Random();

    /**
     * Creates a new {@code Generation} object which represents a set of evolving {@code AIPlayer} objects.
     * @param board        The parent {@code board} on which is the generation created.
     * @param size         The fixed number of {@code AIPlayer} objects in the generation.
     * @param initialMoves The number of initial moves of every {@code AIPlayer} object generation.
     */
    public Generation(Board board, int size, int initialMoves) {
        generation = new AIPlayer[size];

        for (int i = 0; i < generation.length; i++) {
            generation[i] = new AIPlayer(
                    board,
                    board.levelConfig.aiPlayerConfig,
                    initialMoves);
        }
    }

    public AIPlayer getWinner() {
        return winner;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getGenerationNumber() {
        return generationNumber;
    }

    /**
     * Creates a new generation of players out of the current generation and assigns it into the private
     * {@code generation} field.
     */
    private void createNewGen() {
        finished = false;
        generationNumber++;

        AIPlayer[] selectedPlayers = select();
        for (int i = 0; i < selectedPlayers.length; i += 2) {
            crossover(selectedPlayers[i], selectedPlayers[i + 1]);
            mutate(selectedPlayers[i]);
            mutate(selectedPlayers[i + 1]);
        }
        generation = selectedPlayers;

        if (generationNumber % INCREMENT_RATE == 0) {
            for (AIPlayer p : generation) {
                p.addRndMoves(INCREMENT_SIZE);
            }
        }
    }

    /**
     * For each move of a specified {@code AIPlayer} decides with a fixed probability
     * whether to modify the move and then modifies it.
     * @param p The {@code AIPlayer} which moves will be mutated.
     */
    private void mutate(AIPlayer p) {
        for (int i = 0; i < p.getMoves().size(); i++) {
            if (random.nextDouble() <= MUTATION_RATE) {
                p.getMoves().remove(i);
                p.getMoves().add(i, Action.rndAction());
            }
        }
    }

    /**
     * Combines two supplied {@code AIPlayer} objects between each other.
     * @param p1 The first {@code AIPlayer} object.
     * @param p2 The second {@code AIPlayer} object.
     */
    private void crossover(AIPlayer p1, AIPlayer p2) {
        ArrayList<Action> m1 = p1.getMoves();
        ArrayList<Action> m2 = p2.getMoves();
        ArrayList<Action> newMoves1 = new ArrayList<>(m1.size());
        ArrayList<Action> newMoves2 = new ArrayList<>(m2.size());
        for (int i = 0; i < m1.size(); i++) {
            double d = random.nextDouble();
            if (d > CROSSOVER_PROBABILITY) {
                newMoves1.add(m1.get(i));
                newMoves2.add(m2.get(i));
            } else {
                newMoves1.add(m2.get(i));
                newMoves2.add(m1.get(i));
            }
        }
        p1.setMoves(newMoves1);
        p2.setMoves(newMoves2);
    }

    /**
     * Selects perspective {@code AIPlayer} objects from current generation and fills an array with length of
     * generation with them. Same {@code AIPlayer} object can appear in this array more than once.
     * @return Array of perspective {@code AIPlayer} objects.
     */
    private AIPlayer[] select() {
        AIPlayer[] selectedPlayers = new AIPlayer[generation.length];
        Arrays.sort(generation, Comparator.comparingDouble(Player::getFitness));
        double[] accumulatedFit = new double[generation.length];
        int l = generation.length;
        accumulatedFit[0] = generation[l - 1].getFitness();
        for (int i = 1; i < generation.length; i++) {
            accumulatedFit[i] = generation[l - i - 1].getFitness() + accumulatedFit[i - 1];
        }
        for (int j = 0; j < generation.length; j++) {
            int n = random.nextInt((int) accumulatedFit[l - 1]);
            for (int i = 0; i < accumulatedFit.length; i++) {
                if (accumulatedFit[i] > n) {
                    selectedPlayers[j] = new AIPlayer(generation[l - i - 1]);
                    selectedPlayers[j].reset();
                    break;
                }
            }
        }
        return selectedPlayers;
    }

    @Override
    public void render(Graphics g) {
        for (AIPlayer player : generation) {
            player.render(g);
        }
    }

    @Override
    public void update() {
        if (winner != null) {
            return;
        }
        if (isFinished()) {
            createNewGen();
            return;
        }
        for (AIPlayer player : generation) {
            player.update();
            if (player.isFinished()) winner = player;
        }
        finished = Arrays.stream(generation).filter(p -> p.isDead() || p.isOutOfMoves()).count() == generation.length;
    }

    @Override
    public Iterator<AIPlayer> iterator() {
        return Arrays.stream(generation).iterator();
    }
}
