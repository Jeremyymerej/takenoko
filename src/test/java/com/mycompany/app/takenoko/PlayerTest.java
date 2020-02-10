package com.mycompany.app.takenoko;

import com.mycompany.app.TakenokoGame.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {

    List<Player> players = Arrays.asList(new Player("player1"),new Player("player2"));

    @Test
    public void testPlayerIsCreatedWithNameAndScore() {
        Player player = new Player( "name1");

        String name = player.getName();
        Integer playerScore = player.getScore();

        String expectedName = "name1";
        Integer expectedPlayerScore = 0;

        assertEquals(expectedName, name);
        assertEquals(expectedPlayerScore, playerScore);
    }

    @Test
    public void testPlayerCanHaveMultipleObjectives() {
        Player player = new Player( "name1");
        assertEquals(Integer.valueOf(0), player.getObjectivesSize());

        Objective objective = new Objective(ObjectiveType.TILES, Arrays.asList(new Tile(TileType.GREEN)), 5);
        player.drawObjective(objective);
        assertEquals(Integer.valueOf(1), player.getObjectivesSize());

        Objective objective2 = new Objective(ObjectiveType.TILES, Arrays.asList(new Tile(TileType.GREEN)), 4);
        player.drawObjective(objective2);
        assertEquals(Integer.valueOf(2), player.getObjectivesSize());
    }

    @Test
    public void testPlayerCompleteObjectiveEarnPoints() {
        Player player = new Player( "name1");
        Objective objective = new Objective(ObjectiveType.TILES, Arrays.asList(new Tile(TileType.START)), 5);
        TakenokoGame game = new TakenokoGame();

        game.init(players);
        player.drawObjective(objective);

        Integer expectedScore = 0;
        assertEquals(expectedScore, player.getScore());

        player.completeObjective(game);

        expectedScore = 5;
        assertEquals(expectedScore, player.getScore());
    }

    @Test
    public void testPlayerCompleteObjectiveAndEarnCompletedObjectives() {
        Player player = new Player( "name1");
        Objective objective = new Objective(ObjectiveType.TILES, Arrays.asList(new Tile(TileType.START)), 5);
        TakenokoGame game = new TakenokoGame();

        game.init(players);
        player.drawObjective(objective);
        player.completeObjective(game);

        assertEquals(Integer.valueOf(1), player.getCompletedObjectivesSize());
        assertEquals(Integer.valueOf(0), player.getObjectivesSize());
        assertFalse(player.getObjectives().contains(objective));
        assertTrue(player.getCompletedObjectives().contains(objective));
    }

    @Test
    public void testArithMoinsMoins() {
        assertEquals(2, 1 - (-1));
    }

}
