"use strict";

function rollDiceInit() {
    displayThrowButton();
    displayActions();
    const button = document.querySelector("button.rollDice");
    button.addEventListener('click', rollDice);
}

function displayThrowButton() {
    document.querySelector("button.rollDice").classList.add("hidden");
    setToken(loadFromStorage("token"));

    const username = loadFromStorage("username");
    const gameID = loadFromStorage("gameID");
    fetchFromServer(`/games/${gameID}`, 'GET').then(gameInfo => {
        if (gameInfo.canRoll === true && gameInfo.currentPlayer === username) {
            document.querySelector("button.rollDice").classList.remove("hidden");
        }
    }).catch(errorHandler);
}

function rollDice(e) {
    e.preventDefault();
    const gameID = loadFromStorage("gameID");
    const playername = loadFromStorage("username");
    setToken(loadFromStorage("token"));
    fetchFromServer(`/games/${gameID}/players/${playername}/dice`, 'POST').then(gameInfo => {
        const rolledDice = gameInfo.lastRolledDice;

        const div = document.querySelector("#dices");
        div.innerHTML = "";

        rolledDice.forEach(dice => {
            displayRollDiceScreen(dice, gameInfo);
        });
    }).catch(err => console.log(err));

}

function displayRollDiceScreen(dice, gameInfo) {
    const popUpDiv = document.querySelector("div#rollDiceScreen");
    popUpDiv.classList.remove("hidden");

    const diceImage = `<img class="dice" src="../images/dice/Dice-${dice}.png" alt="${dice}">`;
    popUpDiv.insertAdjacentHTML('beforeend', diceImage);

    const dicesEl = document.querySelector("#dices");
    dicesEl.insertAdjacentHTML('beforeend', diceImage);

    setTimeout(removeDiceImg, 1000);
    showFeedback(gameInfo);
}

function removeDiceImg() {
    const imgs = document.querySelectorAll("#rollDiceScreen .dice");
    imgs.forEach(img => img.classList.add("hidden"));
}

function showFeedback(gameInfo) {

    const popUpDiv = document.querySelector("#rollDiceScreen");

    const gameTurns = gameInfo.turns;
    const moves = gameTurns[gameTurns.length - 1].moves;

    popUpDiv.innerHTML = "";

    popUpDiv.insertAdjacentHTML('beforeend', `<h3>${moves.tile}</h3>`);
    popUpDiv.insertAdjacentHTML('beforeend', `<p>${moves.description}</p>`);
    setTimeout(removePopUpDiv, 2000);
}

function removePopUpDiv() {
    const popUpDiv = document.querySelector("#rollDiceScreen");
    popUpDiv.classList.add("hidden");
}
