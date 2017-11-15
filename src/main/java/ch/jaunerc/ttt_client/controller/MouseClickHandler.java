package ch.jaunerc.ttt_client.controller;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class MouseClickHandler {

    private double boardStartX, boardStartY, boardEndX, boardEndY;
    private List<ClickArea> areas;

    public MouseClickHandler() {
        areas = new ArrayList<>();
    }

    public void initAreas(double horizontalGab, double verticalGap, double lineWidth) {
        double currentX1 = boardStartX;
        double currentY1 = boardStartY;
        double currentX2 = boardStartX + horizontalGab;
        double currentY2 = boardStartY + verticalGap;
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                ClickArea area = new ClickArea(x * 3 + y, currentX1, currentY1, currentX2, currentY2);
                areas.add(area);

                currentX1 += horizontalGab + lineWidth;
                currentX2 += horizontalGab + lineWidth;
            }
            currentY1 += verticalGap + lineWidth;
            currentY2 += verticalGap + lineWidth;
            currentX1 = boardStartX;
            currentX2 = boardStartX + horizontalGab;
        }
    }

    public boolean isInside(final MouseEvent event) {
        return areas.stream().anyMatch(area -> area.isInside(event.getX(), event.getY()));
    }

    public ClickArea getClickedArea(final MouseEvent event) {
        return areas.stream()
                .filter(area -> area.isInside(event.getX(), event.getY()))
                .findFirst()
                .get();
    }

    public void setBoardStartX(double boardStartX) {
        this.boardStartX = boardStartX;
    }

    public void setBoardStartY(double boardStartY) {
        this.boardStartY = boardStartY;
    }

    public void setBoardEndX(double boardEndX) {
        this.boardEndX = boardEndX;
    }

    public void setBoardEndY(double boardEndY) {
        this.boardEndY = boardEndY;
    }
}
