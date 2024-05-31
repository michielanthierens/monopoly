"use strict";

function initGameBoard() {
    setInterval(makeIntervalFunctions, 1500);
    setInterval(rollDiceInit, 3000);
    setInterval(displayRightAmountOfHouses, 2000);
    //fetches tiles from server
    fetchFromServer('/tiles', 'GET').then(tiles => apiData(tiles)).catch(errorHandler);
    loadPlayerList();
    // na elke aankoop of na elke tik updaten van properties
    addEventHandlers();
    displayActions();
}

function addEventHandlers() {
    document.querySelector("img.leaveImg").addEventListener("click", clickSettingsButton);
    document.querySelectorAll(".optionsCardsButton").forEach(button => {
        button.addEventListener("click", () => toggleOptionsOfCards(button));
    });

    document.querySelector("#stopGameButton").addEventListener("click", () => {
        document.querySelector(".sure").classList.remove("hidden");
        document.querySelector("#leaveOptions").classList.add("hidden");
    });

    document.querySelector(".no").addEventListener("click", () => {
        document.querySelector(".sure").classList.add("hidden");
    });

    document.querySelector(".yes").addEventListener("click", declareBankruptcy);
}

function makeIntervalFunctions() {
    checkIfGameEnded();
    changePlace();
    reloadMoney();
}

function clickSettingsButton() {
    const domLocation = document.querySelector(".infoSettings");
    // als de client toont are u sure? dan verdwijnen alle opties als je op de img klikt
    if (!domLocation.querySelector(".sure").classList.contains("hidden")) {
        domLocation.querySelector(".sure").classList.add("hidden");
        domLocation.querySelector("#leaveOptions").classList.add("hidden");
        // als de client dat niet toont, geef je de algemene opties
    } else domLocation.querySelector("#leaveOptions").classList.toggle("hidden");

}

// functies display bord + bord-data
function apiData(data) {
    //makes the right path
    const rightPath = getRightPath(data);

    //displays color and names of each item with data from api
    displayColor(rightPath);
}

function displayColor(data) {

    const items = document.querySelectorAll(".grid-item");

    for (let i = 0; i < data.length; i++) {

        if (data[i].color) {
            const div = document.createElement("div");
            div.classList.add(data[i].color);
            div.classList.add("gameboardHeader");
            div.textContent = data[i].name;
            items[i].appendChild(div);

        } else {
            items[i].textContent = data[i].name;
        }
    }
}

function changePlace() {
    const gameId = loadFromStorage("gameID");

    fetchFromServer(`/games/${gameId}`, 'GET').then(game => {
        game.players.forEach(player => {
            _config.playersAndPawns.forEach(playerPawn => {
                if (playerPawn.playerName === player.name) {
                    playerPawn["currentTile"] = player.currentTile;
                }
            });
        });
        removePlace();
        setPlace();

    }).catch(errorHandler);
}

function removePlace() {
    document.querySelectorAll(".grid-item .player").forEach(player => {
        player.outerHTML = "";
    })
}

function setPlace() {
    const items = document.querySelectorAll(".grid-item");

    fetchFromServer(`/tiles`, 'GET').then(tiles => {
        const tilesInRightOrder = getRightPath(tiles);

        for (let i = 0; i < tilesInRightOrder.length; i++) {
            for (let j = 0; j < _config.playersAndPawns.length; j++) {
                if (tilesInRightOrder[i].name === _config.playersAndPawns[j].currentTile) {
                    const pawn = _config.playersAndPawns[j].pawn;
                    const player = _config.playersAndPawns[j].playerName;
                    const pawnDiv = createPawn(pawn, j + 1, player);
                    items[i].appendChild(pawnDiv);

                    saveToStorage("currentTile", player.currentTile);
                }
            }
        }

    }).catch(errorHandler);
}


function createPawn(sortPawn, orderNumber, playerName) {
    const div = document.createElement("div");
    div.classList.add(sortPawn);
    div.classList.add(playerName);
    div.classList.add("player");

    const pawn = document.createElement("img");
    pawn.setAttribute("src", `../images/pawns/${sortPawn}.jpg`);
    pawn.setAttribute("alt", "pawn");
    pawn.setAttribute("title", playerName);
    pawn.classList.add(convertOrderNumberToText(orderNumber));

    div.appendChild(pawn);

    return div;
}

function convertOrderNumberToText(orderNumber) {
    if (orderNumber === 1) {
        return "one";
    } else if (orderNumber === 2) {
        return "two";
    } else if (orderNumber === 3) {
        return "three";
    } else if (orderNumber === 4) {
        return "four";
    }
    return "";
}

//changes order of data api so it is the right path
function getRightPath(data) {
    let rightPath = [];
    let reverse = [];

    const firstElSecondRow = 10;
    const lastElSecondRow = 19;
    const firstElFourthRow = 30;
    const lastElFourthRow = 39;
    const lastElem = 39;

    for (let i = 0; i < data.length; i++) {
        if ((i >= firstElSecondRow && i <= lastElSecondRow) || (i >= firstElFourthRow && i <= lastElFourthRow)) {
            reverse.unshift(data[i]);//add item to beginning of array
            if (i === lastElem || i === lastElSecondRow) {
                rightPath = rightPath.concat(reverse);
                reverse = [];
            }
        } else {
            rightPath.push(data[i]);
        }
    }

    return rightPath;
}

function checkIfGameEnded() {
    const gameId = loadFromStorage("gameID");
    setToken(loadFromStorage("token"));

    fetchFromServer(`/games/${gameId}`, 'GET').then(game => {
        if (game.ended) {

            window.location.href = "end_screen.html";
        }
    }).catch(errorHandler);
}

