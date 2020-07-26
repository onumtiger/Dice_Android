package com.example.dice

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.landing)

        // View elements
        val playersListView = findViewById<ListView>(R.id.player)
        val addButton: Button = findViewById(R.id.addPlayer)
        val playerInput : EditText = findViewById(R.id.playerInput)
        val startButton: Button = findViewById(R.id.startGame)
        val sharedPrefs: SharedPreferences = getSharedPreferences("myAppPackage", 0)

        // Variables
        var playerNum: Int = sharedPrefs.getString("numPlayers", "0")!!.toInt()
        var playersList = mutableListOf<Player>()

        /**
         * get all players from shared prefs
         */
        if (playerNum > 0) {
            for(j in 0..playerNum){
                var name = sharedPrefs.getString(j.toString(), "-1")
                if(name != "-1") {
                    playersList.add(Player(name, j))
                }
            }
        }
        else {
            sharedPrefs.edit().clear().apply()
        }

        /**
         * Display all added players
         */
        val adapter =
            PlayersListAdapter(
                applicationContext,
                R.layout.player_list_item,
                playersList,
                sharedPrefs
            )
        playersListView.adapter = adapter

        /**
         * Add player
         */
        addButton.setOnClickListener() {
            var playername = playerInput.text.toString()
            if (playername.isEmpty()){
                Toast.makeText(this, "Please write a name", Toast.LENGTH_SHORT)
                    .show()
            }
            else {
                playerNum = saveUsername(playerNum, playername, playersList, sharedPrefs)
                clearInput(playerInput)
                adapter.notifyDataSetChanged();
            }
        }

        /**
         * Start game
         */
        startButton.setOnClickListener() {
            val game = Intent(this, DiceActivity::class.java)
            game.putExtra("playerNumber", playersList.size)
            startActivity(game)
        }
    }

    /**
     * save username to shared preferences
     */
    private fun saveUsername(playerNum: Int, username: String?, playersList: MutableList<Player>, prefs: SharedPreferences): Int {
        val newPlayer= Player(username, playerNum)
        playersList.add(newPlayer)

        while(prefs.getString(newPlayer.playerNum.toString(), "-1") != "-1"){
            newPlayer.playerNum++
        }
        prefs.edit().putString("numPlayers", newPlayer.playerNum.toString()).apply()
        prefs.edit().putString(newPlayer.playerNum.toString(), newPlayer.playerName).apply()

        return playerNum + 1
    }

    /**
     * Clear the input bar after adding a player
     */
    private fun clearInput(playerInput: EditText){
        playerInput.setText("")
        playerInput.hint = "Name"
    }
}
