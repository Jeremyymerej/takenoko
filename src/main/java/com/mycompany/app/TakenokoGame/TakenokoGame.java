package com.mycompany.app.TakenokoGame;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class TakenokoGame {

    private List<Tile> plateau;
    private List<Player> players;
    private List<Objective> objectives;
    private Player currentPlayer;
    private GardernerPiece gardernerPiece;

    public TakenokoGame() {
        plateau = new ArrayList<>();
        players = new ArrayList<>();
        objectives = new ArrayList<>();
    }

    public String start() {
        this.currentPlayer = this.players.get(0);
        return "Takenoko started";
    }

    public void init(List<Player> players) {
        Tile startTile = new Tile(TileType.START);
        startTile.setCoord(new TileCoord(0, 0));
        plateau.add(startTile);
        gardernerPiece = new GardernerPiece(new TileCoord(0,0));

        this.players = players;
        objectives.add(new Objective(ObjectiveType.TILES, Arrays.asList(new Tile(TileType.START)), 5));

        setEmptyTilesAround(startTile);
        initTileNeighborhood(startTile);
    }

    public void processUserInput() {
        processUserInput(new Scanner(System.in), System.out);
    }

    /** For testing. Package-private if possible. */
    public String processUserInput(Scanner scanner, PrintStream output) {
        List<String> possibleAnswers = Arrays.asList("obj", "tile");
        output.println("Give a choice among " + possibleAnswers.toString());
        String input = scanner.nextLine();

        while (!possibleAnswers.contains(input)) {
            output.println("Wrong choice, try again. Possible Answers" + possibleAnswers.toString());
            input = scanner.nextLine();
        }

        return input;
    }

    public void putTileAside(Tile toPut, Side side, Tile placedTile) {
        Tile oldNeighbour = placedTile.getTile(side);
        if (oldNeighbour.getType() == TileType.EMPTY) {
            setEmptyTilesAround(toPut);
            setTileNeighbors(side, toPut);
            setCoordinate(toPut, side, placedTile);
            transferNeighborhood(oldNeighbour, toPut);
            placedTile.setTile(side, toPut);
            this.plateau.add(toPut);
        } else {
            System.out.println("You are trying to replace an exisitng tile, move not authorized");
        }
    }

    private void setCoordinate(Tile toPut, Side side, Tile placedTile) {
        switch (side) {
            case NORTH_EAST:
                toPut.setCoord(new TileCoord((int) placedTile.getXcoord() + 1, (int) placedTile.getYcoord() + 1));
                break;
            case EAST:
                toPut.setCoord(new TileCoord((int) placedTile.getXcoord() + 1, (int) placedTile.getYcoord()));
                break;
            case SOUTH_EAST:
                toPut.setCoord(new TileCoord((int) placedTile.getXcoord() + 1, (int) placedTile.getYcoord() - 1));
                break;
            case SOUTH_WEST:
                toPut.setCoord(new TileCoord((int) placedTile.getXcoord() - 1, (int) placedTile.getYcoord() - 1));
                break;
            case WEST:
                toPut.setCoord(new TileCoord((int) placedTile.getXcoord() - 1, (int) placedTile.getYcoord()));
                break;
            case NORTH_WEST:
                toPut.setCoord(new TileCoord((int) placedTile.getXcoord() - 1, (int) placedTile.getYcoord() + 1));
                break;
            default:
                break;
        }
    }

    public void transferNeighborhood(Tile from, Tile to) {
        if (from.getNorthEast() != null) to.setNorthEast(from.getNorthEast());
        if (from.getEast() != null) to.setEast(from.getEast());
        if (from.getSouthEast() != null) to.setSouthEast(from.getSouthEast());
        if (from.getSouthWest() != null) to.setSouthWest(from.getSouthWest());
        if (from.getWest() != null) to.setWest(from.getWest());
        if (from.getNorthWest() != null) to.setNorthWest(from.getNorthWest());
    }

    public void setTileNeighbors(Side side, Tile newTile) {
        switch (side) {
            case NORTH_EAST:
                newTile.getNorthEast().setSouthWest(newTile);
                break;
            case EAST:
                newTile.getEast().setWest(newTile);
                break;
            case SOUTH_EAST:
                newTile.getSouthEast().setNorthWest(newTile);
                break;
            case SOUTH_WEST:
                newTile.getSouthWest().setNorthEast(newTile);
                break;
            case WEST:
                newTile.getWest().setEast(newTile);
                break;
            case NORTH_WEST:
                newTile.getNorthWest().setSouthEast(newTile);
                break;
            default:
                break;
        }
    }

    public void initTileNeighborhood(Tile newTile) {
        newTile.getNorthEast().setSouthWest(newTile);
        newTile.getEast().setWest(newTile);
        newTile.getSouthEast().setNorthWest(newTile);
        newTile.getSouthWest().setNorthEast(newTile);
        newTile.getWest().setEast(newTile);
        newTile.getNorthWest().setSouthEast(newTile);

    }

    public void setEmptyTilesAround(Tile putted) {
        if (putted.getNorthEast() == null) putted.setNorthEast(new Tile(TileType.EMPTY));
        if (putted.getEast() == null) putted.setEast(new Tile(TileType.EMPTY));
        if (putted.getSouthEast() == null) putted.setSouthEast(new Tile(TileType.EMPTY));
        if (putted.getSouthWest() == null) putted.setSouthWest(new Tile(TileType.EMPTY));
        if (putted.getWest() == null) putted.setWest(new Tile(TileType.EMPTY));
        if (putted.getNorthWest() == null) putted.setNorthWest(new Tile(TileType.EMPTY));
    }

    public String end() {
        Optional<Player> optionalPlayer = players.stream().max(comparing(Player::getScore));
        Player winner = optionalPlayer.get();
        return "The winner is " + winner.getName() + " with " + winner.getScore() + " points";
    }


    public List<Tile> getPlateau() {
        return plateau;
    }

    public void setPlateau(List<Tile> plateau) {
        this.plateau = plateau;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public List<Tile> sortTilesToBePrinted() {
        return this.plateau.stream().sorted((o1, o2) -> (int) (o1.getXcoord() - o2.getXcoord())).sorted((o1, o2) -> (int) (o2.getYcoord() - o1.getYcoord())).collect(Collectors.toList());
    }

    public String printTileBoard(String tileInformation) {
        StringBuilder sb = new StringBuilder();
        // Print tiles starting from upper-left corner to lower-right corner
        List<Tile> toBePrinted = sortTilesToBePrinted();
        AtomicReference<Double> currentLineY = new AtomicReference<>(toBePrinted.get(0).getYcoord());
        Double lowestXcoord = toBePrinted.stream().min(comparing(Tile::getXcoord)).get().getXcoord();
        sortTilesToBePrinted().forEach(tile -> {
            buildLine(sb, currentLineY, lowestXcoord, tile, tileInformation);
        });
        return sb.toString();
    }

    private void buildLine(StringBuilder sb, AtomicReference<Double> currentLineY, Double lowestXcoord, Tile tile, String tileInformation) {
        String tileInfoToPrint = "";
        switch (tileInformation){
            case "type":
                tileInfoToPrint = tile.getType().toString().charAt(0) + "";
                break;
            case "id":
                tileInfoToPrint = tile.getId().toString();
                break;
        }
        if (currentLineY.get() > tile.getYcoord()) {
            currentLineY.set(tile.getYcoord());
            sb.append("\n");
            // Add tab space for each Indentation of the current tile compared to the lowest X tile
            for(int i = 0; i < tile.getXcoord() - lowestXcoord; i++){
                sb.append("  ");
            }
        }
        sb.append(tileInfoToPrint + " | ");
    }

    public Player getCurrentPlayerToPlay() {
        return this.currentPlayer;
    }

    public Tile anyTile() {
        return new Tile(TileType.GREEN);
    }

    public GardernerPiece getGardenerPiece() {
        return gardernerPiece;
    }

    public Tile getStartTile() {
        return getTileAt(0,0);
    }

    public Tile getTileAt(double x, double y) {
        Optional<Tile> expectedTile = getPlateau().stream().filter(t -> t.getXcoord() == x && t.getYcoord() == y).findFirst();
        return expectedTile.orElse(null);
    }

    public boolean canGardenerMoveTo(Side side, int distance) {
        Tile tileFound = null;
        int modifierX = 0;
        int modifierY = 0;
        switch (side) {
            case NORTH_EAST:
                modifierX = 1;
                modifierY = 1;
                break;
            case EAST:
                modifierX = 1;
                break;
            case SOUTH_EAST:
                modifierX = 1;
                modifierY = -1;
                break;
            case SOUTH_WEST:
                modifierX = -1;
                modifierY = -1;
                break;
            case WEST:
                modifierX = -1;
                break;
            case NORTH_WEST:
                modifierX = -1;
                modifierY = 1;
                break;
            default:
                break;
        }
        tileFound = getTileAt(gardernerPiece.getCoordX() + (modifierX * distance), gardernerPiece.getCoordY() + (modifierY * distance));
        if(tileFound == null) {
            return false;
        } else {
            return true;
        }
    }

    public void moveGardenerPiece(Side side, int distance) {
        int modifierX = 0;
        int modifierY = 0;
        switch (side) {
            case NORTH_EAST:
                modifierX = 1;
                modifierY = 1;
                break;
            case EAST:
                modifierX = 1;
                break;
            case SOUTH_EAST:
                modifierX = 1;
                modifierY = -1;
                break;
            case SOUTH_WEST:
                modifierX = -1;
                modifierY = -1;
                break;
            case WEST:
                modifierX = -1;
                break;
            case NORTH_WEST:
                modifierX = -1;
                modifierY = 1;
                break;
            default:
                break;
        }
        gardernerPiece.setCoord(new TileCoord((int) gardernerPiece.getCoordX() + (modifierX * distance),(int) gardernerPiece.getCoordY() + (modifierY * distance)));
    }

    public TileType getGardernerTileType() {
        return getTileAt(gardernerPiece.getCoordX(), gardernerPiece.getCoordY()).getType();
    }
}
