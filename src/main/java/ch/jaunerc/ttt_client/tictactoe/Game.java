package ch.jaunerc.ttt_client.tictactoe;

public class Game {

    enum GameState {
        EMPTY, RUNNING, OVER
    }

    private Player currentPlayer;
    private Player otherPlayer;
    private Board board;
    private GameState gameState;
    private BoardEvaluator.BoardSituation boardSituation;

    public Game(Board board, Player currentPlayer, Player otherPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.otherPlayer = otherPlayer;
        gameState = GameState.EMPTY;
    }

    public void move(final int boardPos) throws IllegalArgumentException, IllegalStateException {
        switch (gameState) {
            case EMPTY:
            case RUNNING:
                handleMove(boardPos);
                break;
            case OVER:
                throw new IllegalStateException("The game is already over.");
            default:
                throw new IllegalStateException("The game is in an unknown state.");
        }
    }

    private void handleMove(final int boardPos) {
        board.setValueAt(boardPos, currentPlayer.getFieldValue());
        evaluateBoard();
        if (boardSituation == BoardEvaluator.BoardSituation.OPEN) {
            switchPlayers();
        } else {
            gameState = GameState.OVER;
        }
    }

    private void evaluateBoard() {
        boardSituation = BoardEvaluator.evaluateBoard(board);
    }

    private void switchPlayers() {
        final Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }

    public GameState getGameState() {
        return gameState;
    }

    public BoardEvaluator.BoardSituation getBoardSituation() {
        return boardSituation;
    }

    public FieldValue getCurrentFieldValue() {
        return currentPlayer.getFieldValue();
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
