package be.howest.ti.monopoly.logic;

import be.howest.ti.monopoly.logic.implementation.BaseTile;
import be.howest.ti.monopoly.logic.implementation.Game;
import be.howest.ti.monopoly.web.Request;

import java.util.List;

public interface IService {
    String getVersion();

    List<BaseTile> getTiles();

    List<String> getChance();

    List<String> getCommunityChest();

    BaseTile getTile(int position);

    BaseTile getTile(String name);

    List<Game> getGames(Request request);

    Game createGame(int numberOfPlayers, String prefix);

    Object getDummyGame();

    Game joinGame(String gameId, String name);

    Game setBankrupt(String gameId, String playerName);

    Object useEstimateTax(String gameId, String playerName);

    Object useComputeTax(String gameId, String playerName);

    Object buyProperty(String gameId, String playerName, String propertyName);

    Object dontBuyProperty(String gameId, String playerName, String propertyName);

    Game buyHouse(String gameId, String playerName, String propertyName);

    Game getGame(String gameId);

    Game rollDice(String gameId, String playerName);

    Game sellHouse(String gameId, String playerName, String propertyName);

    Game getOutOfJailFine(String gameId, String playerName);

    Game getOutOfJailFree(String gameId, String playerName);

    Game collectDebt(String gameId, String playerName, String propertyName, String debtorName);

    List<Game> clearGameList();
}
