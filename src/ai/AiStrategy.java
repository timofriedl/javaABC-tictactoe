package ai;

import gamefield.GameField;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A Tic-Tac-Toe AI that uses the {@link MiniMax} algorithm.
 *
 * @author Timo Friedl
 */
public record AiStrategy(String aiPlayer, String humanPlayer) implements MiniMax<GameField, Point, Integer> {
    /**
     * Computes the {@link Set} of possible {@link GameField} configurations that could occur
     * after the AI executed a certain action on the current game field,
     * and the human player potentially answered with its action.
     *
     * @param gameField the current game field configuration
     * @param action    the (x|y) {@link Point}, where the AI places its symbol
     * @return a set of all possible result game fields
     */
    @Override
    public Set<GameField> possibleOutcome(GameField gameField, Point action) {
        gameField = gameField.append(action, aiPlayer);
        if (gameField.findWinner() != null)
            return Set.of(gameField);

        Set<GameField> possibleOutcome = new HashSet<>();
        for (Point p : gameField.getFreeLocations())
            possibleOutcome.add(gameField.append(p, humanPlayer));

        return possibleOutcome;
    }

    /**
     * Computes the set of all possible (x|y) points, where the AI could place its next symbol.
     *
     * @param gameField the current {@link GameField} configuration
     * @return the set of all possible points
     */
    @Override
    public Set<Point> possibleActions(GameField gameField) {
        return gameField.findWinner() == null ? gameField.getFreeLocations() : Set.of();
    }

    /**
     * Computes, how valuable the current {@link GameField} configuration is for the AI,
     * assuming that the current game field already has a winner (or is tied).
     * A higher score means higher value in favor of the AI.
     *
     * @param gameField the current game field configuration
     * @return an {@link Integer} value that represents the state value for the AI
     */
    @Override
    public Integer baseStateValue(GameField gameField) {
        String winner = gameField.findWinner();
        if (winner.equals(aiPlayer))
            return 2;
        else if (winner.equals(humanPlayer))
            return 0;
        else if (winner.equals("tie"))
            return 1;
        else throw new IllegalStateException("baseStateValue function could not find winner");
    }

    /**
     * Places the AI's symbol at the best possible position for the AI.
     *
     * @param gameField the current {@link GameField} configuration
     */
    public void makeMove(GameField gameField) {
        Point bestMove = bestAction(gameField);
        gameField.set(bestMove.x, bestMove.y, aiPlayer);
    }
}
