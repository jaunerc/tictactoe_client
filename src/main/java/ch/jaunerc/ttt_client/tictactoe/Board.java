package ch.jaunerc.ttt_client.tictactoe;

public class Board {
    private final static int BOARD_SIZE = 9;

    private String[] values;

    public Board() {
        values = new String[BOARD_SIZE];
        emptyBoard();
    }

    private void emptyBoard() {
        for (int i = 0; i < values.length; i++) {
            values[i] = FieldValue.EMPTY.getValue();
        }
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public void setValueAt(final int pos, final FieldValue fieldValue) throws IllegalArgumentException {
        if (pos >= 0 && pos < values.length) {
            values[pos] = fieldValue.getValue();
        } else {
            throw new IllegalArgumentException("The board position is out of bounds.");
        }
    }
}
