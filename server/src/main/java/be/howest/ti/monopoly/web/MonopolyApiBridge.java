package be.howest.ti.monopoly.web;

import be.howest.ti.monopoly.logic.IService;
import be.howest.ti.monopoly.logic.exceptions.IllegalMonopolyActionException;
import be.howest.ti.monopoly.logic.exceptions.InsufficientFundsException;
import be.howest.ti.monopoly.logic.exceptions.MonopolyResourceNotFoundException;
import be.howest.ti.monopoly.logic.implementation.BaseTile;
import be.howest.ti.monopoly.logic.implementation.MonopolyService;
import be.howest.ti.monopoly.web.exceptions.ForbiddenAccessException;
import be.howest.ti.monopoly.web.exceptions.InvalidRequestException;
import be.howest.ti.monopoly.web.exceptions.NotYetImplementedException;
import be.howest.ti.monopoly.web.tokens.MonopolyUser;
import be.howest.ti.monopoly.web.tokens.PlainTextTokens;
import be.howest.ti.monopoly.web.tokens.TokenManager;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BearerAuthHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonopolyApiBridge {

    private static final Logger LOGGER = Logger.getLogger(MonopolyApiBridge.class.getName());

    private final IService service;
    private final TokenManager tokenManager;

    public MonopolyApiBridge(IService service, TokenManager tokenManager) {
        this.service = service;
        this.tokenManager = tokenManager;
    }

    public MonopolyApiBridge() {
        this(new MonopolyService(), new PlainTextTokens());
    }

    public Router buildRouter(RouterBuilder routerBuilder) {
        LOGGER.log(Level.INFO, "Installing CORS handlers");
        routerBuilder.rootHandler(createCorsHandler());

        LOGGER.log(Level.INFO, "Installing security handlers");
        routerBuilder.securityHandler("playerAuth", BearerAuthHandler.create(tokenManager));


        LOGGER.log(Level.INFO, "Installing Failure handlers");
        routerBuilder.operations().forEach(op -> op.failureHandler(this::onFailedRequest));

        LOGGER.log(Level.INFO, "Installing Actual handlers");

        // General Game and API Info
        routerBuilder.operation("getInfo").handler(this::getInfo);//done
        routerBuilder.operation("getTiles").handler(this::getTiles);//done
        routerBuilder.operation("getTile").handler(this::getTile);//done
        routerBuilder.operation("getChance").handler(this::getChance);//done
        routerBuilder.operation("getCommunityChest").handler(this::getCommunityChest);//done

        // Managing Games
        routerBuilder.operation("getGames").handler(this::getGames);//done
        routerBuilder.operation("createGame").handler(this::createGame);//done
        routerBuilder.operation("joinGame").handler(this::joinGame);//done
        routerBuilder.operation("clearGameList").handler(this::clearGameList);//done

        // Game Info
        routerBuilder.operation("getGame").handler(this::getGame);
        routerBuilder.operation("getDummyGame").handler(this::getDummyGame);//done

        // Turn Management
        routerBuilder.operation("rollDice").handler(this::rollDice);//done
        routerBuilder.operation("declareBankruptcy").handler(this::declareBankruptcy);//done

        // Tax Management
        routerBuilder.operation("useEstimateTax").handler(this::useEstimateTax);//done
        routerBuilder.operation("useComputeTax").handler(this::useComputeTax);//done

        // Buying property
        routerBuilder.operation("buyProperty").handler(this::buyProperty);//done
        routerBuilder.operation("dontBuyProperty").handler(this::dontBuyProperty);//done

        // Improving property
        routerBuilder.operation("buyHouse").handler(this::buyHouse);//done
        routerBuilder.operation("sellHouse").handler(this::sellHouse);//done
        routerBuilder.operation("buyHotel").handler(this::buyHotel);
        routerBuilder.operation("sellHotel").handler(this::sellHotel);

        // Mortgage
        routerBuilder.operation("takeMortgage").handler(this::takeMortgage);
        routerBuilder.operation("settleMortgage").handler(this::settleMortgage);

        // Interaction with other player
        routerBuilder.operation("collectDebt").handler(this::collectDebt);//done
        routerBuilder.operation("trade").handler(this::trade);

        // Prison
        routerBuilder.operation("getOutOfJailFine").handler(this::getOutOfJailFine);//done
        routerBuilder.operation("getOutOfJailFree").handler(this::getOutOfJailFree);//done

        // Auctions
        routerBuilder.operation("getBankAuctions").handler(this::getBankAuctions);
        routerBuilder.operation("placeBidOnBankAuction").handler(this::placeBidOnBankAuction);
        routerBuilder.operation("getPlayerAuctions").handler(this::getPlayerAuctions);
        routerBuilder.operation("startPlayerAuction").handler(this::startPlayerAuction);
        routerBuilder.operation("placeBidOnPlayerAuction").handler(this::placeBidOnPlayerAuction);


        LOGGER.log(Level.INFO, "All handlers are installed");
        return routerBuilder.createRouter();
    }

    private void getInfo(RoutingContext ctx) {
        Response.sendJsonResponse(ctx, 200, new JsonObject().put("name", "monopoly").put("version", service.getVersion()));
    }

    private void getTiles(RoutingContext ctx) {
        Response.sendJsonResponse(ctx, 200, service.getTiles());
    }

    private void getTile(RoutingContext ctx) {
        Request request = Request.from(ctx);

        BaseTile tile;
        if(request.hasTilePosition()){
            int position = request.getTilePosition();


            tile = service.getTile(position);
        }else{
            String name = request.getTileName();
            tile = service.getTile(name);
        }

        Response.sendJsonResponse(ctx, 200, tile);
    }

    private void getChance(RoutingContext ctx) {
        Response.sendJsonResponse(ctx, 200, service.getChance());
    }

    private void getCommunityChest(RoutingContext ctx) {
        Response.sendJsonResponse(ctx, 200, service.getCommunityChest());
    }

    private void clearGameList(RoutingContext ctx) {
        Response.sendJsonResponse(ctx, 200, service.clearGameList());
    }

    private void createGame(RoutingContext ctx) {
        Request request = Request.from(ctx);
        JsonObject body = request.getRequestParameters().toJson().getJsonObject("body");

        int numberOfPlayers = body.getInteger("numberOfPlayers");
        String prefix = body.getString("prefix");

        Response.sendJsonResponse(ctx, 200, service.createGame(numberOfPlayers, prefix));
    }

    private void getGames(RoutingContext ctx) {
        Request request = Request.from(ctx);
        Response.sendJsonResponse(ctx, 200, service.getGames(request));
    }

    private void joinGame(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerName();
        service.joinGame(gameId, playerName);

        String playerToken = tokenManager.createToken(new MonopolyUser(gameId, playerName));

        Response.sendJsonResponse(ctx,200, new JsonObject()
                .put("token", playerToken)
        );
    }

    private void getGame(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();

        if (!request.isAuthorized(gameId)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.getGame(gameId));
    }

    private void getDummyGame(RoutingContext ctx) {
        Response.sendJsonResponse(ctx, 200, service.getDummyGame());
    }

    private void useEstimateTax(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.useEstimateTax(gameId, playerName));
    }

    private void useComputeTax(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.useComputeTax(gameId, playerName));
    }

    private void rollDice(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.rollDice(gameId, playerName));
    }

    private void declareBankruptcy(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.setBankrupt(gameId, playerName));
    }

    private void buyProperty(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyNameFromPAth();

       if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.buyProperty(gameId, playerName, propertyName));
    }

    private void dontBuyProperty(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyNameFromPAth();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.dontBuyProperty(gameId, playerName, propertyName));
    }

    private void collectDebt(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyName();
        String debtorName = request.getDebtorNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.collectDebt(gameId, playerName, propertyName, debtorName) );
    }

    private void takeMortgage(RoutingContext ctx) {
        throw new NotYetImplementedException("takeMortgage");
    }

    private void settleMortgage(RoutingContext ctx) {
        throw new NotYetImplementedException("settleMortgage");
    }

    private void buyHouse(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyName();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.buyHouse(gameId, playerName, propertyName));
    }

    private void sellHouse(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerNameFromPath();
        String propertyName = request.getPropertyName();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.sellHouse(gameId, playerName, propertyName));
    }

    private void buyHotel(RoutingContext ctx) {
        throw new NotYetImplementedException("buyHotel");
    }

    private void sellHotel(RoutingContext ctx) {
        throw new NotYetImplementedException("sellHotel");
    }

    private void getOutOfJailFine(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.getOutOfJailFine(gameId, playerName));
    }

    private void getOutOfJailFree(RoutingContext ctx) {
        Request request = Request.from(ctx);

        String gameId = request.getGameID();
        String playerName = request.getPlayerNameFromPath();

        if (!request.isAuthorized(gameId, playerName)) {
            throw new ForbiddenAccessException("Not authorized, invalid token.");
        }

        Response.sendJsonResponse(ctx, 200, service.getOutOfJailFree(gameId, playerName));
    }

    private void getBankAuctions(RoutingContext ctx) {
        throw new NotYetImplementedException("getBankAuctions");
    }

    private void placeBidOnBankAuction(RoutingContext ctx) {
        throw new NotYetImplementedException("placeBidOnBankAuction");
    }

    private void getPlayerAuctions(RoutingContext ctx) {
        throw new NotYetImplementedException("getPlayerAuctions");
    }

    private void startPlayerAuction(RoutingContext ctx) {
        throw new NotYetImplementedException("startPlayerAuction");
    }

    private void placeBidOnPlayerAuction(RoutingContext ctx) {
        throw new NotYetImplementedException("placeBidOnPlayerAuction");
    }

    private void trade(RoutingContext ctx) {
        throw new NotYetImplementedException("trade");
    }

    private void onFailedRequest(RoutingContext ctx) {
        Throwable cause = ctx.failure();
        int code = ctx.statusCode();
        String quote = Objects.isNull(cause) ? "" + code : cause.getMessage();

        // Map custom runtime exceptions to a HTTP status code.
        LOGGER.log(Level.INFO, "Failed request", cause);
        if (cause instanceof InvalidRequestException) {
            code = 400;
        } else if (cause instanceof IllegalArgumentException) {
            code = 400;
        } else if (cause instanceof InsufficientFundsException) {
            code = 402;
        } else if (cause instanceof ForbiddenAccessException) {
            code = 403;
        } else if (cause instanceof MonopolyResourceNotFoundException) {
            code = 404;
        } else if (cause instanceof IllegalMonopolyActionException) {
            code = 409;
        } else if (cause instanceof NotYetImplementedException) {
            code = 501;
        } else {
            LOGGER.log(Level.WARNING, "Failed request", cause);
        }

        Response.sendFailure(ctx, code, quote);
    }

    private CorsHandler createCorsHandler() {
        return CorsHandler.create(".*.").allowedHeader("x-requested-with").allowedHeader("Access-Control-Allow-Origin").allowedHeader("origin").allowedHeader("Content-Type").allowedHeader("accept").allowedMethod(HttpMethod.GET).allowedMethod(HttpMethod.POST).allowedMethod(HttpMethod.OPTIONS).allowedMethod(HttpMethod.PATCH).allowedMethod(HttpMethod.DELETE).allowedMethod(HttpMethod.PUT);
    }
}
