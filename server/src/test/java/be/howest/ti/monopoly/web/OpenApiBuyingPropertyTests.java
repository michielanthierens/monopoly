package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import be.howest.ti.monopoly.logic.implementation.*;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;


class OpenApiBuyingPropertyTests extends OpenApiTestsBase {

    @Test
    void buyProperty(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Object buyProperty(String gameId, String playerName, String propertyName) {
                return null;
            }
        });

        post(
                testContext,
                "/games/gameid/players/Alice/properties/some-property",
                "gameid-Alice",
                new JsonObject(),
                response -> assertOkResponse(response)
        );
    }

    @Test
    void buyPropertyUnauthorized(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Object buyProperty(String gameId, String playerName, String propertyName) {
                return null;
            }
        });
        post(
                testContext,
                "/games/game-id/players/Alice/properties/some-property",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void dontBuyProperty(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Object dontBuyProperty(String gameId, String playerName, String propertyName) {
                return null;
            }
        });
        delete(
                testContext,
                "/games/gameid/players/Alice/properties/some-property",
                "gameid-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void dontBuyPropertyUnauthorized(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Object dontBuyProperty(String gameId, String playerName, String propertyName) {
                return null;
            }
        });
        delete(
                testContext,
                "/games/game-id/players/Alice/properties/some-property",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }
}
