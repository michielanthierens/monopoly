package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.web.Request;
import be.howest.ti.monopoly.web.exceptions.InvalidRequestException;
import io.vertx.core.json.JsonObject;

import java.util.*;
import java.util.ArrayList;
import java.util.List;


public class MonopolyService extends ServiceAdapter {
    static List<Game> allGames = new ArrayList<>();

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public List<BaseTile> getTiles() {
        return List.of(
                new BaseTile(0, "Spawner", "Go", 0),
                new StreetTile(1, "Wood", 60, 30, 2, "purple", 10, 30, 90, 160, 250, 50, 0, 2),
                new CommunityChestTile(2, "Chest 1"),
                new StreetTile(3, "Wood Planks", 60, 30, 4, "purple", 20, 60, 180, 320, 450, 50, 0, 2),
                new BaseTile(4, "Tax 1", "Tax Income", 0),
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
                new BaseTile(38, "Tax 2", "tax_income", 0),
                new StreetTile(39, "Netherite Scrap", 400, 200, 50, "darkblue", 200, 600, 1400, 1700, 2000, 200, 0, 2)
        );
    }

    @Override
    public List<String> getChance() {
        return ChanceTile.getChance();
    }

    @Override
    public List<String> getCommunityChest() {
        return CommunityChestTile.getCommunityChest();
    }

    @Override
    public BaseTile getTile(int position) {
        for (BaseTile tile : getTiles()) {
            if (tile.getPosition() == position) {
                return tile;
            }
        }
        throw new IllegalArgumentException("Could not find tile with position " + position);
    }

    @Override
    public BaseTile getTile(String name) {
        for (BaseTile tile : getTiles()) {
            if (tile.getNameAsPathParameter().equalsIgnoreCase(name)) {
                return tile;
            }
        }

        throw new IllegalArgumentException("Could not find tile with name " + name);
    }

    private BaseTile getTileByName(String propertyName) {
        for (BaseTile tile : getTiles()) {
            if (tile.getName().equals(propertyName)) {
                return tile;
            }
        }
        throw new IllegalArgumentException("tile does not exist");
    }

    @Override
    public Game createGame(int numberOfPlayers, String prefix) {
        String gameNumber = "";

        int listSize = allGames.size();

        if (listSize < 10)
            gameNumber = "00" + listSize;
        else if (listSize < 100)
            gameNumber = "0" + listSize;
        else
            gameNumber += listSize;

        Game game = new Game(numberOfPlayers, prefix, gameNumber);
        allGames.add(game);

        return game;
    }

    @Override
    public List<Game> getGames(Request request) {
        List<Game> matchingGames = new ArrayList<>();

        if (request.isPrefixNull() && request.isNumberOfPlayersNull() && request.isStartedNull())
            return allGames;

        else {
            prefixAndNumberOfPlayersAreNull(request, matchingGames);
            prefixAndStartedAreNull(request, matchingGames);
            onlyPrefixIsNull(request, matchingGames);

            numberOfPlayersAndStartedAreNull(request, matchingGames);
            onlyNumberOfPlayersIsNull(request, matchingGames);

            onlyStartedIsNull(request, matchingGames);

            allParametersAreGiven(request, matchingGames);
        }

        return matchingGames;
    }

    private void prefixAndNumberOfPlayersAreNull(Request request, List<Game> matchingGames) {
        if (request.isPrefixNull() && request.isNumberOfPlayersNull() && !request.isStartedNull())
            for (Game game : allGames)
                if (game.isStarted() == request.getStartedFromQuery())
                    matchingGames.add(game);
    }

    private void prefixAndStartedAreNull(Request request, List<Game> matchingGames) {
        if (request.isPrefixNull() && request.isStartedNull() && !request.isNumberOfPlayersNull())
            for (Game game : allGames)
                if (game.getNumberOfPlayers() == request.getNumberOfPlayersFromQuery())
                    matchingGames.add(game);
    }

