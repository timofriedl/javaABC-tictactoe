package gamefield;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * An implementation of {@link GameField} with double purpose:
 * 1) Store the current game field state
 * 2) Provide a GUI
 */
public class UiGameField extends JPanel implements GameField, ActionListener {
    /**
     * a grid of buttons that represent the entries to place symbols in
     */
    private JButton[][] buttons;

    /**
     * a reference to the main {@link Game} instance
     */
    private final Game game;

    /**
     * Creates a new {@link UiGameField} that both stores the current game field state, and provides a GUI.
     *
     * @param game the reference to the main {@link Game} instance
     */
    public UiGameField(Game game) {
        super(new GridLayout(3, 3, 5, 5));
        this.game = game;
        setBackground(Color.DARK_GRAY);
        initButtons();
    }

    /**
     * Creates the grid of clickable {@link JButton}s.
     */
    private void initButtons() {
        buttons = new JButton[3][3];
        Font buttonFont = new Font("Arial", Font.BOLD, 100);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                JButton button = new JButton("");
                button.setFont(buttonFont);
                button.setBackground(Color.WHITE);
                button.setFocusable(false);
                button.addActionListener(this);

                buttons[y][x] = button;
                add(button);
            }
        }
    }

    /**
     * Enables or disables the buttons, i.e. whether the buttons appear clickable or not.
     *
     * @param enabled true if the buttons of this {@link UiGameField} should be enabled, false otherwise
     */
    @Override
    public void setEnabled(boolean enabled) {
        setBackground(enabled ? Color.DARK_GRAY : Color.GRAY);
        for (var row : buttons)
            for (var button : row)
                button.setEnabled(enabled);
    }

    @Override
    public void set(int x, int y, String value) {
        buttons[y][x].setText(value);
    }

    @Override
    public String get(int x, int y) {
        return buttons[y][x].getText();
    }

    /**
     * Sets all entries of this game field to the empty string.
     */
    public void reset() {
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                set(x, y, "");
    }

    /**
     * This method is called when any of the 9 buttons has been clicked.
     * A new {@link Thread} is started to not block the UI thread.
     *
     * @param e the {@link ActionEvent} containing the {@link JButton} that has been clicked.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> game.buttonClicked((JButton) e.getSource())).start();
    }
}
