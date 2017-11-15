package ch.jaunerc.ttt_client.tictactoe;

public class WinSituation {

    private static WinSituation[] ALL_WIN_SITUATIONS = new WinSituation[]{
            new WinSituation(0, 1, 2),
            new WinSituation(3, 4, 5),
            new WinSituation(6, 7, 8),
            new WinSituation(0, 3, 6),
            new WinSituation(1, 4, 7),
            new WinSituation(2, 5, 8),
            new WinSituation(0, 4, 8),
            new WinSituation(2, 4, 6)
    };

    public static WinSituation[] getAllWinSituations() {
        return ALL_WIN_SITUATIONS;
    }

    private int[] boardIndexes;

    public WinSituation(final int a, final int b, final int c) {
        boardIndexes = new int[3];
        boardIndexes[0] = a;
        boardIndexes[1] = b;
        boardIndexes[2] = c;
    }

    public int[] getBoardIndexes() {
        return boardIndexes;
    }
}