    private void onlyPrefixIsNull(Request request, List<Game> matchingGames) {
        if (request.isPrefixNull() && !request.isNumberOfPlayersNull() && !request.isStartedNull())
            for (Game game : allGames)
                if (game.getNumberOfPlayers() == request.getNumberOfPlayersFromQuery() &&
                        game.isStarted() == request.getStartedFromQuery())
                    matchingGames.add(game);
    }

    private void numberOfPlayersAndStartedAreNull(Request request, List<Game> matchingGames) {
        if (request.isNumberOfPlayersNull() && request.isStartedNull() && !request.isPrefixNull())
            for (Game game : allGames)
                if (game.getPrefix().equals(request.getPrefixFromQuery()))
                    matchingGames.add(game);
    }

    private void onlyNumberOfPlayersIsNull(Request request, List<Game> matchingGames) {
        if (request.isNumberOfPlayersNull() && !request.isPrefixNull() && !request.isStartedNull())
            for (Game game : allGames)
                if (game.getPrefix().equals(request.getPrefixFromQuery()) &&
                        game.isStarted() == request.getStartedFromQuery())
                    matchingGames.add(game);
    }

    private void onlyStartedIsNull(Request request, List<Game> matchingGames) {
        if (request.isStartedNull() && !request.isPrefixNull() && !request.isNumberOfPlayersNull())
            for (Game game : allGames)
                if (game.getPrefix().equals(request.getPrefixFromQuery()) &&
                        game.getNumberOfPlayers() == request.getNumberOfPlayersFromQuery())
                    matchingGames.add(game);
    }

    private void allParametersAreGiven(Request request, List<Game> matchingGames) {
        if (!request.isPrefixNull() && !request.isNumberOfPlayersNull() && !request.isStartedNull())
            for (Game game : allGames)
                if (game.getPrefix().equals(request.getPrefixFromQuery()) &&
                        game.getNumberOfPlayers() == request.getNumberOfPlayersFromQuery() &&
                        game.isStarted() == request.getStartedFromQuery())
                    matchingGames.add(game);
    }

