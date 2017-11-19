package ch.jaunerc.ttt_client.controller;

import ch.jaunerc.ttt_client.jersey.RestConnectionHandler;
import ch.jaunerc.ttt_client.tictactoe.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 * This class represents a controller for the tic tac toe view.
 */
public class TttViewController {

    @FXML
    private Canvas tttCanvas;
    @FXML
    private Button restartButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label currentPlayerLabel;

    private Game game;
    private GraphicsContext gc;
    private ClickAreaMapper ClickAreaMapper;
    private EventHandler<MouseEvent> mouseClickEventHandler;
    private RestConnectionHandler restConnectionHandler;

    private double verticalPadding;
    private double horizontalPadding;
    private double verticalSpaceBetweenLines;
    private double horizontalSpaceBetweenLines;

    public TttViewController() {
    }

    @FXML
    private void initialize() {
        initGame();
        initCanvas();
        initUiControls();
    }

    private void initGame() {
        final Board board = new Board();
        final Player playerOne = new Player(FieldValue.NOUGHT);
        final AiPlayer playerTwo = new AiPlayer(FieldValue.CROSS);
        game = new Game(board, playerOne, playerTwo);
        try {
            restConnectionHandler = new RestConnectionHandler(new URI("http://localhost:8080/minimax"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCanvas() {
        final double canvasWidth = tttCanvas.getWidth();
        final double canvasHeight = tttCanvas.getHeight();
        verticalPadding = 0.1 * canvasHeight;
        final double verticalLineLength = canvasHeight - 2 * verticalPadding;
        horizontalPadding = 0.1 * canvasWidth;
        final double horizontalLineLength = canvasWidth - 2 * horizontalPadding;
        verticalSpaceBetweenLines = verticalLineLength / 3;
        horizontalSpaceBetweenLines = horizontalLineLength / 3;

        gc = tttCanvas.getGraphicsContext2D();
        drawEmptyBoard();

        ClickAreaMapper = new ClickAreaMapper();
        ClickAreaMapper.setBoardStartX(horizontalPadding);
        ClickAreaMapper.setBoardStartY(verticalPadding);
        ClickAreaMapper.initAreas(horizontalSpaceBetweenLines, verticalSpaceBetweenLines, gc.getLineWidth());
        initClickHandler();
        setMouseClickHandlerToCanvas();
    }

    private void initClickHandler() {
        mouseClickEventHandler = (event) -> {
            final ClickArea clickArea = ClickAreaMapper.getClickedArea(event);
            drawCrossOrNought(clickArea);
            game.move(clickArea.getAreaNumber());
            setCurrentPlayerText();
            if (game.getCurrentPlayer() instanceof AiPlayer) {
                removeMouseClickHandlerFromCanvas();
                handleAiMoveAsync();
            }
        };
    }

    private void setMouseClickHandlerToCanvas() {
        tttCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickEventHandler);
    }

    private void removeMouseClickHandlerFromCanvas() {
        tttCanvas.removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickEventHandler);
    }

    private void drawCrossOrNought(final ClickArea clickArea) {
        switch (game.getCurrentFieldValue()) {
            case CROSS:
                drawCross(clickArea);
                break;
            case NOUGHT:
                drawNought(clickArea);
        }
    }

    private void handleAiMoveAsync() {
        final Board currentBoard = game.getBoard();
        CompletableFuture<Board> backendTaskFuture = getAskBackendForNextMoveFuture(currentBoard);
        handleBackendFutureResult(backendTaskFuture, currentBoard);
    }

    private CompletableFuture<Board> getAskBackendForNextMoveFuture(final Board currentBoard) {
        return CompletableFuture.supplyAsync(() ->
                restConnectionHandler.getNextTurn(currentBoard)
        );
    }

    private void handleBackendFutureResult(final CompletableFuture<Board> backendFuture, final Board currentBoard) {
        backendFuture.thenAccept(nextBoard -> {
            final String[] currentValues = currentBoard.getBoard();
            final String[] nextValues = nextBoard.getBoard();
            for (int i = 0; i < nextValues.length; i++) {
                if (!nextValues[i].equals(currentValues[i])) {
                    ClickArea clickArea = ClickAreaMapper.getAreaByIndex(i);
                    drawCrossOrNought(clickArea);
                    game.move(i);
                    Platform.runLater(() -> setCurrentPlayerText());    // Execution on FX thread
                    break;
                }
            }
            setMouseClickHandlerToCanvas();
        });
    }

    private void initUiControls() {
        setCurrentPlayerText();
        restartButton.setOnMouseClicked(event -> {
            drawEmptyBoard();
            initGame();
        });
        exitButton.setOnMouseClicked(event -> Platform.exit());
    }

    private void setCurrentPlayerText() {
        currentPlayerLabel.setText(game.getCurrentFieldValue().getValue());
    }

    private void overwriteCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, tttCanvas.getWidth(), tttCanvas.getHeight());
    }

    private void drawEmptyBoard() {
        overwriteCanvas();
        drawBoardLines();
    }

    private void drawBoardLines() {
        double currentXPos = horizontalPadding + horizontalSpaceBetweenLines;
        double currentYPos = verticalPadding + verticalSpaceBetweenLines;
        gc.setStroke(Color.BLUE);

        for (int i = 0; i < 2; i++) {
            drawLine(currentXPos, verticalPadding, currentXPos, tttCanvas.getHeight() - verticalPadding);
            drawLine(horizontalPadding, currentYPos, tttCanvas.getWidth() - horizontalPadding, currentYPos);
            currentXPos += horizontalSpaceBetweenLines;
            currentYPos += verticalSpaceBetweenLines;
        }
    }

    private void drawLine(final double x1, final double y1, final double x2, final double y2) {
        gc.strokeLine(x1, y1, x2, y2);
    }

    private void drawOval(final double x, final double y, final double w, final double h) {
        gc.strokeOval(x, y, w, h);
    }

    private void drawCross(final ClickArea clickedArea) {
        final double gap = 5;
        final double x1 = clickedArea.getX1() + gap;
        final double x2 = clickedArea.getX2() - gap;
        final double y1 = clickedArea.getY1() + gap;
        final double y2 = clickedArea.getY2() - gap;
        gc.setStroke(Color.RED);
        drawLine(x1, y1, x2, y2);
        drawLine(x2, y1, x1, y2);
    }

    private void drawNought(final ClickArea clickedArea) {
        final double gap = 5;
        final double radius = clickedArea.getX2() - clickedArea.getX1() - 2 * gap;
        gc.setStroke(Color.GREEN);
        drawOval(clickedArea.getX1() + gap, clickedArea.getY1() + gap, radius, radius);
    }
}
