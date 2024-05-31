"use strict";

function waitingScreenInit() {
    loadGameCode();
    loadPawnOptions();
    document.querySelector("#player-list #readyButton").addEventListener('click', function (e) {
        selectPawn(e);
        addPlayerToGame(e);
        removesReadyButton(e);
    });

}

function loadGameCode() {
    const selector = document.querySelector("section#code p");
    selector.innerHTML = "";

    const gameID = loadFromStorage("gameID");
    selector.insertAdjacentHTML('beforeend', gameID);
}

function loadPawnOptions() {
    const pawnList = document.querySelector("#pawn_list");

    _config.pawns.forEach(pawn => {
        const html = `<option id=${pawn}>${pawn}</option>`;
        pawnList.insertAdjacentHTML("beforeend", html);
    });
}

function loadPlayers() {
    const selector = document.querySelector("select#players-and-pawns");
    selector.innerHTML = "";
    const gameID = loadFromStorage("gameID");

    fetchFromServer(`/games/${gameID}`, 'GET').then(gameInfo => {
        const players = gameInfo.players;
        for (const player of players) {
            selector.insertAdjacentHTML('beforeend', `<option>${player.name}</option>`);
        }
    });
}

function selectPawn(e) {
    e.preventDefault();
    const pawn = document.querySelector("select#pawn_list").value;
    saveToStorage("pawn", pawn);
}

function addPlayerToGame(e) {
    e.preventDefault();
    const playerName = loadFromStorage("username");
    const gameID = loadFromStorage("gameID");
    const requestBody = {
        "playerName": playerName
    };

    fetchFromServer(`/games/${gameID}/players`, 'POST', requestBody).then(
        function (token) {
        setToken(token);

        saveToStorage("token", token);
        setInterval(loadPlayers, 1000);
        setInterval(startGame, 1000);
        setInterval(loadReadyPlayersOfTotalPlayers, 1000); // load the X of X players, ready for example: 1/3
    }).catch(errorHandler);
}

function removesReadyButton(e) {
    document.querySelector("#readyButton").classList.toggle("hidden");
}

function startGame() {
    const gameID = loadFromStorage("gameID");

    fetchFromServer(`/games/${gameID}`, 'GET').then(game => {
        if (game.started) {
            window.location.href = "gameboard.html";
        }
    });
}

function loadReadyPlayersOfTotalPlayers() {
    const gameID = loadFromStorage("gameID");
    fetchFromServer(`/games/${gameID}`, 'GET').then(gameInfo => {
        const totalPlayerAmount = gameInfo.numberOfPlayers;
        const readyPlayerAmount = gameInfo.players.length;

        const selectorTotal = document.querySelector("p#joined span:last-of-type");
        selectorTotal.innerHTML = "";
        selectorTotal.insertAdjacentHTML('beforeend', totalPlayerAmount);

        const selectorReadyPlayers = document.querySelector("p#joined span:first-of-type");
        selectorReadyPlayers.innerHTML = "";
        selectorReadyPlayers.insertAdjacentHTML('beforeend', readyPlayerAmount);
    }).catch(errorHandler);
}