function playerListBuildHtml(playerName, player) {
    if (playerName === loadFromStorage("username")) {
        return `<li class="selected player_own_box ${playerName}"> <div class="players_hud">
                                <h3>${playerName}</h3>
                                <p>xp: ${player.money}</p>
                             </div>`;
    } else {
        return `<li class="${playerName}"> <div class="players_hud">
                                <h3>${playerName}</h3>
                                <p>xp: ${player.money}</p>
                             </div>`;
    }
}

function loadPlayerList() {
    setToken(loadFromStorage("token"));
    const gameID = loadFromStorage("gameID");

    fetchFromServer(`/games/${gameID}`, 'GET').then(gameInfo => {
        const players = gameInfo.players;
        for (const player of players) {
            const playerName = player.name;
            let html = ""
            html = playerListBuildHtml(playerName, player);
            if (playerName === loadFromStorage("username")) {
                addPlayerAndPawnToStorage(playerName, loadFromStorage("pawn"));
                html += `<img src="../images/pawns/${loadFromStorage("pawn")}.jpg" alt="player_pawn" /> </li>`;
            } else {
                const randomIndex = Math.floor(Math.random() * _config.pawns.length);
                addPlayerAndPawnToStorage(playerName, _config.pawns[randomIndex]);
                html += `<img src="../images/pawns/${_config.pawns[randomIndex]}.jpg" alt="player_pawn" /> </li>`;
            }
            document.querySelector("#player_hud_list").insertAdjacentHTML("beforeend", html);
        }
        addEventHandlersToPlayers();
    });
}

function addPlayerAndPawnToStorage(username, pawn) {
    _config.playersAndPawns.push({
        playerName: username,
        pawn: pawn
    });
    saveToStorage("players_and_pawns", _config.playersAndPawns)
}

function addEventHandlersToPlayers() {
    const playerList = document.querySelectorAll("#player_hud_list li");

    playerList.forEach(function (item) {
        item.addEventListener("click", clickOnPlayer);
    });
}

function clickOnPlayer(e) {
    const playerList = document.querySelectorAll("#player_hud_list li");
    playerList.forEach(function (item) {
        item.classList.remove("selected");
    });
    e.target.classList.add("selected");
    showProperties();
}

function showProperties() {
    const activePlayer = document.querySelector("#player_hud_list li.selected h3").innerHTML;
    const gameID = loadFromStorage("gameID");
    fetchFromServer(`/games/${gameID}`, 'GET').then(response => {
        response.players.forEach(player => {
            if (player.name === activePlayer) {
                clearProperties();
                addPropertiesToPossessions(player);
                addGetOutOfJailCardsToPossessions(player);
            }
        })
    });
}

function clearProperties() {
    document.querySelectorAll("#possessions ul").forEach(ul => {
        ul.innerHTML = "";
    });
}

function addPropertiesToPossessions(player) {
    const cardsLocation = document.querySelector("#possessions .cards")

    player.properties.forEach(property => {
        const nameOfPropertyAsPath = property.name.replaceAll(" ", "_");
        let colorIdOfProperty = "#card_" + property.color;
        cardsLocation.querySelector(colorIdOfProperty).insertAdjacentHTML("beforeend", `
        <li>
            <h3>${property.name}</h3>
            <p class="">type: ${property.type}</p>
            <input type="button" value="Options" class="optionsCardsButton">
            <ul class="card_options hidden">
                <li><input type="button" value="buy house" class="${nameOfPropertyAsPath} buyHouse"></li>
                <li><input type="button" value="sell house" class="${nameOfPropertyAsPath} sellHouse"></li>
            </ul>
        </li>
        `)
    })
    document.querySelectorAll(".optionsCardsButton").forEach(button => {
        button.addEventListener('click', () => {
            toggleOptionsOfCards(button);

        });
    })
}

function addGetOutOfJailCardsToPossessions(player) {
    const amountOfJailCards = player.getOutOfJailFreeCards
    for (let i = 0; i < amountOfJailCards; i++) {
        document.querySelector("#possessions #get_out_of_jail_cards").insertAdjacentHTML("beforeend", `
        <li>
            <p>lucky block</p>
            <h3>Get out of jail free!</h3>
            <img src="/src/images/iron_bars.png" alt="iron_bars">
            <p>Once used, this card will disappear</p>
        </li>
        `)
    }
}

function toggleOptionsOfCards(button) {
    button.parentElement.querySelector("ul.card_options").classList.toggle("hidden");

    button.parentElement.querySelector(".buyHouse").addEventListener('click', buyHouse);
    button.parentElement.querySelector(".sellHouse").addEventListener('click', sellHouse);
}

function declareBankruptcy(e) {
    e.preventDefault();

    setToken(loadFromStorage("token"));
    const gameId = loadFromStorage("gameID");
    const name = loadFromStorage("username");

    fetchFromServer(`/games/${gameId}/players/${name}/bankruptcy`, 'POST').catch(errorHandler);
}

function displayActions(){
    document.querySelector(".actions").classList.add("hidden");
    setToken(loadFromStorage("token"));

    const username = loadFromStorage("username");
    const gameID = loadFromStorage("gameID");
    fetchFromServer(`/games/${gameID}`, 'GET').then(gameInfo => {
        if (gameInfo.currentPlayer === username) {
            document.querySelector(".actions").classList.remove("hidden");
        }
    }).catch(errorHandler);
}


function reloadMoney() {
    const gameId = loadFromStorage("gameID");

    fetchFromServer(`/games/${gameId}`, 'GET').then(game => {
        game.players.forEach(player => {
            document.querySelector(`#player_hud_list li.${player.name} p`).innerHTML = `xp: ${player.money}`;
        })
    }).catch(errorHandler);
}