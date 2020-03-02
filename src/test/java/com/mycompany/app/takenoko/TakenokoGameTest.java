package com.mycompany.app.takenoko;

import com.mycompany.app.TakenokoGame.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TakenokoGameTest {

    TakenokoGame game;
    List<Player> players = Arrays.asList(new Player("player1"),new Player("player2"));
    double comparisonDelta = 0.05;

    @Before
    public void setUp() {
        game = new TakenokoGame();
    }

    @Test
    public void testTakenokoGameStarted() {
        game.init(players);
        String res = game.start();
        String expected = "Takenoko started";

        assertEquals(expected, res);
    }

    @Test
    public void testEndGameChoseWinner() {
        game.init(players);
        Player winner = players.get(0);
        winner.drawObjective(game.getObjectives().get(0));
        winner.completeObjective(game);
        String winningString = game.end();

        String expected = "The winner is player1 with 5 points";
        assertEquals(expected, winningString);
    }

    @Test
    public void testFirstTileInBoardIsStartTile() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        assertEquals(TileType.START, startTile.getType());
    }

    @Test
    public void testTakenokoGameInitDefaultValue() {
        long expectedSize = 0;
        assertEquals(game.getPlayers().size(), expectedSize);
        assertEquals(game.getObjectives().size(), expectedSize);
        assertEquals(game.getPlateau().size(), expectedSize);

        List<Player> players = Arrays.asList(new Player("player1"),new Player("player2"));
        game.init(players);

        expectedSize = 1;
        assertEquals(game.getPlayers().size(), 2);
        assertEquals(game.getObjectives().size(), expectedSize);
        assertEquals(game.getPlateau().size(), expectedSize);
    }

    @Test
    public void testPutTileAsideStarterTile() {
        game.init(players);
        assertEquals(game.getPlateau().size(), 1);

        Tile startTile = game.getPlateau().get(0);
        System.out.println("START " + startTile);
        Tile tileToPut = new Tile(TileType.GREEN);
        game.putTileAside(tileToPut, Side.EAST, startTile);
        System.out.println("START " + startTile);

        assertEquals(startTile.getTile(Side.EAST), tileToPut);
        assertEquals(game.getPlateau().size(), 2);
        assertEquals(tileToPut.getWest(), startTile);
    }

    @Test
    public void testSetEmptyTilesAroundAnotherTile() {
        Tile middle = new Tile(TileType.GREEN);
        Tile emptyTile = new Tile(TileType.EMPTY);
        game.setEmptyTilesAround(middle);

        assertTrue(middle.getNorthEast().sameTypeAs(emptyTile));
        assertTrue(middle.getEast().sameTypeAs(emptyTile));
        assertTrue(middle.getSouthEast().sameTypeAs(emptyTile));
        assertTrue(middle.getSouthWest().sameTypeAs(emptyTile));
        assertTrue(middle.getWest().sameTypeAs(emptyTile));
        assertTrue(middle.getNorthWest().sameTypeAs(emptyTile));
    }

    @Test
    public void testPutTileAsideAnyTile() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        Tile emptyTile = new Tile(TileType.EMPTY);
        Tile tester = new Tile(TileType.GREEN);
        game.setEmptyTilesAround(startTile);
        game.initTileNeighborhood(startTile);

        game.putTileAside(tester,Side.WEST,startTile);

        assertEquals(startTile.getWest(), tester);
        assertEquals(startTile.getWest().getEast(), startTile);

        assertEquals(startTile.getNorthEast().getType(), emptyTile.getType());
        assertEquals(startTile.getEast().getType(), emptyTile.getType());
        assertEquals(startTile.getSouthEast().getType(), emptyTile.getType());
        assertEquals(startTile.getSouthWest().getType(), emptyTile.getType());
        assertEquals(startTile.getNorthWest().getType(), emptyTile.getType());
    }

    @Test
    public void testSetTileEmptyNeighbours() {
        Tile middle = new Tile(TileType.GREEN);
        Tile emptyTile = new Tile(TileType.EMPTY);
        game.setEmptyTilesAround(middle);

        assertTrue(middle.getNorthEast().sameTypeAs(emptyTile));
        assertTrue(middle.getEast().sameTypeAs(emptyTile));
        assertTrue(middle.getSouthEast().sameTypeAs(emptyTile));
        assertTrue(middle.getSouthWest().sameTypeAs(emptyTile));
        assertTrue(middle.getWest().sameTypeAs(emptyTile));
        assertTrue(middle.getNorthWest().sameTypeAs(emptyTile));
    }

    @Test
    public void testTransferNeighborhoodToTile() {
        Tile middle = new Tile(TileType.GREEN);
        Tile emptyTile = new Tile(TileType.EMPTY);

        Tile northWest = new Tile(TileType.YELLOW);
        Tile west = new Tile(TileType.YELLOW);
        Tile southWest = new Tile(TileType.YELLOW);
        Tile southEast = new Tile(TileType.YELLOW);
        Tile east = new Tile(TileType.YELLOW);
        Tile northEast = new Tile(TileType.YELLOW);

        game.setEmptyTilesAround(middle);
        assertTrue(middle.getNorthEast().sameTypeAs(emptyTile));
        assertTrue(middle.getEast().sameTypeAs(emptyTile));
        assertTrue(middle.getSouthEast().sameTypeAs(emptyTile));
        assertTrue(middle.getSouthWest().sameTypeAs(emptyTile));
        assertTrue(middle.getWest().sameTypeAs(emptyTile));
        assertTrue(middle.getNorthWest().sameTypeAs(emptyTile));

        middle.setNorthEast(northEast);
        middle.setEast(east);
        middle.setSouthEast(southEast);
        middle.setSouthWest(southWest);
        middle.setWest(west);
        middle.setNorthWest(northWest);

        assertTrue(middle.getNorthEast().sameTypeAs(northEast));
        assertTrue(middle.getEast().sameTypeAs(east));
        assertTrue(middle.getSouthEast().sameTypeAs(southEast));
        assertTrue(middle.getSouthWest().sameTypeAs(southWest));
        assertTrue(middle.getWest().sameTypeAs(west));
        assertTrue(middle.getNorthWest().sameTypeAs(northWest));

        Tile otherMiddle = new Tile(TileType.GREEN);
        game.setEmptyTilesAround(otherMiddle);
        game.transferNeighborhood(middle, otherMiddle);

        assertTrue(otherMiddle.getNorthEast().sameTypeAs(northEast));
        assertTrue(otherMiddle.getEast().sameTypeAs(east));
        assertTrue(otherMiddle.getSouthEast().sameTypeAs(southEast));
        assertTrue(otherMiddle.getSouthWest().sameTypeAs(southWest));
        assertTrue(otherMiddle.getWest().sameTypeAs(west));
        assertTrue(otherMiddle.getNorthWest().sameTypeAs(northWest));
    }

    @Test
    public void testSetTileNeighbour() {
        Tile middle = new Tile(TileType.GREEN);
        Tile emptyTile = new Tile(TileType.EMPTY);
        game.setEmptyTilesAround(middle);
        game.initTileNeighborhood(middle);

        assertTrue(middle.getNorthEast().sameTypeAs(emptyTile));
        assertTrue(middle.getEast().sameTypeAs(emptyTile));
        assertTrue(middle.getSouthEast().sameTypeAs(emptyTile));
        assertTrue(middle.getSouthWest().sameTypeAs(emptyTile));
        assertTrue(middle.getWest().sameTypeAs(emptyTile));
        assertTrue(middle.getNorthWest().sameTypeAs(emptyTile));

        // NE
        Tile midNE = middle.getNorthEast();
        assertEquals(midNE.getType(), emptyTile.getType());
        assertNull(midNE.getNorthWest());
        assertNull(midNE.getWest());
        assertEquals(midNE.getSouthWest(), middle);
        assertNull(midNE.getSouthEast());
        assertNull(midNE.getEast());
        assertNull(midNE.getNorthEast());
    }

    @Test
    public void testPutTileAsideAnotherOneNotReplaceIfAnotherTileHere() {
        game.init(players);
        assertEquals(game.getPlateau().size(), 1);

        Tile startTile = game.getPlateau().get(0);
        Tile tileToPut = new Tile(TileType.GREEN);
        game.putTileAside(tileToPut, Side.EAST, startTile);

        assertEquals(startTile.getTile(Side.EAST), tileToPut);
        assertEquals(game.getPlateau().size(), 2);
        assertEquals(tileToPut.getWest(), startTile);

        Tile secondTileToPut = new Tile(TileType.YELLOW);
        game.putTileAside(secondTileToPut, Side.EAST, startTile);

        assertEquals(startTile.getTile(Side.EAST), tileToPut);
        assertNotEquals(startTile.getTile(Side.EAST), secondTileToPut);
        assertEquals(game.getPlateau().size(), 2);
        assertEquals(tileToPut.getWest(), startTile);
    }

    @Test
    public void testBaseBoardCoordIsStartTile() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        assertEquals(TileType.START, startTile.getType());
        assertEquals(0, startTile.getXcoord(), comparisonDelta);
        assertEquals(0, startTile.getYcoord(), comparisonDelta);
    }

    @Test
    public void testTileCoordWest() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        Tile tileToPut = new Tile(TileType.GREEN);
        game.putTileAside(tileToPut, Side.WEST, startTile);
        assertEquals(-1, tileToPut.getXcoord(), comparisonDelta);
        assertEquals(0, tileToPut.getYcoord(), comparisonDelta);
    }

    @Test
    public void testTileCoordEast() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        Tile tileToPut = new Tile(TileType.GREEN);
        game.putTileAside(tileToPut, Side.EAST, startTile);
        assertEquals(1, tileToPut.getXcoord(), comparisonDelta);
        assertEquals(0, tileToPut.getYcoord(), comparisonDelta);
    }

    @Test
    public void testTileCoordNorthWest() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        Tile tileToPut = new Tile(TileType.GREEN);
        game.putTileAside(tileToPut, Side.NORTH_WEST, startTile);
        assertEquals(-1, tileToPut.getXcoord(), comparisonDelta);
        assertEquals(1, tileToPut.getYcoord(), comparisonDelta);
    }

    @Test
    public void testTileCoordNorthEast() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        Tile tileToPut = new Tile(TileType.GREEN);
        game.putTileAside(tileToPut, Side.NORTH_EAST, startTile);
        assertEquals(1, tileToPut.getXcoord(), comparisonDelta);
        assertEquals(1, tileToPut.getYcoord(), comparisonDelta);
    }

    @Test
    public void testTileCoordSouthWest() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        Tile tileToPut = new Tile(TileType.GREEN);
        game.putTileAside(tileToPut, Side.SOUTH_WEST, startTile);
        assertEquals(-1, tileToPut.getXcoord(), comparisonDelta);
        assertEquals(-1, tileToPut.getYcoord(), comparisonDelta);
    }

    @Test
    public void testTileCoordSouthEast() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        Tile tileToPut = new Tile(TileType.GREEN);
        game.putTileAside(tileToPut, Side.SOUTH_EAST, startTile);
        assertEquals(1, tileToPut.getXcoord(), comparisonDelta);
        assertEquals(-1, tileToPut.getYcoord(), comparisonDelta);

        Tile tileToPut2 = new Tile(TileType.GREEN);
        game.putTileAside(tileToPut2, Side.SOUTH_EAST, tileToPut);
        assertEquals(2, tileToPut2.getXcoord(), comparisonDelta);
        assertEquals(-2, tileToPut2.getYcoord(), comparisonDelta);
    }

    @Test
    public void testPutATileAddItToTheBoard() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);
        assertEquals(1, game.getPlateau().size());

        Tile tileToPut = new Tile(TileType.GREEN);
        game.putTileAside(tileToPut, Side.SOUTH_EAST, startTile);

        assertEquals(2, game.getPlateau().size());
    }

    @Test
    public void testTileSortedFromNorthWestToSouthEast() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        Tile tileToPutNW = new Tile(TileType.GREEN);
        Tile tileToPutSE = new Tile(TileType.YELLOW);
        Tile tileToPutNE = new Tile(TileType.PINK);
        Tile tileToPutSW = new Tile(TileType.EMPTY);

        game.putTileAside(tileToPutSE, Side.SOUTH_EAST, startTile);
        game.putTileAside(tileToPutNW, Side.NORTH_WEST, startTile);
        game.putTileAside(tileToPutNE, Side.NORTH_EAST, startTile);
        game.putTileAside(tileToPutSW, Side.SOUTH_WEST, startTile);

        List<Tile> sortedTiles = game.sortTilesToBePrinted();
        assertEquals(tileToPutNW, sortedTiles.get(0));
        assertEquals(tileToPutNE, sortedTiles.get(1));
        assertEquals(startTile, sortedTiles.get(2));
        assertEquals(tileToPutSW, sortedTiles.get(3));
        assertEquals(tileToPutSE, sortedTiles.get(4));
    }

    @Test
    public void testPrintBoard() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        Tile tileToPutNW = new Tile(TileType.GREEN);
        Tile tileToPutSE = new Tile(TileType.YELLOW);
        Tile tileToPutNE = new Tile(TileType.PINK);
        Tile tileToPutSW = new Tile(TileType.EMPTY);

        game.putTileAside(tileToPutSE, Side.SOUTH_EAST, startTile);
        game.putTileAside(tileToPutNW, Side.NORTH_WEST, startTile);
        game.putTileAside(tileToPutNE, Side.NORTH_EAST, startTile);
        game.putTileAside(tileToPutSW, Side.SOUTH_WEST, startTile);

        String expected = String.join("\n"
                , "G | P | "
                , "  S | "
                , "E | Y | "
        );
        assertEquals(expected, game.printTileBoard("type"));
    }

    @Test
    public void testDoubleNorthWestPrintBoard() {
        game.init(players);
        Tile startTile = game.getPlateau().get(0);

        Tile tileToPutNW = new Tile(TileType.GREEN);
        Tile tileToPutNW2 = new Tile(TileType.GREEN);

        game.putTileAside(tileToPutNW, Side.NORTH_WEST, startTile);
        game.putTileAside(tileToPutNW2, Side.NORTH_WEST, tileToPutNW);

        String expected = String.join("\n"
                , "G | "
                , "  G | "
                , "    S | "
        );
        assertEquals(expected, game.printTileBoard("type"));
    }

    @Test
    public void testFirstPlayerIsTheFirstOneWhenGameStart() {
        game.init(players);
        assertNull(game.getCurrentPlayerToPlay());
        game.start();
        assertEquals(game.getCurrentPlayerToPlay(), players.get(0));
    }

    @Test
    public void testChangePlayersTurnOnceItsTurnIsFinished() {
        game.init(players);
        assertNull(game.getCurrentPlayerToPlay());
        game.start();
        assertEquals(game.getCurrentPlayerToPlay(), players.get(0));
    }

    @Test
    public void testGameInitWithGardenerOnStartTile() {
        assertNull(game.getGardenerPiece());
        game.init(players);
        assertNotNull(game.getGardenerPiece());
        GardernerPiece gardernerPiece = game.getGardenerPiece();
        assertEquals(TileType.START, game.getGardernerTileType());
        assertEquals(0, gardernerPiece.getCoordX(), comparisonDelta);
        assertEquals(0, gardernerPiece.getCoordY(), comparisonDelta);
    }

    @Test
    public void testGameGetStartTile(){
        game.init(players);
        Tile expectedStartTile = game.getStartTile();
        assertEquals(TileType.START, expectedStartTile.getType());
        assertEquals(0, expectedStartTile.getXcoord(), comparisonDelta);
        assertEquals(0, expectedStartTile.getYcoord(), comparisonDelta);
    }

    @Test
    public void testGameGetTileAtSpecificCoord(){
        game.init(players);
        Tile expectedStartTile = game.getStartTile();
        Tile anyTile = game.anyTile();
        assertEquals(expectedStartTile, game.getTileAt(0,0));
        assertEquals(null, game.getTileAt(1,0));
        game.putTileAside(anyTile, Side.EAST, expectedStartTile);
        assertEquals(anyTile, game.getTileAt(1,0));
    }

    @Test
    public void testTestMoveGardenerStraightLineIsPossible() {
        game.init(players);
        Tile tileToPutEast = new Tile(TileType.GREEN);
        Tile tiletoPutEast2 = new Tile(TileType.GREEN);

        game.putTileAside(tileToPutEast, Side.EAST, game.getStartTile());
        game.putTileAside(tiletoPutEast2, Side.EAST, tileToPutEast);

        assertTrue(game.canGardenerMoveTo(Side.EAST, 2));
        assertFalse(game.canGardenerMoveTo(Side.SOUTH_EAST, 2));
        GardernerPiece gardernerPiece = game.getGardenerPiece();
        assertEquals(TileType.START, game.getGardernerTileType());
        assertEquals(0, gardernerPiece.getCoordX(), comparisonDelta);
        assertEquals(0, gardernerPiece.getCoordY(), comparisonDelta);
    }

    @Test
    public void testMoveGardenerStraightLineWithXMove() {
        game.init(players);
        GardernerPiece gardernerPiece = game.getGardenerPiece();

        Tile tileToPutEast = new Tile(TileType.GREEN);
        Tile tiletoPutEast2 = new Tile(TileType.GREEN);

        game.putTileAside(tileToPutEast, Side.EAST, game.getStartTile());
        game.putTileAside(tiletoPutEast2, Side.EAST, tileToPutEast);
        assertEquals(TileType.START, game.getGardernerTileType());
        assertEquals(0, gardernerPiece.getCoordX(), comparisonDelta);
        assertEquals(0, gardernerPiece.getCoordY(), comparisonDelta);
        game.moveGardenerPiece(Side.EAST,2);
        assertEquals(TileType.GREEN, game.getGardernerTileType());
        assertEquals(2, gardernerPiece.getCoordX(), comparisonDelta);
        assertEquals(0, gardernerPiece.getCoordY(), comparisonDelta);

    }
}