    @Override
    public Object getDummyGame() {
        return new JsonObject("{\r\n  \"numberOfPlayers\": 5,\r\n  \"players\": [\r\n    {\r\n      \"name\": \"Bob\",\r\n      \"currentTile\": \"Pennsylvania RR\",\r\n      \"jailed\": false,\r\n      \"money\": 830,\r\n      \"bankrupt\": false,\r\n      \"getOutOfJailFreeCards\": 0,\r\n      \"taxSystem\": \"ESTIMATE\",\r\n      \"properties\": [\r\n        {\r\n          \"property\": \"Oriental\",\r\n          \"mortgage\": false,\r\n          \"houseCount\": 0,\r\n          \"hotelCount\": 0\r\n        },\r\n        {\r\n          \"property\": \"Water Works\",\r\n          \"mortgage\": false,\r\n          \"houseCount\": 0,\r\n          \"hotelCount\": 0\r\n        },\r\n        {\r\n          \"property\": \"Pennsylvania\",\r\n          \"mortgage\": false,\r\n          \"houseCount\": 0,\r\n          \"hotelCount\": 0\r\n        },\r\n        {\r\n          \"property\": \"Pennsylvania RR\",\r\n          \"mortgage\": false,\r\n          \"houseCount\": 0,\r\n          \"hotelCount\": 0\r\n        }\r\n      ],\r\n      \"debt\": 0\r\n    },\r\n    {\r\n      \"name\": \"Eve\",\r\n      \"currentTile\": \"Free Parking\",\r\n      \"jailed\": false,\r\n      \"money\": 1390,\r\n      \"bankrupt\": false,\r\n      \"getOutOfJailFreeCards\": 0,\r\n      \"taxSystem\": \"ESTIMATE\",\r\n      \"properties\": [\r\n        {\r\n          \"property\": \"Connecticut\",\r\n          \"mortgage\": false,\r\n          \"houseCount\": 0,\r\n          \"hotelCount\": 0\r\n        }\r\n      ],\r\n      \"debt\": 0\r\n    },\r\n    {\r\n      \"name\": \"David\",\r\n      \"currentTile\": \"Reading RR\",\r\n      \"jailed\": false,\r\n      \"money\": 1300,\r\n      \"bankrupt\": false,\r\n      \"getOutOfJailFreeCards\": 0,\r\n      \"taxSystem\": \"ESTIMATE\",\r\n      \"properties\": [\r\n        {\r\n          \"property\": \"Reading RR\",\r\n          \"mortgage\": false,\r\n          \"houseCount\": 0,\r\n          \"hotelCount\": 0\r\n        }\r\n      ],\r\n      \"debt\": 0\r\n    },\r\n    {\r\n      \"name\": \"Alice\",\r\n      \"currentTile\": \"Boardwalk\",\r\n      \"jailed\": false,\r\n      \"money\": 1050,\r\n      \"bankrupt\": false,\r\n      \"getOutOfJailFreeCards\": 0,\r\n      \"taxSystem\": \"ESTIMATE\",\r\n      \"properties\": [\r\n        {\r\n          \"property\": \"Illinois Avenue\",\r\n          \"mortgage\": false,\r\n          \"houseCount\": 1,\r\n          \"hotelCount\": 0\r\n        },\r\n        {\r\n          \"property\": \"Kentucky Avenue\",\r\n          \"mortgage\": false,\r\n          \"houseCount\": 0,\r\n          \"hotelCount\": 0\r\n        },\r\n        {\r\n          \"property\": \"Indiana Avenue\",\r\n          \"mortgage\": false,\r\n          \"houseCount\": 0,\r\n          \"hotelCount\": 0\r\n        },\r\n        {\r\n          \"property\": \"Boardwalk\",\r\n          \"mortgage\": false,\r\n          \"houseCount\": 0,\r\n          \"hotelCount\": 0\r\n        }\r\n      ],\r\n      \"debt\": 0\r\n    },\r\n    {\r\n      \"name\": \"Carol\",\r\n      \"currentTile\": \"Jail\",\r\n      \"jailed\": false,\r\n      \"money\": 200,\r\n      \"bankrupt\": false,\r\n      \"getOutOfJailFreeCards\": 0,\r\n      \"taxSystem\": \"ESTIMATE\",\r\n      \"properties\": [],\r\n      \"debt\": -1600\r\n    }\r\n  ],\r\n  \"started\": true,\r\n  \"directSale\": null,\r\n  \"availableHouses\": 31,\r\n  \"availableHotels\": 12,\r\n  \"turns\": [\r\n    {\r\n      \"roll\": [\r\n        1,\r\n        3\r\n      ],\r\n      \"player\": \"Bob\",\r\n      \"type\": \"DEFAULT\",\r\n      \"moves\": [\r\n        {\r\n          \"tile\": \"Water Works\",\r\n          \"description\": \"can buy this property in direct sale\"\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"roll\": [\r\n        1,\r\n        1\r\n      ],\r\n      \"player\": \"Eve\",\r\n      \"type\": \"DEFAULT\",\r\n      \"moves\": [\r\n        {\r\n          \"tile\": \"Community Chest I\",\r\n          \"description\": \"You have won second prize in a beauty contest. Collect $10\"\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"roll\": [\r\n        1,\r\n        6\r\n      ],\r\n      \"player\": \"Eve\",\r\n      \"type\": \"DEFAULT\",\r\n      \"moves\": [\r\n        {\r\n          \"tile\": \"Connecticut\",\r\n          \"description\": \"can buy this property in direct sale\"\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"roll\": [\r\n        3,\r\n        2\r\n      ],\r\n      \"player\": \"David\",\r\n      \"type\": \"DEFAULT\",\r\n      \"moves\": [\r\n        {\r\n          \"tile\": \"Reading RR\",\r\n          \"description\": \"can buy this property in direct sale\"\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"roll\": [\r\n        2,\r\n        5\r\n      ],\r\n      \"player\": \"Alice\",\r\n      \"type\": \"DEFAULT\",\r\n      \"moves\": [\r\n        {\r\n          \"tile\": \"Chance I\",\r\n          \"description\": \"Advance to Boardwalk\"\r\n        },\r\n        {\r\n          \"tile\": \"Boardwalk\",\r\n          \"description\": \"can buy this property in direct sale\"\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"roll\": [\r\n        6,\r\n        4\r\n      ],\r\n      \"player\": \"Carol\",\r\n      \"type\": \"DEFAULT\",\r\n      \"moves\": [\r\n        {\r\n          \"tile\": \"Jail\",\r\n          \"description\": \"is just visiting\"\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"roll\": [\r\n        3,\r\n        3\r\n      ],\r\n      \"player\": \"Bob\",\r\n      \"type\": \"DEFAULT\",\r\n      \"moves\": [\r\n        {\r\n          \"tile\": \"Pennsylvania\",\r\n          \"description\": \"can buy this property in direct sale\"\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"roll\": [\r\n        6,\r\n        6\r\n      ],\r\n      \"player\": \"Bob\",\r\n      \"type\": \"DEFAULT\",\r\n      \"moves\": [\r\n        {\r\n          \"tile\": \"Go\",\r\n          \"description\": \"passes 'GO!' and receives 200 for it\"\r\n        },\r\n        {\r\n          \"tile\": \"Oriental\",\r\n          \"description\": \"can buy this property in direct sale\"\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"roll\": [\r\n        3,\r\n        6\r\n      ],\r\n      \"player\": \"Bob\",\r\n      \"type\": \"DEFAULT\",\r\n      \"moves\": [\r\n        {\r\n          \"tile\": \"Pennsylvania RR\",\r\n          \"description\": \"can buy this property in direct sale\"\r\n        }\r\n      ]\r\n    },\r\n    {\r\n      \"roll\": [\r\n        6,\r\n        5\r\n      ],\r\n      \"player\": \"Eve\",\r\n      \"type\": \"DEFAULT\",\r\n      \"moves\": [\r\n        {\r\n          \"tile\": \"Free Parking\",\r\n          \"description\": \"does nothing special\"\r\n        }\r\n      ]\r\n    }\r\n  ],\r\n  \"lastDiceRoll\": [\r\n    6,\r\n    5\r\n  ],\r\n  \"canRoll\": true,\r\n  \"ended\": false,\r\n  \"currentPlayer\": \"David\",\r\n  \"winner\": null\r\n}");
    }

