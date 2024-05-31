package be.howest.ti.monopoly.logic.implementation;

import be.howest.ti.monopoly.web.exceptions.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MonopolyServiceTest {

    private static final String GAME_ID = "group08_000";
    MonopolyService monopolyService = new MonopolyService();

    @BeforeEach
    void setUpGame() {
        monopolyService.clearGameList();
    }

    @Test
    void getVersion() {
        assertEquals("0.0.1", monopolyService.getVersion());
    }

    @Test
    void getAllTiles() {
        List<BaseTile> allTiles = monopolyService.getTiles();

        int listLength = allTiles.size();

        assertEquals(40, listLength);
    }

    @Test
    void getAllChanceCards() {
        List<String> allCards = monopolyService.getChance();

        int listLength = allCards.size();

        assertEquals(6, listLength);
    }

    @Test
    void getAllCommunityChestCards() {
        List<String> allCards = monopolyService.getCommunityChest();

        int listLength = allCards.size();

        assertEquals(12, listLength);
    }

    @Test
    void getTileByPosition() {
        BaseTile tile = monopolyService.getTile(0);

        assertEquals("Spawner", tile.getName());
        assertEquals(0, tile.getPosition());
    }

    @Test
    void getTileByPositionNotFound() {
        assertThrows(IllegalArgumentException.class, () -> monopolyService.getTile(45));
    }

    @Test
    void getTileByName() {
        BaseTile tile = monopolyService.getTile("Spawner");

        assertEquals("Spawner", tile.getName());
        assertEquals(0, tile.getPosition());
    }

    @Test
    void getTileByNameNotFound() {
        assertThrows(IllegalArgumentException.class, () -> monopolyService.getTile("this tile does not exist."));
    }

    @Test
    void createGame() {
        monopolyService.createGame(2, "group08");

        assertEquals(1, MonopolyService.allGames.size());
        assertEquals(GAME_ID, MonopolyService.allGames.get(0).getId());
    }

    // TODO: Add test for getGames

    @Test
    void getGame() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);

        String gameId = game.getId();
        int numberOfPlayers = game.getNumberOfPlayers();

        assertEquals(GAME_ID, gameId);
        assertEquals(2, numberOfPlayers);
    }

    @Test
    void getGameDoesNotExist() {
        assertThrows(IllegalArgumentException.class,
                () -> monopolyService.getGame("test"));
    }

    @Test
    void clearGameList() {
        monopolyService.createGame(2, "group08");

        assertEquals(1, MonopolyService.allGames.size());

        monopolyService.clearGameList();

        assertEquals(0, MonopolyService.allGames.size());
    }

    @Test
    void joinGame() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);

        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");

        assertEquals(2, game.getPlayers().size());
        assertEquals("alice", game.getCurrentPlayer());
    }

    @Test
    void joinGameWithSameName() {
        monopolyService.createGame(2, "group08");

        monopolyService.joinGame(GAME_ID, "alice");

        assertThrows(IllegalArgumentException.class, () -> monopolyService.joinGame(GAME_ID, "alice"));
    }

    @Test
    void joinGameDoesNotExist() {
        assertThrows(IllegalArgumentException.class,
            () -> monopolyService.joinGame("test", "alice"));
    }

    @Test
    void rollDice() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);
        monopolyService.joinGame(GAME_ID, "alice");

        monopolyService.rollDice(game.getId(), "alice");

        int turnCount = game.getTurns().size();
        int[] lastDiceRoll = game.getLastRolledDice();
        String currentPlayer = game.getCurrentPlayer();

        assertEquals(1, turnCount);
        assertEquals(game.getTurns().get(0).getRoll(), lastDiceRoll);
        assertEquals("alice", currentPlayer);
    }

    @Test
    void getPlayer() {
        monopolyService.createGame(2, "group08");
        monopolyService.joinGame(GAME_ID, "alice");

        Player player = monopolyService.getPlayer(GAME_ID, "alice");

        assertEquals("alice", player.getName());
    }

    @Test
    void getPlayerThatDoesNotExist() {
        monopolyService.createGame(2, "group08");
        monopolyService.joinGame(GAME_ID, "alice");

        assertThrows(IllegalArgumentException.class, () -> monopolyService.getPlayer(GAME_ID, "bob"));
    }

    @Test
    void getPlayerInGameThatDoesNotExist() {
        monopolyService.createGame(2, "group08");
        monopolyService.joinGame(GAME_ID, "alice");

        assertThrows(IllegalArgumentException.class, () -> monopolyService.getPlayer("group08_001", "alice"));
    }

    @Test
    void setPlayerBankrupt() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);

        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");
        assertTrue(game.isStarted());

        Player alice = monopolyService.getPlayer(GAME_ID, "alice");
        Player bob = monopolyService.getPlayer(GAME_ID, "bob");

        alice.addProperty(monopolyService.getTile(1));
        bob.addProperty(monopolyService.getTile(3));
        assertEquals(1, alice.getProperties().size());
        assertEquals(1, bob.getProperties().size());

        monopolyService.setBankrupt(GAME_ID, "alice");

        assertEquals(0, alice.getProperties().size());
        assertEquals(1, bob.getProperties().size());

        assertFalse(bob.isBankrupt());
        assertTrue(alice.isBankrupt());

        assertTrue(game.isEnded());
        assertEquals("bob", game.getWinner());
    }

    @Test
    void getRightWinner() {
        monopolyService.createGame(3, "group08");
        Game game = monopolyService.getGame(GAME_ID);

        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");
        monopolyService.joinGame(GAME_ID, "carol");

        Player alice = monopolyService.getPlayer(GAME_ID, "alice");
        Player bob = monopolyService.getPlayer(GAME_ID, "bob");
        Player carol = monopolyService.getPlayer(GAME_ID, "carol");

        alice.addMoney(200);
        alice.addProperty(monopolyService.getTile(3));
        carol.addMoney(100);
        bob.withdrawMoney(150);

        assertEquals(1760, alice.getTotalNetWorth());
        monopolyService.setBankrupt(GAME_ID, "alice");
        assertEquals(0, alice.getTotalNetWorth());
        assertEquals(1600, carol.getTotalNetWorth());
        assertEquals(1350, bob.getTotalNetWorth());

        assertEquals("carol", game.getWinner());

    }

    @Test
    void changeTaxMethodToEstimate() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);
        monopolyService.joinGame(GAME_ID, "alice");
        Player alice = monopolyService.getPlayer(GAME_ID, "alice");

        monopolyService.changeTaxMethod(GAME_ID, "alice", "ESTIMATE");

        assertEquals("ESTIMATE", alice.getTaxSystem());
    }

    @Test
    void changeTaxMethodToCompute() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);
        monopolyService.joinGame(GAME_ID, "alice");
        Player alice = monopolyService.getPlayer(GAME_ID, "alice");

        monopolyService.changeTaxMethod(GAME_ID, "alice", "COMPUTE");

        assertEquals("COMPUTE", alice.getTaxSystem());
    }

    @Test
    void changeTaxMethodPlayerDoesNotExist() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);
        monopolyService.joinGame(GAME_ID, "alice");

        assertThrows(IllegalArgumentException.class, () -> monopolyService.changeTaxMethod(GAME_ID, "bob", "COMPUTE"));
    }

    @Test
    void changeTaxMethodGameDoesNotExist() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);
        monopolyService.joinGame(GAME_ID, "alice");

        assertThrows(IllegalArgumentException.class, () -> monopolyService.changeTaxMethod("game doesn't exist", "bob", "COMPUTE"));
    }

    @Test
    void buyProperty() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);
        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");
        Property tile = (Property) game.getTile("Wood");
        Player alice = monopolyService.getPlayer(GAME_ID, "alice");

        monopolyService.buyProperty(GAME_ID, "alice", "Wood");

        assertEquals(1, alice.getProperties().size());
        assertThrows(InvalidRequestException.class, () -> monopolyService.buyProperty(GAME_ID, "bob", "Redstone"));
        assertThrows(IllegalArgumentException.class, () -> monopolyService.buyProperty(GAME_ID, "alice", "Wood"));
        assertTrue(alice.hasProperty("Wood"));
        assertEquals(alice, tile.getOwner());
    }

    @Test
    void DontBuyProperty() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);
        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");
        Property tile = (Property) game.getTile("Wood");
        Player alice = monopolyService.getPlayer(GAME_ID, "alice");

        monopolyService.dontBuyProperty(GAME_ID, "alice", "Wood");

        assertEquals(0, alice.getProperties().size());
        assertFalse(alice.hasProperty("Wood"));
        assertNull(tile.getOwner());
    }

    @Test
    void buyHouse() {
        monopolyService.createGame(2, "group08");
        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");
        Game game = monopolyService.getGame(GAME_ID);
        StreetTile tile = (StreetTile) game.getTile("WOOD");
        Player alice = monopolyService.getPlayer(GAME_ID, "alice");
        Player bob = monopolyService.getPlayer(GAME_ID, "bob");

        assertThrows(IllegalArgumentException.class,
                () -> monopolyService.buyHouse(GAME_ID, "alice", "Wood"));

        game.setCurrentPlayer("alice");
        monopolyService.buyProperty(GAME_ID, "alice", "Wood");

        game.setCurrentPlayer("alice");
        monopolyService.buyHouse(GAME_ID, "alice", "Wood");

        assertEquals(31, game.getAvailableHouses());
        assertEquals(1, tile.getAmountOfHouses());
    }

    @Test
    void sellHouse() {
        monopolyService.createGame(2, "group08");
        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");
        Game game = monopolyService.getGame(GAME_ID);
        StreetTile tile = (StreetTile) game.getTile("WOOD");
        Player alice = monopolyService.getPlayer(GAME_ID, "alice");
        Player bob = monopolyService.getPlayer(GAME_ID, "bob");

        assertThrows(IllegalArgumentException.class,
                () -> monopolyService.buyHouse(GAME_ID, "alice", "Wood"));

        game.setCurrentPlayer("alice");
        monopolyService.buyProperty(GAME_ID, "alice", "Wood");

        game.setCurrentPlayer("alice");
        monopolyService.buyHouse(GAME_ID, "alice", "Wood");

        assertEquals(31, game.getAvailableHouses());
        assertEquals(1, tile.getAmountOfHouses());

        game.setCurrentPlayer("alice");
        monopolyService.sellHouse(GAME_ID, "alice", "Wood");

        assertEquals(32, game.getAvailableHouses());
        assertEquals(0, tile.getAmountOfHouses());
    }

    @Test
    void getOutOfJailFine() {
        monopolyService.createGame(2, "group08");
        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");
        Player alice = monopolyService.getPlayer(GAME_ID, "alice");

        alice.setJailed(true);

        monopolyService.getOutOfJailFine(GAME_ID, "alice");

        assertEquals(1450, alice.getMoney());
        assertFalse(alice.isJailed());
    }

    @Test
    void getOutOfJailFree() {
        monopolyService.createGame(2, "group08");
        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");
        Player alice = monopolyService.getPlayer(GAME_ID, "alice");

        alice.addGetOutOfJailFreeCards();
        alice.setJailed(true);

        monopolyService.getOutOfJailFree(GAME_ID, "alice");

        assertFalse(alice.isJailed());
    }

    @Test
    void collectDebt() {
        monopolyService.createGame(2, "group08");
        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");
        Game game = monopolyService.getGame(GAME_ID);
        Player debtor = monopolyService.getPlayer(GAME_ID, "alice");
        Player owner = monopolyService.getPlayer(GAME_ID, "bob");

        game.setCurrentPlayer("bob");
        monopolyService.buyProperty(GAME_ID, "bob", "Wood");

        game.setCurrentPlayer("bob");
        monopolyService.collectDebt(GAME_ID, "bob", "Wood", "alice");

        assertEquals(1442, owner.getMoney());   // 1442 => 1500 - cost wood + rent
        assertEquals(1498, debtor.getMoney());
    }

    @Test
    void payRent() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);
        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");
        Player bob = monopolyService.getPlayer(GAME_ID, "bob");

        monopolyService.buyProperty(GAME_ID, "alice", "Wood");
        game.setCurrentPlayer("alice");
        monopolyService.buyHouse(GAME_ID, "alice", "Wood");

        while (!bob.getCurrentTile().equals("Wood"))
        {
            game.setCanRoll(true);
            monopolyService.rollDice(GAME_ID, "bob");
        }

        assertTrue(bob.getAlreadyPaidDebt());
    }

    @Test
    void getProperty() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);

        BaseTile tile = monopolyService.getProperty("Wood", game);

        assertEquals("Wood", tile.getName());
    }

    @Test
    void getPropertyDoesNotExist() {
        monopolyService.createGame(2, "group08");
        Game game = monopolyService.getGame(GAME_ID);

        assertThrows(IllegalArgumentException.class, () ->
                monopolyService.getProperty("Tile", game));
    }

    @Test
    void changeCurrentPlayer() {
        monopolyService.createGame(2, "group08");
        monopolyService.joinGame(GAME_ID, "alice");
        monopolyService.joinGame(GAME_ID, "bob");
        Game game = monopolyService.getGame(GAME_ID);

        assertEquals("alice", game.getCurrentPlayer());

        monopolyService.dontBuyProperty(GAME_ID, "alice", "Wood");
        assertEquals("bob", game.getCurrentPlayer());

        monopolyService.buyProperty(GAME_ID, "bob", "Redstone");
        assertEquals("bob", game.getCurrentPlayer());

        monopolyService.buyHouse(GAME_ID, "bob", "Redstone");
        assertEquals("bob", game.getCurrentPlayer());
    }

    @Test
    void pullChanceCard() {
        monopolyService.createGame(2, "group08");
        monopolyService.joinGame(GAME_ID, "alice");
        Game game = monopolyService.getGame(GAME_ID);

        Player player = new Player("alice");
        player.setCurrentTile("Lucky_Block_1");

        Turn turn = new Turn(player.getName());
        turn.addMove(game.getTile(player.getCurrentTile()), player);
        assertFalse(player.getMoney() == 1500 && player.getGetOutOfJailFreeCards() == 0 );
    }

    @Test
    void pullCommunityChest() {
        monopolyService.createGame(2, "group08");
        monopolyService.joinGame(GAME_ID, "alice");
        Game game = monopolyService.getGame(GAME_ID);

        Player player = new Player("alice");
        player.setCurrentTile("Chest_1");

        Turn turn = new Turn(player.getName());
        turn.addMove(game.getTile(player.getCurrentTile()), player);

        assertFalse(player.getMoney() == 1500 && player.getGetOutOfJailFreeCards() == 0 );
    }
}