package be.howest.ti.monopoly.logic.implementation;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private int numberOfPlayers;
    private List<Player> players;
    private int availableHouses = 32;
    private int availableHotels = 12;
    private List<Turn> turns;
    private String id;
    private String prefix;
    private boolean ended;
    private String winner;
    private boolean canRoll = true;
    private String currentPlayer;
    private int[] lastRolledDice;
    private List<BaseTile> tiles;


    public Game(int numberOfPlayers, String prefix, String gameNumber) {
        this.numberOfPlayers = numberOfPlayers;
        this.ended = false;
        this.winner = null;

        if (prefix != null) {
            this.id = prefix + "_" + gameNumber;
            this.prefix = prefix;
        } else {
            this.id = gameNumber;
        }

        this.players = new ArrayList<>();
        this.turns = new ArrayList<>();
        this.tiles = createTiles();
    }

    public void addPlayer(String playerName) {
        if (players.size() < numberOfPlayers) {
            Boolean isAlreadyInGame = false;
            for (Player player : players) {
                if (player.getName().equals(playerName)) {
                    isAlreadyInGame = true;
                    break;
                }
            }
            if (!isAlreadyInGame) {
                Player newPlayer = new Player(playerName);
                players.add(newPlayer);
            } else throw new IllegalArgumentException("player is already in game!");
        }
    }


    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public boolean isCanRoll() {
        return canRoll;
    }

    public void setCanRoll(boolean canRoll) {
        this.canRoll = canRoll;
    }

    public boolean isStarted() {
        return players.size() == numberOfPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isEnded() {
        return ended;
    }

    public String getWinner() {
        return winner;
    }

    public int getAvailableHouses() {
        return availableHouses;
    }

    public void incrAvailableHouses() {
        availableHouses++;
    }

    public void decrAvailableHouses() {
        availableHouses--;
    }

    public int getAvailableHotels() {
        return availableHotels;
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public boolean canRoll() {
        return canRoll;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public int[] getLastRolledDice() {
        return lastRolledDice;
    }

    @JsonIgnore
    public List<BaseTile> getTiles() {
        return tiles;
    }

    public void setEnded() {
        this.ended = true;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void addTurn(Turn turn) {
        turns.add(turn);
    }

    public void setLastDiceRoll(int[] rolledDice) {
        this.lastRolledDice = rolledDice;
    }

    public void setCanRoll(Boolean canRoll) {
        this.canRoll = canRoll;
    }

    public void setEnded(Boolean ended) {
        this.ended = ended;
    }

    public void setCurrentPlayer(String playerName) {
        this.currentPlayer = playerName;
    }

    public List<BaseTile> createTiles() {
        return List.of(
                new BaseTile(0, "Spawner", "Go", 0),
                new StreetTile(1, "Wood", 60, 30, 2, "purple", 10, 30, 90, 160, 250, 50, 0, 2),
                new CommunityChestTile(2, "Chest 1"),
                new StreetTile(3, "Wood Planks", 60, 30, 4, "purple", 20, 60, 180, 320, 450, 50, 0, 2),
                new BaseTile(4, "Tax", "Tax Income", 0),
                new StationTile(5, "Station 1", 200, 100, 25, "black"),
                new StreetTile(6, "Coal Ore", 100, 50, 6, "lightblue", 30, 90, 270, 400, 550, 50, 0, 3),
                new ChanceTile(7, "Lucky Block 1"),
                new StreetTile(8, "Coal", 100, 50, 6, "lightblue", 30, 90, 270, 400, 550, 50, 0, 3),
                new StreetTile(9, "Coal Block", 120, 60, 8, "lightblue", 40, 100, 300, 450, 600, 50, 0, 3),
                new BaseTile(10, "End", "Jail", 0),
                new StreetTile(11, "Iron ore", 140, 70, 10, "violet", 50, 150, 450, 625, 750, 100, 0, 3),
                new UtilTile(12, "Food", 150, 75, 4, "white"),
                new StreetTile(13, "Iron", 140, 70, 10, "violet", 50, 150, 450, 625, 750, 100, 0, 3),
                new StreetTile(14, "Iron Block", 160, 80, 12, "violet", 60, 180, 500, 700, 900, 100, 0, 3),
                new StationTile(15, "Station 2", 200, 100, 25, "black"),
                new StreetTile(16, "Redstone Ore", 180, 90, 14, "orange", 70, 200, 550, 750, 950, 100, 0, 3),
                new CommunityChestTile(17, "Chest 2"),
                new StreetTile(18, "Redstone", 180, 90, 14, "orange", 70, 200, 550, 750, 950, 100, 0, 3),
                new StreetTile(19, "Redstone Block", 200, 100, 16, "orange", 80, 220, 600, 800, 1000, 100, 0, 3),
                new BaseTile(20, "Nether", "Free Parking", 0),
                new StreetTile(21, "Gold Ore", 220, 110, 18, "red", 90, 250, 700, 875, 1050, 150, 0, 3),
                new ChanceTile(22, "Lucky Block 2"),
                new StreetTile(23, "Gold", 220, 110, 18, "red", 90, 250, 700, 875, 1050, 150, 0, 3),
                new StreetTile(24, "Gold Block", 240, 120, 20, "red", 100, 300, 750, 925, 1100, 150, 0, 3),
                new StationTile(25, "Station 3", 200, 100, 25, "black"),
                new StreetTile(26, "Emerald Ore", 260, 130, 22, "yellow", 110, 330, 800, 975, 1150, 150, 0, 3),
                new StreetTile(27, "Emerald", 260, 130, 22, "yellow", 110, 330, 800, 975, 1150, 150, 0, 3),
                new UtilTile(28, "Water", 150, 75, 4, "white"),
                new StreetTile(29, "Emerald Block", 280, 140, 24, "yellow", 120, 360, 850, 1025, 1200, 150, 0, 3),
                new BaseTile(30, "End Portal", "Go to Jail", 0),
                new StreetTile(31, "Diamond Ore", 300, 150, 26, "darkgreen", 130, 390, 900, 1100, 1275, 200, 0, 3),
                new StreetTile(32, "Diamond", 300, 150, 26, "darkgreen", 130, 390, 900, 1100, 1275, 200, 0, 3),
                new CommunityChestTile(33, "Chest 3"),
                new StreetTile(34, "Diamond Block", 320, 160, 28, "darkgreen", 150, 450, 1000, 1200, 1400, 200, 0, 3),
                new StationTile(35, "Station 4", 200, 100, 25, "black"),
                new ChanceTile(36, "Lucky Block 3"),
                new StreetTile(37, "Netherite", 350, 175, 35, "darkblue", 175, 500, 1100, 1300, 1500, 200, 0, 2),
                new BaseTile(38, "Tax", "tax_income", 0),
                new StreetTile(39, "Netherite Scrap", 400, 200, 50, "darkblue", 200, 600, 1400, 1700, 2000, 200, 0, 2)
        );
    }


    public BaseTile getTile(int position) {
        for (BaseTile tile : getTiles()) {
            if (tile.getPosition() == position) {
                return tile;
            }
        }
        throw new IllegalArgumentException("Could not find tile with position " + position);
    }


    public BaseTile getTile(String name) {
        for (BaseTile tile : getTiles()) {
            if (tile.getNameAsPathParameter().equalsIgnoreCase(name)) {
                return tile;
            }
        }

        throw new IllegalArgumentException("Could not find tile with name " + name);
    }

    public BaseTile getTileByName(String propertyName) {
        for (BaseTile tile : getTiles()) {
            if (tile.getName().equals(propertyName)) {
                return tile;
            }
        }
        throw new IllegalArgumentException("tile does not exist");
    }
}