    @Override
    public Game getGame(String gameId) {
        for (Game game : allGames)
            if (game.getId().equals(gameId))
                return game;

        throw new IllegalArgumentException("Could not find game with ID " + gameId);
    }

    @Override
    public List<Game> clearGameList() {
        allGames.clear();

        return allGames;
    }

    @Override
    public Game rollDice(String gameId, String playerName) {
        Game game = getGame(gameId);
        if (!game.canRoll()) {
            throw new IllegalStateException("player can not roll!");
        }
        Turn turn = new Turn(playerName);
        Player player = getPlayer(gameId, playerName);

        // if turn.getroll[0] = [1] => player.canRoll niet veranderen
        game.setCanRoll(false);

        int[] rolledDice = turn.getRoll();

        int currentPosition = game.getTileByName(player.getCurrentTile()).getPosition();
        int newPosition = turn.calculateNewTilePosition(currentPosition, player);

        if (newPosition == 30) {
            player.setJailed(true);
        } else if (newPosition == 4 || newPosition == 38) {
            String system = player.getTaxSystem();
            if (system.equals("COMPUTE")) {
                player.withdrawMoney((10 / player.calculateTotalNetWorth()) * 100);
            } else {
                player.withdrawMoney(200);
            }
        }

        turn.addMove(game.getTile(newPosition), player);

        game.addTurn(turn);
        game.setLastDiceRoll(rolledDice);
        game.setCanRoll(turn.calculateCanRoll(rolledDice));
        game.setCurrentPlayer(playerName);

        BaseTile newTile = game.getTile(newPosition);

        player.setCurrentTile(newTile.getName());

        player.setAlreadyPaidDebt(false);

        if (newTile instanceof Property) {
            Property property = (Property) newTile;

            if (property.getOwner() != null && !property.getOwner().equals(player) && !player.getAlreadyPaidDebt()) {
                collectDebt(gameId, property.getOwner().getName(), property.getName(), playerName);
            }
        }

        return game;
    }

