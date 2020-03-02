package com.mycompany.app.TakenokoGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tile {

    private static Integer tileCounter = 0;
    private Integer id;
    private TileType type;

    private Tile northEast;
    private Tile east;
    private Tile southEast;
    private Tile southWest;
    private Tile west;
    private Tile northWest;
    private TileCoord coord;

    public Tile(TileType type) {
        this.id = tileCounter++;
        this.type = type;
    }

    public Tile getTile(Side side) {
        switch (side) {
            case NORTH_EAST:
                return northEast;
            case EAST:
                return east;
            case SOUTH_EAST:
                return southEast;
            case SOUTH_WEST:
                return southWest;
            case WEST:
                return west;
            case NORTH_WEST:
                return northWest;
            default:
                return null;
        }
    }

    public void setTile(Side side, Tile tile) {
        switch (side) {
            case NORTH_EAST:
                northEast = tile;
                break;
            case EAST:
                east = tile;
                break;
            case SOUTH_EAST:
                southEast = tile;
                break;
            case SOUTH_WEST:
                southWest = tile;
                break;
            case WEST:
                west = tile;
                break;
            case NORTH_WEST:
                northWest = tile;
                break;
        }
    }

    public boolean sameTypeAs(Tile other){
        return this.type == other.getType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return tile.getId() == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    public TileType getType() {
        return type;
    }

    public Tile getNorthEast() {
        return northEast;
    }

    public void setNorthEast(Tile northEast) {
        this.northEast = northEast;
    }

    public Tile getEast() {
        return east;
    }

    public void setEast(Tile east) {
        this.east = east;
    }

    public Tile getSouthEast() {
        return southEast;
    }

    public void setSouthEast(Tile southEast) {
        this.southEast = southEast;
    }

    public Tile getSouthWest() {
        return southWest;
    }

    public void setSouthWest(Tile southWest) {
        this.southWest = southWest;
    }

    public Tile getWest() {
        return west;
    }

    public void setWest(Tile west) {
        this.west = west;
    }

    public Tile getNorthWest() {
        return northWest;
    }

    public void setNorthWest(Tile northWest) {
        this.northWest = northWest;
    }

    @Override
    public String toString() {
        if (getNorthEast() == null) setNorthEast(new Tile(TileType.EMPTY));
        if (getEast() == null) setEast(new Tile(TileType.EMPTY));
        if (getSouthEast() == null) setSouthEast(new Tile(TileType.EMPTY));
        if (getSouthWest() == null) setSouthWest(new Tile(TileType.EMPTY));
        if (getWest() == null) setWest(new Tile(TileType.EMPTY));
        if (getNorthWest() == null) setNorthWest(new Tile(TileType.EMPTY));
        return "Tile{" + '\n' +
                northWest.type + "^" + northEast.type + '\n' +
                west.type + "|" + type +  "|" + east.type + '\n' +
                southWest.type + "v" + southEast.type + '\n' +
                '}';
    }

    public String toPrint() {
        return " / \\ \n" + "| " + this.type.toString().substring(0,1) + " |\n" + " \\ /";
    }

    public TileCoord getCoords() {
        return coord;
    }

    public void setCoord(TileCoord coord) {
        this.coord = coord;
    }

    public double getYcoord() {
        return this.coord.getYCoord();
    }

    public double getXcoord() {
        return this.coord.getXCoord();
    }

    public List<Side> getSidesWithNeighbors() {
        List<Side> sidesWithNeighbors = new ArrayList<>();
        for(Side s : Side.values()) {
            Tile neighbor = getTile(s);
            if (neighbor != null && neighbor.getType() != TileType.EMPTY){
                sidesWithNeighbors.add(s);
            }
        }
        return sidesWithNeighbors;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static void resetCounter(){
        tileCounter = 0;
    }
}
