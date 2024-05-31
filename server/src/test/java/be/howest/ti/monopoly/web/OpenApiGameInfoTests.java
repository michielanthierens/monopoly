package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.Game;

import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;


class OpenApiGameInfoTests extends OpenApiTestsBase {

    @Test
    void getGame(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Game getGame(String gameID) {
                return null;
            }
        });

        get(
                testContext,
                "/games/gameid",
                "gameid-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void getGameUnauthorized(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Game getGame(String gameID) {
                return null;
            }
        });
        get(
                testContext,
                "/games/game-id",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void getDummyGame(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Object getDummyGame() {
                return null;
            }
        });
        get(
                testContext,
                "/games/dummy",
                null,
                response -> assertOkResponse(response)
        );
    }
}
