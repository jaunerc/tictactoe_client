package ch.jaunerc.ttt_client.controller;

public class ClickArea {
    private int areaNumber;
    private double x1, y1, x2, y2;

    public ClickArea(int areaNumber, double x1, double y1, double x2, double y2) {
        this.areaNumber = areaNumber;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean isInside(final double x, final double y) {
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public int getAreaNumber() {
        return areaNumber;
    }
}
