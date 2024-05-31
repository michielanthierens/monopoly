package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.Game;
import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.Game;
import be.howest.ti.monopoly.logic.implementation.Turn;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;


class OpenApiTurnManagementTests extends OpenApiTestsBase {

    @Test
    void rollDice(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Game rollDice(String gameId, String playerName) {
                return null;
            }
        });

        post(
                testContext,
                "/games/gameid/players/Alice/dice",
                "gameid-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void rollDiceUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players/Alice/dice",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void declareBankruptcy(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Game setBankrupt(String gameId, String playerName) {
                return null;
            }
        });

        post(
                testContext,
                "/games/gameid/players/Alice/bankruptcy",
                "gameid-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void declareBankruptcyUnauthorized(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Game setBankrupt(String gameId, String playerName) {
                return null;
            }
        });
        post(
                testContext,
                "/games/game-id/players/Alice/bankruptcy",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }
}
