package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.IService;
import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.BaseTile;
import be.howest.ti.monopoly.logic.implementation.Game;

import java.util.List;
import be.howest.ti.monopoly.logic.implementation.Game;
import be.howest.ti.monopoly.logic.implementation.Turn;
import io.vertx.core.json.JsonObject;

import java.util.List;


public class TestService implements IService {

    IService delegate = new ServiceAdapter();

    void setDelegate(IService delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getVersion() {
        return delegate.getVersion();
    }

    @Override
    public List<BaseTile> getTiles() {
        return delegate.getTiles();
    }

    @Override
    public BaseTile getTile(int position) {
        return delegate.getTile(position);
    }

    @Override
    public BaseTile getTile(String name) {
        return delegate.getTile(name);
    }

    @Override
    public List<String> getChance() {
        return delegate.getChance();
    }

    @Override
    public List<String> getCommunityChest() {
        return delegate.getCommunityChest();
    }

    @Override
    public List<Game> getGames(Request request) {
        return delegate.getGames(request);
    }

    @Override
    public Game createGame(int numberOfPlayers, String prefix) {
        return delegate.createGame(numberOfPlayers, prefix);
    }

    @Override
    public Object getDummyGame() {
        return delegate.getDummyGame();
    }

    @Override
    public Game joinGame(String gameId, String name) {
        return delegate.joinGame(gameId, name);
    }

    @Override
    public Game rollDice(String gameId, String playerName) {
        return delegate.rollDice(gameId, playerName);
    }

    @Override
    public Game sellHouse(String gameId, String playerName, String propertyName) {
        return delegate.sellHouse(gameId, playerName, propertyName);
    }

    @Override
    public Game getOutOfJailFine(String gameId, String playerName) {
        return delegate.getOutOfJailFine(gameId, playerName);
    }

    @Override
    public Game getOutOfJailFree(String gameId, String playerName) {
        return delegate.getOutOfJailFree(gameId, playerName);
    }

    @Override
    public Game collectDebt(String gameId, String playerName, String propertyName, String debtorName) {
        return delegate.collectDebt(gameId, playerName, propertyName, debtorName);
    }

    @Override
    public List<Game> clearGameList() {
        return delegate.clearGameList();
    }

    @Override
    public Game setBankrupt(String gameId, String playerName) {
        return delegate.setBankrupt(gameId, playerName);
    }

    @Override
    public Object useEstimateTax(String gameId, String playerName) {return delegate.useEstimateTax(gameId, playerName);}

    @Override
    public Object useComputeTax(String gameId, String playerName) {return delegate.useComputeTax(gameId, playerName);}

    @Override
    public Game getGame(String gameId) {
        return delegate.getGame(gameId);
    }

    @Override
    public Game buyHouse(String gameId, String playerName, String propertyName) {
        return delegate.buyHouse(gameId, playerName, propertyName);
    }

    @Override
    public Object buyProperty(String gameId, String playerName, String propertyName){
        return  delegate.buyProperty(gameId, playerName, propertyName);
    }

    @Override
    public Object dontBuyProperty(String gameId, String playerName, String propertyName) {
        return delegate.dontBuyProperty(gameId, playerName, propertyName);
    }
}
