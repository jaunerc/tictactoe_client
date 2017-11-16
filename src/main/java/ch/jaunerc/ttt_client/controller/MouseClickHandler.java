package ch.jaunerc.ttt_client.controller;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a handler for mouse click events in the tic tac toe canvas.
 */
public class MouseClickHandler {

    private double boardStartX, boardStartY;
    private List<ClickArea> areas;

    public MouseClickHandler() {
        areas = new ArrayList<>();
    }

    /**
     * Initializes nine areas where the players can click on it.
     *
     * @param horizontalGap The horizontal space between two areas.
     * @param verticalGap   The vertical space between two areas.
     * @param lineWidth     The size of a single line.
     */
    public void initAreas(double horizontalGap, double verticalGap, double lineWidth) {
        double currentX1 = boardStartX;
        double currentY1 = boardStartY;
        double currentX2 = boardStartX + horizontalGap;
        double currentY2 = boardStartY + verticalGap;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                ClickArea area = new ClickArea(x * 3 + y, currentX1, currentY1, currentX2, currentY2);
                areas.add(area);

                currentX1 += horizontalGap + lineWidth;
                currentX2 += horizontalGap + lineWidth;
            }
            currentY1 += verticalGap + lineWidth;
            currentY2 += verticalGap + lineWidth;
            currentX1 = boardStartX;
            currentX2 = boardStartX + horizontalGap;
        }
    }

    public ClickArea getClickedArea(final MouseEvent event) {
        return areas.stream()
                .filter(area -> area.isInside(event.getX(), event.getY()))
                .findFirst()
                .get();
    }

    public ClickArea getAreaByIndex(final int i) {
        return areas.get(i);
    }

    public void setBoardStartX(double boardStartX) {
        this.boardStartX = boardStartX;
    }

    public void setBoardStartY(double boardStartY) {
        this.boardStartY = boardStartY;
    }
}
