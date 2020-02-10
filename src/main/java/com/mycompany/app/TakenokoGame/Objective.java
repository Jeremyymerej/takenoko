package com.mycompany.app.TakenokoGame;

import com.mycompany.app.takenoko.ObjectiveType;

import java.util.ArrayList;
import java.util.List;

public class Objective {

    private Integer victoryPoints;
    private ObjectiveType objectiveType;
    /**
     *  The representation of a Tile Objective is a Path composed of 1-N tiles
     *  Each Tile has only 1 neighbors excpet the last one, that have no neighbors
     */
    private List<Tile> objectifRepresentation;

    public Objective(ObjectiveType objectiveType, List<Tile> objectifRepresentation, Integer victoryPoint) {
        this.objectifRepresentation = objectifRepresentation;
        this.objectiveType = objectiveType;
        this.victoryPoints = victoryPoint;
    }

    public ObjectiveType getType() {
        return objectiveType;
    }

    public Integer getVictoryPoints() {
        return victoryPoints;
    }

    public boolean conditionFullfilled(List<Tile> boardTiles) {
        boolean conditionFullFilled = false;
        for(Tile boardTile : boardTiles) {
            Tile objectifTile = objectifRepresentation.get(0);
            if(checkObjectivPathPattern(boardTile, objectifTile)){
                conditionFullFilled =  true;
            }
        }
        return conditionFullFilled;
    }

    private boolean checkObjectivPathPattern(Tile boardStart, Tile objectivStart) {
        Tile currentBoardTile = boardStart;
        Tile currentObjectivTile = objectivStart;
        while (currentBoardTile.getType() == currentObjectivTile.getType()) {
            if(currentObjectivTile.getSidesWithNeighbors().isEmpty()){
                return true;
            } else {
                Side nextObjectivSide = currentObjectivTile.getSidesWithNeighbors().get(0);
                currentObjectivTile = currentObjectivTile.getTile(nextObjectivSide);
                currentBoardTile = currentBoardTile.getTile(nextObjectivSide);
            }
        }
        return false;
    }
}