    public Player getPlayer(String gameId, String playerName) {
        Game game = getGame(gameId);
        for (Player player : game.getPlayers()) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }

        throw new IllegalArgumentException("Player does not exist in this game.");
    }

    @Override
    public Game joinGame(String gameId, String name) {
        for (Game game : allGames) {
            if (game.getId().equals(gameId)) {
                if (game.getPlayers().isEmpty()) {
                    game.setCurrentPlayer(name);
                }

                game.addPlayer(name);
                return game;
            }
        }

        throw new IllegalArgumentException("Could not find game with gameId " + gameId);
    }

    @Override
    public Game setBankrupt(String gameId, String playerName) {
        for (Game game : allGames) {
            if (game.getId().equals(gameId)) {
                //set game ended
                game.setEnded();
                //set winner

                for (Player player : game.getPlayers()) {
                    if (player.getName().equals(playerName)) {
                        //set player bankrupt
                        player.setBankrupt();

                        //delete properties of player
                        deleteProperties(playerName, game);
                        deleteMoney(playerName, game);
                    }
                }

                game.setWinner(getWinner(game));


                return game;
            }
        }

        throw new IllegalArgumentException("Could not find game with ID " + gameId);
    }

    private void deleteProperties(String playerName, Game game) {
        for (Player player : game.getPlayers()) {
            if (player.getName().equals(playerName)) {
                player.deleteProperties();
            }
        }
    }

    private void deleteMoney(String playerName, Game game) {
        for (Player player : game.getPlayers()) {
            if (player.getName().equals(playerName)) {
                int totalMoney = player.getMoney();
                player.withdrawMoney(totalMoney);
            }
        }
    }

    private String getWinner(Game game) {
        List<Player> players = game.getPlayers();
        Collections.sort(players);
        return players.get(0).getName();
    }


    public Object changeTaxMethod(String gameId, String playerName, String method) {
        for (Game game : allGames) {
            if (game.getId().equals(gameId)) {
                for (Player player : game.getPlayers()) {
                    if (player.getName().equals(playerName)) {
                        player.changeTaxSystem(method);

                        return new JsonObject()
                                .put("name", playerName)
                                .put("currentTile", player.getCurrentTile())
                                .put("jailed", player.isJailed())
                                .put("money", player.getMoney())
                                .put("bankrupt", player.isBankrupt())
                                .put("getOutOfJailFreeCards", player.getGetOutOfJailFreeCards())
                                .put("taxSystem", player.getTaxSystem())
                                .put("properties", player.getProperties())
                                .put("debt", player.getDebt());
                    } else {
                        throw new IllegalArgumentException("Player does not exist");
                    }
                }
            } else {
                throw new IllegalArgumentException("Game does not exist");
            }
        }

        throw new IllegalArgumentException("Could not find game with ID " + gameId);
    }

    @Override
    public Object useEstimateTax(String gameId, String playerName) {
        return changeTaxMethod(gameId, playerName, "ESTIMATE");
    }

    @Override
    public Object useComputeTax(String gameId, String playerName) {
        return changeTaxMethod(gameId, playerName, "COMPUTE");
    }

    @Override
    public Game buyHouse(String gameId, String playerName, String propertyName) {
        Game game = getGame(gameId);
        Player player = getPlayer(gameId, playerName);
        StreetTile streetTile = (StreetTile) game.getTile(propertyName);

        if (!game.getCurrentPlayer().equals(playerName))
            throw new InvalidRequestException("You cannot buy a house right now.");

        if (game.getAvailableHouses() <= 0)
            throw new IllegalStateException("There are no houses left to buy.");

        if (player.hasProperty(propertyName)) {
            // Lower the xp of the player after buying a house
            int amount = streetTile.getAmountOfHouses();
            if (amount < 5) {
                player.withdrawMoney(streetTile.getHousePrice());
                streetTile.incr();
                game.decrAvailableHouses();     // Decrease availableHouses player
            } else {
                throw new IllegalStateException("You can't buy more houses.");
            }

            return game;
        }

        throw new IllegalArgumentException("You do not have this property");
    }

    @Override
    public Game sellHouse(String gameId, String playerName, String propertyName) {
        Game game = getGame(gameId);
        Player player = getPlayer(gameId, playerName);
        StreetTile streetTile = (StreetTile) game.getTile(propertyName);

        if (!game.getCurrentPlayer().equals(playerName))
            throw new InvalidRequestException("You cannot sell a house right now.");


        if (streetTile.getAmountOfHouses() < 1)
            throw new IllegalArgumentException("You can't sell a house because you don't have any on this tile.");

        if (player.hasProperty(propertyName)) {
            streetTile.decr();
            game.incrAvailableHouses();
            player.addMoney(streetTile.getHousePrice() / 2);

            return game;
        }

        throw new IllegalArgumentException("You do not have this property");
    }

    @Override
    public Game getOutOfJailFine(String gameId, String playerName) {
        Game game = getGame(gameId);
        Player player = getPlayer(gameId, playerName);

        if (player.isJailed()) {
            player.withdrawMoney(50);
            player.setJailed(false);
        }
        return game;
    }

    @Override
    public Game getOutOfJailFree(String gameId, String playerName) {
        Game game = getGame(gameId);
        Player player = getPlayer(gameId, playerName);

        if (player.isJailed()) {
            player.useGetOutOfJailFreeCards();
            player.setJailed(false);
        }
        return game;
    }

    @Override
    public Object buyProperty(String gameId, String playerName, String propertyName) {
        Game game = getGame(gameId);
        Player player = getPlayer(gameId, playerName);
        BaseTile tile = game.getTile(propertyName);

        if (!game.getCurrentPlayer().equals(playerName))
            throw new InvalidRequestException("You cannot buy this property right now.");


        if (tile instanceof Property) {
            Property property = (Property) tile;

            if (!property.hasOwner() && !player.hasProperty(propertyName)) {
                //paying for the property
                player.withdrawMoney(property.getCost());

                //property toevoegen aan speler
                player.addProperty(property);

                property.setOwner(player);

                return new JsonObject()
                        .put("property", propertyName)
                        .put("purchased", true);
            }
        }

        throw new IllegalArgumentException("someone already owns this property");
    }

    private void setNextCurrentPlayerInGame(Game game, Player player) {
        int indexOfCurrentPlayer = game.getPlayers().indexOf(player);
        int indexOfNextPlayer = indexOfCurrentPlayer + 1;

        if (indexOfNextPlayer == game.getNumberOfPlayers())
            indexOfNextPlayer = 0;

        String nextPlayer = game.getPlayers().get(indexOfNextPlayer).getName();

        game.setCurrentPlayer(nextPlayer);
        game.setCanRoll(true);
    }

    @Override
    public Object dontBuyProperty(String gameId, String playerName, String propertyName) {
        Game game = getGame(gameId);
        Player player = getPlayer(gameId, playerName);

        if (!game.getCurrentPlayer().equals(playerName))
            throw new InvalidRequestException("You cannot do this right now.");

        setNextCurrentPlayerInGame(game, player);

        return new JsonObject()
                .put("property", propertyName)
                .put("purchased", false);
    }

    @Override
    public Game collectDebt(String gameId, String playerName, String propertyName, String debtorName) {
        Game game = getGame(gameId);
        Player debtor = getPlayer(gameId, debtorName);
        Player owner = getPlayer(gameId, playerName);
        BaseTile tile = getProperty(propertyName, game);

        if (tile instanceof Property) {
            Property property = (Property) tile;

            if (property.getOwner().getName().equals(playerName)) {
                int rent = property.calculateRent();

                if (debtor.getMoney() < rent) {
                    setBankrupt(gameId, debtorName);
                }

                debtor.withdrawMoney(rent);
                owner.addMoney(rent);
            }

            debtor.setAlreadyPaidDebt(true);

            return game;
        }

        throw new IllegalArgumentException("Cannot pay rent on a tile that's not a property.");
    }

    public BaseTile getProperty(String propertyName, Game game) {
        if (game.getTileByName(propertyName) instanceof Property) {
            return game.getTileByName(propertyName);
        }
        throw new IllegalArgumentException("tile does not exist or tile is not a property!");
    }
}