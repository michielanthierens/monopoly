"use strict";

function lobbiesInit() {
    getGames();
    document.querySelector("button#submit_search_gameid").addEventListener('click', function (e) {
        searchInGames(e);
    });
    document.querySelector("button#join-game").addEventListener('click', function (e) {
        selectGame(e);
    });
    document.querySelector("button#create-new-game").addEventListener('click', function (e) {
        createNewGame(e);
    });
}

function selectGame(e) {
    e.preventDefault();
    const selector = document.querySelector("#available-games");
    const selectedGameID = selector.options[selector.selectedIndex].value; //geeft de geselecteerde ID van game terug

    saveToStorage("gameID", selectedGameID);
    window.location.href = "waiting_screen.html";
}

function createNewGame(e) {
    e.preventDefault();
    const numberOfPlayers = loadFromStorage("playerAmount");
    const requestBody = {
        "prefix": _config.prefix,
        "numberOfPlayers": parseInt(numberOfPlayers)
    };
    fetchFromServer('/games', 'POST', requestBody).then(gameInfo => {
        saveToStorage("gameID", gameInfo.id);
        window.location.href = "waiting_screen.html";
    });
}

function getGames() {
    const numberOfPlayers = loadFromStorage("playerAmount");

    fetchFromServer(`/games?started=false&numberOfPlayers=${numberOfPlayers}&prefix=${_config.prefix}`, 'GET').then(result => {
        loadGames(result);
    }).catch(errorHandler);
}

function loadGames(games) {
    const options = document.querySelector("select#available-games");
    options.innerHTML = "";
    for (const game of games) {
        options.insertAdjacentHTML('beforeend',`<option class="gameOption">${game.id}</option>`);
    }
}

function searchInGames(e) {
    e.preventDefault();

    const numberOfPlayers = loadFromStorage("playerAmount");
    fetchFromServer(`/games?started=false&numberOfPlayers=${numberOfPlayers}&prefix=${_config.prefix}`, 'GET').then(games => {
        const searchID = document.querySelector("input#search_for_gameid").value;
        const matchingGames = [];
        for (const game of games) {
            if (game.id.includes(searchID)) {
                matchingGames.push(game);
            }
        }
        loadGames(matchingGames);
    });
}
