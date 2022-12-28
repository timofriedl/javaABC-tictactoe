package gamefield;

/**
 * A faster implementation for {@link GameField} that is not usable for GUI purpose.
 */
public class FastGameField implements GameField {
    /**
     * The grid of symbols in the game field.
     */
    private final String[][] entries;

    /**
     * Copy constructor.
     *
     * @param gameField another {@link GameField} instance to copy
     */
    public FastGameField(GameField gameField) {
        entries = new String[3][3];
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 3; x++)
                entries[y][x] = gameField.get(x, y);
    }

    @Override
    public void set(int x, int y, String value) {
        entries[y][x] = value;
    }

    @Override
    public String get(int x, int y) {
        return entries[y][x];
    }
}
