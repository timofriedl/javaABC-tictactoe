package game;

import ai.AiStrategy;
import gamefield.UiGameField;

import javax.swing.*;

/**
 * A Tic-Tac-Toe game with a "perfect" AI opponent.
 *
 * @author Timo Friedl
 */
public class Game extends JFrame {
    /**
     * the game field to play on
     */
    private final UiGameField gameField;

    /**
     * the symbol of the player who chooses its action next
     */
    private String currentPlayer = "X";

    /**
     * the AI
     */
    private AiStrategy ai;

    /**
     * Creates a new Tic-Tac-Toe game instance.
     */
    public Game() {
        super("TicTacToe");

        ai = new AiStrategy("O", "X");

        gameField = new UiGameField(this);
        add(gameField);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Resets the state of this game.
     */
    private void reset() {
        currentPlayer = "X";
        gameField.reset();

        String newPlayerName = ai.aiPlayer();
        String newAiName = newPlayerName.equals("O") ? "X" : "O";
        ai = new AiStrategy(newAiName, newPlayerName);
    }

    /**
     * Shows a message with the winner of the played round and resets the game field afterwards.
     *
     * @param winner the symbol of the winner, or "tie" if there is no winner
     */
    private void endGame(String winner) {
        String message = winner.equals(ai.aiPlayer()) ? "Die KI hat gewonnen!"
                : winner.equals(ai.humanPlayer()) ? "Du hast gewonnen!"
                : "Unentschieden!";

        JOptionPane.showMessageDialog(null, message);
        reset();
    }

    /**
     * Swaps the symbol of the current player from "X" to "O" or vice versa.
     */
    private void swapCurrentPlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    /**
     * If there is a winner in terms of the current game field, the game ends.
     * Otherwise, the opponent will choose its next action.
     */
    private void checkForWinner() {
        String winner = gameField.findWinner();
        if (winner != null)
            endGame(winner);
        else
            swapCurrentPlayer();
    }

    /**
     * Handles the action after a certain button has been clicked by the user.
     *
     * @param button the butten that has been clicked
     */
    public void buttonClicked(JButton button) {
        if (currentPlayer.equals(ai.aiPlayer()) || !button.getText().isEmpty())
            return;

        button.setText(currentPlayer);
        checkForWinner();

        while (currentPlayer.equals(ai.aiPlayer())) {
            gameField.setEnabled(false);
            ai.makeMove(gameField);
            gameField.setEnabled(true);
            checkForWinner();
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
