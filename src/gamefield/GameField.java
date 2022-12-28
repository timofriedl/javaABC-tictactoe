package gamefield;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A 3x3 game field consisting of symbols "X" and "O" (and empty entries).
 */
public interface GameField {
    /**
     * Sets the entry at position (x|y) to a certain string value ("X", "O", or "").
     *
     * @param x     the horizontal coordinate, indexed [0, 2]
     * @param y     the vertical coordinate, indexed [0, 2]
     * @param value the string value to set
     */
    void set(int x, int y, String value);

    /**
     * Returns the symbol at position (x|y), or an empty string if the entry is not set.
     *
     * @param x the horizontal coordinate, indexed [0, 2]
     * @param y the vertical coordinate, indexed [0, 2]
     * @return the symbol at the given position
     */
    String get(int x, int y);

    /**
     * Checks whether a certain row is full of identical symbols.
     *
     * @param y the row number, indexed [0, 2]
     * @return true iff the given row is either ["X", "X", "X"], or ["O", "O", "O"]
     */
    private boolean isFullRow(int y) {
        String left = get(0, y);
        return !left.isEmpty() && left.equals(get(1, y)) && left.equals(get(2, y));
    }

    /**
     * Checks whether a certain column is full of identical symbols.
     *
     * @param x the column number, indexed [0, 2]
     * @return true iff the given column is either ["X", "X", "X"], or ["O", "O", "O"]
     */
    private boolean isFullColumn(int x) {
        String upper = get(x, 0);
        return !upper.isEmpty() && upper.equals(get(x, 1)) && upper.equals(get(x, 2));
    }

    /**
     * Checks whether one (or both) of the diagonals is full of identical symbols.
     *
     * @return true iff a diagonal is either ["X", "X", "X"], or ["O", "O", "O"]
     */
    private boolean isFullDiagonal() {
        String center = get(1, 1);
        boolean firstDiagFull = center.equals(get(0, 0)) && center.equals(get(2, 2));
        boolean secondDiagFull = center.equals(get(2, 0)) && center.equals(get(0, 2));
        return !center.isEmpty() && (firstDiagFull || secondDiagFull);
    }

    /**
     * @return the set of empty locations on this {@link GameField}
     */
    default Set<Point> getFreeLocations() {
        Set<Point> freeLocations = new HashSet<>();
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                if (get(x, y).equals(""))
                    freeLocations.add(new Point(x, y));
        return freeLocations;
    }

    /**
     * @return true iff all entries are either "X" or "O", but not empty
     */
    default boolean isFinished() {
        return getFreeLocations().size() == 0;
    }

    /**
     * Determines the symbol of the winner, if there is one.
     *
     * @return "X" or "O" if there is a winner, "tie" if the game is over but there is no winner, or <code>null</code> if the game is not over
     */
    default String findWinner() {
        // Full row
        for (int y = 0; y < 3; y++)
            if (isFullRow(y))
                return get(0, y);

        // Full column
        for (int x = 0; x < 3; x++)
            if (isFullColumn(x))
                return get(x, 0);

        // Full diagonal
        if (isFullDiagonal())
            return get(1, 1);

        // Tie or no winner yet
        return isFinished() ? "tie" : null;
    }

    /**
     * Creates a new {@link GameField} that can be obtained by setting one more entry to a certain symbol.
     *
     * @param p    the {@link Point} to set the symbol
     * @param name the symbol to set
     * @return a new {@link FastGameField} instance with all entries of this plus the added symbol at point <code>p</code>
     */
    default GameField append(Point p, String name) {
        var result = new FastGameField(this); // Copy
        result.set(p.x, p.y, name);
        return result;
    }
}
