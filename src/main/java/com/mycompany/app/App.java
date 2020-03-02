package com.mycompany.app;

import com.mycompany.app.TakenokoGame.*;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        // Init game with Players
        TakenokoGame game = new TakenokoGame();
        List<Player> players = Arrays.asList(new Player("player1"),new Player("player2"));
        game.init(players);
        System.out.println( "TakenokoGame initiated!" );

        Scanner scanner = new Scanner(System.in);

        int maxNumberOfTurns = 10;
        int currentTurn = 0;
        List<String> playableOptions = Arrays.asList("drawObjective", "drawTile", "gardener", "end");
        Player currentPlayer;
        while (currentTurn < maxNumberOfTurns){
            currentPlayer = players.get((currentTurn) % (players.size()));
            System.out.println("Player " + currentPlayer.getName() + " is playing");
            System.out.println("Choose an action : " + playableOptions);
            String chosenOption = scanner.next();
            while (!playableOptions.contains(chosenOption)) {
                System.out.println(chosenOption + " not found. Please choose any option within -> " + playableOptions.toString());
                chosenOption = scanner.next();
            }
            if (chosenOption.equals(playableOptions.get(0))) {
                currentPlayer.drawObjective(game.getObjectives().get(0));
                System.out.println("Here are your current objectives : " + currentPlayer.getObjectives().toString());
            } else if (chosenOption.equals(playableOptions.get(1))){
                Tile newTile = game.anyTile();
                System.out.println("=== BOARD === \n" + game.printTileBoard("type"));
                System.out.println("=== BOARD [ID] === \n" + game.printTileBoard("id"));
                System.out.println("You drew a tile of type : " + newTile.getType().name());
                System.out.println("Please choose a position to put your tile (enter a number)");
                int chosenTilePosition = scanner.nextInt();
                System.out.println("Please choose a side within : " + Arrays.asList(Side.values()));
                String chosenSide = scanner.next();
                Side choseSide = Side.valueOf(chosenSide);
                Tile target = game.getPlateau().stream().filter(t -> t.getId() == chosenTilePosition ).findFirst().get();
                game.putTileAside(newTile, choseSide, target);
            } else if (chosenOption.equals(playableOptions.get(2))) {
                int chosenGardenerPosition;
                Side choseSide;
                do {
                    System.out.println("Please choose a side within : " + Arrays.asList(Side.values()));
                    String chosenSide = scanner.next();
                    choseSide = Side.valueOf(chosenSide);
                    System.out.println("Please choose a distance to put your tile (enter a number)");
                    chosenGardenerPosition = scanner.nextInt();
                } while(!game.canGardenerMoveTo(choseSide, chosenGardenerPosition));
                game.moveGardenerPiece(choseSide, chosenGardenerPosition);
            }else { // END
                currentTurn = 1000;
            }
            System.out.println("Check if you completed some objectives");
            currentPlayer.completeObjective(game);
            System.out.println("=== BOARD STATE === \n" + game.printTileBoard("type"));
            System.out.println("=== Garderner === \n" + "x : " + game.getGardenerPiece().getCoordX() + ", y : " + game.getGardenerPiece().getCoordY());
            System.out.println("End of turn of player " + currentPlayer.getName());
            currentTurn++;
        }
        System.out.println("End Of Game");
        System.out.println("Score :");
        for(Player p : players){
            System.out.println(p.getName() + ": " + p.getScore());
        }
        System.out.println(game.end());
    }
}
