package com.mycompany.app.TakenokoGame;

public class GardernerPiece {

    TileCoord coord;

    GardernerPiece(TileCoord coord) {
        this.coord = coord;
    }

    public TileCoord getCoord() {
        return coord;
    }

    public void setCoord(TileCoord coord) {
        this.coord = coord;
    }

    public double getCoordX() {
        return coord.getXCoord();
    }

    public double getCoordY() {
        return coord.getYCoord();
    }
}
