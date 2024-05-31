package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.ServiceAdapter;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;

import java.util.Collections;


class OpenApiTaxManagementTests extends OpenApiTestsBase {

    @Test
    void useEstimateTax(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Object useEstimateTax(String gameId, String playerName){
                return null;
            }
        });
        post(
                testContext,
                "/games/gameid/players/Alice/tax/estimate",
                "gameid-Alice",
                new JsonObject()
                        .put("name", "playerNameValue")
                        .put("currentTile", "currentTileValue")
                        .put("jailed", false)
                        .put("money", 1500)
                        .put("bankrupt", false)
                        .put("getOutOfJailFreeCards", 0)
                        .put("taxSystem", "TaxSystemValue")
                        .put("properties", Collections.emptyList())
                        .put("debt", 0),
                response -> assertOkResponse(response)
        );
    }

    @Test
    void useEstimateTaxUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players/Alice/tax/estimate",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }

    @Test
    void useComputeTax(final VertxTestContext testContext) {
        service.setDelegate(new ServiceAdapter() {
            @Override
            public Object useComputeTax(String gameId, String playerName){
                return null;
            }
        });
        post(
                testContext,
                "/games/gameid/players/Alice/tax/compute",
                "gameid-Alice",
                response -> assertOkResponse(response)
        );
    }

    @Test
    void useComputeTaxUnauthorized(final VertxTestContext testContext) {
        post(
                testContext,
                "/games/game-id/players/Alice/tax/compute",
                null,
                response -> assertErrorResponse(response, 401)
        );
    }
}
