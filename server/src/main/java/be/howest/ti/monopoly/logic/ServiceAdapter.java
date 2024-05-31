package be.howest.ti.monopoly.logic;

import be.howest.ti.monopoly.logic.implementation.BaseTile;
import be.howest.ti.monopoly.logic.implementation.Game;
import be.howest.ti.monopoly.logic.implementation.Turn;
import be.howest.ti.monopoly.web.Request;

import java.util.List;

public class ServiceAdapter implements IService {

    @Override
    public String getVersion() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<BaseTile> getTiles() {
        throw new UnsupportedOperationException();
    }

    @Override
    public BaseTile getTile(int position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BaseTile getTile(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getChance() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getCommunityChest() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Game> getGames(Request request) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game createGame(int numberOfPlayers, String prefix) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getDummyGame() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game joinGame(String playerToken, String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game setBankrupt(String gameId, String playerName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object useEstimateTax(String gameId, String playerName) {throw new UnsupportedOperationException();}

    @Override
    public Object useComputeTax(String gameId, String playerName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object buyProperty(String gameId, String playerName, String propertyName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object dontBuyProperty(String gameId, String playerName, String propertyName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game getGame(String gameId) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Game rollDice(String gameId, String playerName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game sellHouse(String gameId, String playerName, String propertyName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game getOutOfJailFine(String gameId, String playerName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game getOutOfJailFree(String gameId, String playerName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game collectDebt(String gameId, String playerName, String propertyName, String debtorName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Game> clearGameList() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Game buyHouse(String gameId, String playerName, String propertyName) {
        throw new UnsupportedOperationException();
    }


}
