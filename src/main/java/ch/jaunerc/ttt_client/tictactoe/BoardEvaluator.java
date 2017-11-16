package ch.jaunerc.ttt_client.tictactoe;

import java.util.Arrays;
import java.util.List;

public class BoardEvaluator {

    public enum BoardSituation {
        WIN, DRAW, OPEN
    }

    public static BoardSituation evaluateBoard(final Board board) {
        BoardSituation situation = BoardSituation.OPEN;
        if (checkForDraw(board)) {
            situation = BoardSituation.DRAW;
        } else if (checkForWin(board, FieldValue.CROSS) ||
                checkForWin(board, FieldValue.NOUGHT)) {
            situation = BoardSituation.WIN;
        }
        return situation;
    }

    private static boolean checkForDraw(final Board board) {
        List<String> values = Arrays.asList(board.getBoard());
        return values.stream()
                .noneMatch(value -> value.equals(FieldValue.EMPTY.getValue()));
    }

    private static boolean checkForWin(final Board board, final FieldValue fieldValue) {
        List<WinSituation> situations = Arrays.asList(WinSituation.getAllWinSituations());
        return situations.stream()
                .anyMatch(winPos -> isWin(board.getBoard(), winPos.getBoardIndexes(), fieldValue.getValue()));
    }

    private static boolean isWin(final String[] boardValues, final int[] winPos, final String playerValue) {
        if (winPos.length == 0) {
            throw new IllegalArgumentException("winPos cannot be empty.");
        }
        for (int pos : winPos) {
            if (!playerValue.equals(boardValues[pos])) {
                return false;
            }
        }
        return true;
    }
}
