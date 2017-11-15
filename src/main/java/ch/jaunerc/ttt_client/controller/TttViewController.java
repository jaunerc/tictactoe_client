package ch.jaunerc.ttt_client.controller;

import ch.jaunerc.ttt_client.tictactoe.Board;
import ch.jaunerc.ttt_client.tictactoe.FieldValue;
import ch.jaunerc.ttt_client.tictactoe.Game;
import ch.jaunerc.ttt_client.tictactoe.Player;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class TttViewController {

    @FXML
    private Canvas tttCanvas;

    private Game game;
    private GraphicsContext gc;
    private MouseClickHandler mouseClickHandler;

    public TttViewController() {

    }

    @FXML
    private void initialize() {
        initGame();
        initCanvas();
    }

    private void initGame() {
        final Board board = new Board();
        final Player playerOne = new Player(FieldValue.CROSS);
        final Player playerTwo = new Player(FieldValue.NOUGHT);
        game = new Game(board, playerOne, playerTwo);
    }

    private void initCanvas() {
        final double canvasWidth = tttCanvas.getWidth();
        final double canvasHeight = tttCanvas.getHeight();
        final double verticalPadding = 0.1 * canvasHeight;
        final double verticalLineLength = canvasHeight - 2 * verticalPadding;
        final double horizontalPadding = 0.1 * canvasWidth;
        final double horizontalLineLength = canvasWidth - 2 * horizontalPadding;
        final double verticalSpaceBetweenLines = verticalLineLength / 3;
        final double horizontalSpaceBetweenLines = horizontalLineLength / 3;

        gc = tttCanvas.getGraphicsContext2D();
        drawEmptyBoard(horizontalPadding, horizontalSpaceBetweenLines, verticalPadding, verticalSpaceBetweenLines);

        mouseClickHandler = new MouseClickHandler();
        mouseClickHandler.setBoardStartX(horizontalPadding);
        mouseClickHandler.setBoardStartY(verticalPadding);
        mouseClickHandler.setBoardEndX(canvasWidth - verticalPadding);
        mouseClickHandler.setBoardEndY(canvasHeight - horizontalPadding);
        mouseClickHandler.initAreas(horizontalSpaceBetweenLines, verticalSpaceBetweenLines, gc.getLineWidth());

        tttCanvas.setOnMouseClicked(e -> handleMouseClick(e));
    }

    private void handleMouseClick(final MouseEvent event) {
        final ClickArea clickArea = mouseClickHandler.getClickedArea(event);
        switch (game.getCurrentFieldValue()) {
            case CROSS:
                drawCross(clickArea);
                break;
            case NOUGHT:
                drawNought(clickArea);
        }
        game.move(clickArea.getAreaNumber());
    }

    private void overwriteCanvas() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, tttCanvas.getWidth(), tttCanvas.getHeight());
    }

    private void drawEmptyBoard(double horizontalPadding, double horizontalSpaceBetweenLines,
                                double verticalPadding, double verticalSpaceBetweenLines) {
        overwriteCanvas();
        drawBoardLines(horizontalPadding, horizontalSpaceBetweenLines, verticalPadding, verticalSpaceBetweenLines);
    }

    private void drawBoardLines(double horizontalPadding, double horizontalSpaceBetweenLines,
                                double verticalPadding, double verticalSpaceBetweenLines) {
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
