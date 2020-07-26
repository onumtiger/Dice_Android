package com.example.dice

class Player {
    var playerName: String = ""
    var playerNum: Int = 0

    constructor(playerName: String?, playerNum: Int) {
        if (playerName != null) {
            this.playerName = playerName
        }
        this.playerNum = playerNum
    }

    constructor() {}
}