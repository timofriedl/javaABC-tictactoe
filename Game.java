import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JFrame implements ActionListener {
    // GUI
    private JButton[][] buttons;
    private JPanel gridPanel;

    // Game logic
    private char currentPlayer;
    private int moves;

    public Game() {
        super("TicTacToe");

        initGui();
        resetGame();
    }

    private void initGui() {
        gridPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        gridPanel.setBackground(Color.DARK_GRAY);

        buttons = new JButton[3][3];
        Font buttonFont = new Font("Arial", Font.BOLD, 60);
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                JButton button = new JButton("");
                button.setFont(buttonFont);
                button.setBackground(Color.WHITE);
                button.setFocusable(false);
                button.addActionListener(this);

                buttons[y][x] = button;
                gridPanel.add(button);
            }
        }

        add(gridPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private char findWinner() {
        // Full row
        for (int y = 0; y < 3; y++) {
            String leftText = buttons[y][0].getText();
            String middleText = buttons[y][1].getText();
            String rightText = buttons[y][2].getText();
            if (!leftText.isEmpty() && leftText.equals(middleText) && leftText.equals(rightText)) {
                return leftText.charAt(0);
            }
        }

        // Full column
        for (int x = 0; x < 3; x++) {
            String upperText = buttons[0][x].getText();
            String middleText = buttons[1][x].getText();
            String lowerText = buttons[2][x].getText();
            if (!upperText.isEmpty() && upperText.equals(middleText) && upperText.equals(lowerText)) {
                return upperText.charAt(0);
            }
        }

        // Full diagonal
        String upperLeftText = buttons[0][0].getText();
        String upperRightText = buttons[0][2].getText();
        String centerText = buttons[1][1].getText();
        String lowerLeftText = buttons[2][0].getText();
        String lowerRightText = buttons[2][2].getText();
        if (!centerText.isEmpty() && (centerText.equals(upperLeftText) && centerText.equals(lowerRightText)
                || centerText.equals(upperRightText) && centerText.equals(lowerLeftText))) {
            return centerText.charAt(0);
        }

        // Tie
        if (moves >= 9) {
            return '-';
        }

        // No winner yet
        return '?';
    }

    private void resetGame() {
        currentPlayer = 'X';
        moves = 0;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                buttons[y][x].setText("");
            }
        }
    }

    private void win(char winner) {
        if (winner == '-') {
            JOptionPane.showMessageDialog(null, "Unentschieden!");
        } else {
            JOptionPane.showMessageDialog(null, "Spieler " + winner + " hat gewonnen!");
        }
        resetGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        if (!clickedButton.getText().isEmpty()) {
            return;
        }

        clickedButton.setText(Character.toString(currentPlayer));
        moves++;

        char winner = findWinner();
        if (winner != '?') {
            win(winner);
        } else if (currentPlayer == 'X') {
            currentPlayer = 'O';
        } else {
            currentPlayer = 'X';
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
