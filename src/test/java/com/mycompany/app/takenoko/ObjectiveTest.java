package com.mycompany.app.takenoko;

import com.mycompany.app.TakenokoGame.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ObjectiveTest {

    List<Player> players = Arrays.asList(new Player("player1"),new Player("player2"));



    @Test
    public void testCreateObjective() {
        Objective objective = new Objective(ObjectiveType.TILES, Arrays.asList(new Tile(TileType.GREEN)), 5);

        ObjectiveType type = objective.getType();
        Integer victoryPoint = objective.getVictoryPoints();

        ObjectiveType expectedType = ObjectiveType.TILES;
        Integer expectedVictoryPoints = 5;

        assertEquals(expectedType, type);
        assertEquals(expectedVictoryPoints, victoryPoint);
    }

    @Test
    public void testTileObjectiveFullfilled() {
        List<Tile> objectifRepresentation =  new ArrayList<>();
        objectifRepresentation.add(new Tile(TileType.START));
        Objective objective = new Objective(ObjectiveType.TILES, objectifRepresentation ,  2);

        TakenokoGame gameState = new TakenokoGame();
        gameState.init(players);

        boolean completeness = objective.conditionFullfilled(gameState.getPlateau());
        assertEquals(true, completeness);
    }

    @Test
    public void testTileObjectiveNotFullfilled() {
        List<Tile> objectifRepresentation =  new ArrayList<>();
        objectifRepresentation.add(new Tile(TileType.GREEN));
        Objective objective = new Objective(ObjectiveType.TILES, objectifRepresentation ,  2);

        TakenokoGame gameState = new TakenokoGame();
        gameState.init(players);

        boolean completeness = objective.conditionFullfilled(gameState.getPlateau());
        assertEquals(false, completeness);
    }

    @Test
    public void testTileObjective1TileIsMatching() {
        List<Tile> objectifRepresentation = new ArrayList<Tile>();
        Tile firstGreen = new Tile(TileType.GREEN);
        objectifRepresentation.add(firstGreen);
        Objective oneGreenTileObjective = new Objective(ObjectiveType.TILES, objectifRepresentation, 5);

        TakenokoGame gameState = new TakenokoGame();
        gameState.init(players);

        assertFalse(oneGreenTileObjective.conditionFullfilled(gameState.getPlateau()));

        gameState.putTileAside(new Tile(TileType.GREEN), Side.EAST, gameState.getPlateau().get(0));

        assertTrue(oneGreenTileObjective.conditionFullfilled(gameState.getPlateau()));
    }

    @Test
    public void testTileObjective3TilesAreMatching() {
        List<Tile> objectifRepresentation = new ArrayList<Tile>();
        Tile firstGreen = new Tile(TileType.GREEN);
        Tile secondYellow = new Tile(TileType.YELLOW);
        Tile thirdGreen = new Tile(TileType.GREEN);
        firstGreen.setTile(Side.SOUTH_EAST,secondYellow);
        secondYellow.setTile(Side.SOUTH_EAST,thirdGreen);
        objectifRepresentation.add(firstGreen);
        objectifRepresentation.add(secondYellow);
        objectifRepresentation.add(thirdGreen);
        Objective oneGreenTileObjective = new Objective(ObjectiveType.TILES, objectifRepresentation, 5);

        TakenokoGame gameState = new TakenokoGame();
        gameState.init(players);

        assertFalse(oneGreenTileObjective.conditionFullfilled(gameState.getPlateau()));

        Tile test1 = new Tile(TileType.GREEN);
        Tile test2 = new Tile(TileType.YELLOW);
        Tile test3 = new Tile(TileType.GREEN);
        gameState.putTileAside(test1, Side.EAST, gameState.getPlateau().get(0));
        gameState.putTileAside(test2, Side.SOUTH_EAST, test1);
        gameState.putTileAside(test3, Side.SOUTH_EAST, test2);

        assertTrue(oneGreenTileObjective.conditionFullfilled(gameState.getPlateau()));
    }

    @Test
    public void testTileObjective3TilesAreNotMatching() {
        List<Tile> objectifRepresentation = new ArrayList<Tile>();
        Tile firstGreen = new Tile(TileType.GREEN);
        Tile secondYellow = new Tile(TileType.YELLOW);
        Tile thirdGreen = new Tile(TileType.GREEN);
        firstGreen.setTile(Side.SOUTH_EAST,secondYellow);
        secondYellow.setTile(Side.SOUTH_EAST,thirdGreen);
        objectifRepresentation.add(firstGreen);
        objectifRepresentation.add(secondYellow);
        Objective oneGreenTileObjective = new Objective(ObjectiveType.TILES, objectifRepresentation, 5);

        TakenokoGame gameState = new TakenokoGame();
        gameState.init(players);

        assertFalse(oneGreenTileObjective.conditionFullfilled(gameState.getPlateau()));

        gameState.putTileAside(new Tile(TileType.GREEN), Side.EAST, gameState.getPlateau().get(0));
        gameState.putTileAside(new Tile(TileType.YELLOW), Side.EAST, gameState.getPlateau().get(1));
        gameState.putTileAside(new Tile(TileType.GREEN), Side.EAST, gameState.getPlateau().get(2));

        assertFalse(oneGreenTileObjective.conditionFullfilled(gameState.getPlateau()));
    }

}
