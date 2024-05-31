package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.Game;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;


class OpenApiPrisonTests extends OpenApiTestsBase {

    @Test
    void getOutOfJailFine(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Game getOutOfJailFine(String gameId, String playerName) {
                return null;
            }
        });
        post(
                testContext,
                "/games/gameid/prison/Alice/fine",
                "gameid-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void getOutOfJailFineUnauthorized(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Game getOutOfJailFine(String gameId, String playerName) {
                return null;
            }
        });
        post(
                testContext,
                "/games/game-id/prison/Alice/fine",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void getOutOfJailFree(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Game getOutOfJailFree(String gameId, String playerName) {
                return null;
            }
        });
        post(
                testContext,
                "/games/gameid/prison/Alice/free",
                "gameid-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void getOutOfJailFreeUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/prison/Alice/free",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }
}
