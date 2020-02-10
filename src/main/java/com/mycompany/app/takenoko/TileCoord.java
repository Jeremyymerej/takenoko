package com.mycompany.app.takenoko;

import java.awt.*;

public class TileCoord {

    private Point coord;

    public TileCoord(int x, int y) {
        this.coord = new Point(x, y);
    }

    public Point getCoord() {
        return coord;
    }

    public double getXCoord(){
        return coord.getX();
    }

    public double getYCoord(){
        return coord.getY();
    }
}
