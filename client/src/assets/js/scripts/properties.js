"use strict";

function propertyInit() {
    document.querySelector("div.buttons div.actions button").addEventListener('click', buyProperty);
    document.querySelector("#dontBuyProperty").addEventListener("click", endTurn);
}

function buyProperty(e) {
    e.preventDefault();
    const playerName = loadFromStorage("username");
    const gameId = loadFromStorage("gameID");
    let tile = "";
    _config.playersAndPawns.forEach(playerAndPawn => {
        if (playerAndPawn.playerName === playerName) {
            tile = playerAndPawn.currentTile;
        }
    });

    const replacedTile = tile.split(" ").join("_");

    setToken(loadFromStorage("token"));
    fetchFromServer(`/games/${gameId}/players/${playerName}/properties/${replacedTile}`, 'POST').then(purchaseInfo => {
        if (purchaseInfo.purchased) {
            const popUpDiv = document.querySelector("#rollDiceScreen");
            popUpDiv.innerHTML = " ";
            popUpDiv.classList.remove("hidden");
            const html = `<h3>you've bought ${replacedTile} !</h3>`;
            popUpDiv.insertAdjacentHTML('beforeend', html);
            setTimeout(hidePopUp, 1500);
        }
    }).catch(() => {
        const popUpDiv = document.querySelector("#rollDiceScreen");
        popUpDiv.innerHTML = " ";
        popUpDiv.classList.remove("hidden");

        const html = `<h3>ERROR! you CAN'T BUY ${replacedTile} !</h3>
                        <p>You can only buy streets, stations and utility tiles! Make sure the tile hasn't been bought already by another player!</p>`;
        popUpDiv.insertAdjacentHTML('beforeend', html);
        setTimeout(hidePopUp, 3000);
    });
}

function hidePopUp() {
    const popUpDiv = document.querySelector("#rollDiceScreen");
    popUpDiv.classList.add("hidden");
}

function endTurn(e) {
    e.preventDefault();

    let property;
    const gameId = loadFromStorage("gameID");
    const playerName = loadFromStorage("username");
    setToken(loadFromStorage("token"));

    _config.playersAndPawns.forEach(player => {
        if (player.playerName === playerName) {
            property = player.currentTile;
        }
    });


    fetchFromServer(`/games/${gameId}/players/${playerName}/properties/${property}`, 'DELETE')
        .catch(errorHandler);
}

function buyHouse(e) {
    e.preventDefault();
    const playerName = loadFromStorage("username");
    const gameId = loadFromStorage("gameID");
    const propertyName = e.target.classList[0];

    fetchFromServer(`/games/${gameId}/players/${playerName}/properties/${propertyName}/houses`, 'POST');
}

function sellHouse(e) {
    e.preventDefault();

    const playerName = loadFromStorage("username");
    const gameId = loadFromStorage("gameID");
    const propertyName = e.target.classList[0];

    fetchFromServer(`/games/${gameId}/players/${playerName}/properties/${propertyName}/houses`, 'DELETE');
}

function displayRightAmountOfHouses() {
    const gameId = loadFromStorage("gameID");

    fetchFromServer(`/games/${gameId}`, "GET").then(gameInfo => {
        gameInfo.players.forEach(player => {
            player.properties.forEach(property => {
                const propertyName = property.name;
                const amountOfHouses = property.amountOfHouses;

                document.querySelectorAll(".grid-item div").forEach(propertyBox => {
                    if (propertyBox.innerHTML === propertyName) {
                        propertyBox.setAttribute("id", `house${amountOfHouses}`);
                    }
                });
            });
        });
    });
}