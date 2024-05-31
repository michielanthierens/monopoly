"use strict";

function initEndScreen() {
    const gameId = loadFromStorage("gameID");
    setToken(loadFromStorage("token"));
    fetchFromServer(`/games/${gameId}`, 'GET').then(game => {
        const winner = game.winner;
        if (winner !== null) {
            document.querySelector("h2").innerHTML = winner + " has won!";
            loadImage(winner);
            loadPlayerResultList(winner, game.players);
        }
    }).catch(errorHandler);

}

function loadImage(winner) {
    loadFromStorage("players_and_pawns").forEach(player => {
        if (player.playerName === winner) {
            document.querySelector("main").insertAdjacentHTML("afterbegin", `
            <img src="../images/winning-images/${player.pawn}_wins.png" alt="${player.pawn} wins">`)
        }
    });
}

function loadPlayerResultList(winner, players) {
    const listDomLocation = document.querySelector("ul");

    players.forEach(player => {
        const playerResultListHtml = `<li><p>${player.name}</p><div><p>xp: ${player.money}</p><p>amount of properties: ${player.properties.length}</p><p>score: ${player.totalNetWorth}</p></div></li>`;
        if (player.name === winner) {
            listDomLocation.insertAdjacentHTML("afterbegin", playerResultListHtml);
        } else {
            listDomLocation.insertAdjacentHTML("beforeend", playerResultListHtml);
        }
    });
}
