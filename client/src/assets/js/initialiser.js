"use strict";
let _token = null;

document.addEventListener('DOMContentLoaded', init);

function init() {
    if(window.location.pathname.includes("index.html")){
        homeScreenInit();
    }
    else if (window.location.pathname.includes("lobbies.html")) {
        lobbiesInit();
    }
    else if (window.location.pathname.includes("waiting_screen.html")) {
        waitingScreenInit();
    }
    else if (window.location.pathname.includes("gameboard.html")) {
        initGameBoard();
        rollDiceInit();
        propertyInit();
        saveToStorage("players_and_pawns", _config.playersAndPawns);
    }
    else if(window.location.pathname.includes("end_screen.html")){
        initEndScreen();
    }
}

function setToken(token) {
    _token = token;
}
