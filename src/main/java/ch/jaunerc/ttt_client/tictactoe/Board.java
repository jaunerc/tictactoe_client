package ch.jaunerc.ttt_client.tictactoe;

public class Board {
    private final static int BOARD_SIZE = 9;

    private String[] board;

    public Board() {
        board = new String[BOARD_SIZE];
        emptyBoard();
    }

    private void emptyBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = FieldValue.EMPTY.getValue();
        }
    }

    public String[] getBoard() {
        return board;
    }

    public void setBoard(String[] board) {
        this.board = board;
    }

    public void setValueAt(final int pos, final FieldValue fieldValue) throws IllegalArgumentException {
        if (pos >= 0 && pos < board.length) {
            board[pos] = fieldValue.getValue();
        } else {
            throw new IllegalArgumentException("The board position is out of bounds.");
        }
    }
}
