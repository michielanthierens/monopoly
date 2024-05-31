"use strict";

function homeScreenInit() {
    document.querySelector("#form-username").addEventListener("submit", usernameSubmitted);
    document.querySelector("#form-player-amount").addEventListener("submit", playerAmountSubmitted);
    document.querySelector("#form-back").addEventListener("submit", goBack);
}

function usernameSubmitted(e) {
    e.preventDefault();

    const username = document.querySelector("#username").value;

    if (username) {
        saveToStorage("username", username);

        document.querySelector("#home-screen").classList.add("hidden");
        document.querySelector("#select-players").classList.remove("hidden");
        document.querySelector("#back").classList.remove("hidden");

    }
}

function playerAmountSubmitted(e) {
    e.preventDefault();

    saveToStorage("playerAmount", e.submitter.value);

    window.location.href = "pages/lobbies.html";
}

function goBack(e) {
    e.preventDefault();

    document.querySelector("#home-screen").classList.remove("hidden");
    document.querySelector("#select-players").classList.add("hidden");
    document.querySelector("#back").classList.add("hidden");
}
