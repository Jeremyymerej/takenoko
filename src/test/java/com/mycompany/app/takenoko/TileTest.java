package com.mycompany.app.takenoko;

import com.mycompany.app.TakenokoGame.Side;
import com.mycompany.app.TakenokoGame.Tile;
import com.mycompany.app.TakenokoGame.TileType;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TileTest {

    @Before
    public void setUp(){
        Tile.resetCounter();
    }

    @Test
    public void testCreateTileWithType() {
        Tile tile = new Tile(TileType.GREEN);

        TileType type = tile.getType();
        TileType expected = TileType.GREEN;
        assertEquals(expected, type);
    }

    @Test
    public void testEachTileHaveUniqueId() {
        Tile tile = new Tile(TileType.GREEN);
        Tile tile2 = new Tile(TileType.GREEN);
        assertEquals(0, tile.getId().intValue());
        assertEquals(1, tile2.getId().intValue());
    }

    @Test
    public void testTwoDifferentTilesOfSameTypeAreDifferent() {
        Tile tile = new Tile(TileType.GREEN);
        Tile tile2 = new Tile(TileType.GREEN);
        assertFalse(tile.equals(tile2));
        assertTrue(tile.equals(tile));
    }

    @Test
    public void testGetSidesWithNeighbors() {
        Tile tile = new Tile(TileType.GREEN);

        tile.setTile(Side.EAST, new Tile(TileType.GREEN));
        tile.setTile(Side.NORTH_WEST, new Tile(TileType.START));
        tile.setTile(Side.WEST, new Tile(TileType.YELLOW));
        tile.setTile(Side.NORTH_EAST, new Tile(TileType.EMPTY));

        List<Side> expected = Arrays.asList(Side.EAST, Side.WEST, Side.NORTH_WEST);
        assertEquals(expected, tile.getSidesWithNeighbors());
    }
}
