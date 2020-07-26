package com.example.dice

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class DiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dice)

        // View elements
        val prefs: SharedPreferences = getSharedPreferences("myAppPackage", 0)
        val diceButton: ImageButton = findViewById(R.id.diceButton)
        val againButton: Button = findViewById(R.id.again)
        val nextTurn: TextView = findViewById(R.id.nextTurn)

        // Variables
        val min = 1
        val max = 6
//val mp: MediaPlayer = MediaPlayer.create(this, R.raw.shake_dice)
        var round = 1
        val numPlayers = intent.getIntExtra("playerNumber", 0)
        val normalDice = numPlayers <= 1

        /**
         * hide again button and text view if no players are available
         */
        if(normalDice){
            againButton.visibility = View.GONE
            nextTurn.visibility = View.GONE
        }

        /**
         * Set view text in the first round
         */
        var index = 0
        if (index == 0 && !normalDice) {
            var player = getplayer(index, prefs)
            while  (player == "0") {
                index++
                player = getplayer(index, prefs)
            }
            var nextPlayer = player + getString(R.string.next)
            nextTurn.text = nextPlayer
        }

        /**
         * Let the player roll the dice again
         */
        againButton.setOnClickListener(){
            animateDice(diceButton)
            Handler().postDelayed(
                {
                    throwDice(min, max, diceButton)
                }, 1000
            )
        }

        /**
         * Roll the dice and if there are at least two players display next player
         */
        diceButton.setOnClickListener() {
            animateDice(diceButton)
            //mp.start()

            Handler().postDelayed(
                    {
                        throwDice(min, max, diceButton)
                        if(!normalDice){
                            var nextPlayer:String
                            if(checkRound(round, numPlayers)){
                                nextPlayer = getplayer(index + 1, prefs)
                                while  (nextPlayer == "0" || nextPlayer == nextTurn.text) {
                                    index++
                                    nextPlayer = getplayer(index + 1, prefs)
                                }
                                index ++
                                round ++
                            } else {
                                index = 0
                                round = 1
                                nextPlayer = getplayer( index, prefs)
                                while  (nextPlayer == "0") {
                                    index++
                                    nextPlayer = getplayer(index, prefs)
                                }
                            }
                            nextTurn.text = nextPlayer + getString(R.string.next)
                        }
                    },
                    1000 // value in milliseconds
            )
        }
    }

    /**
     * Get current player
     */
    private fun getplayer(playerNum: Int, prefs: SharedPreferences): String {
        val player: String? = prefs.getString(playerNum.toString(), "0")

        return player.toString()
    }

    /**
     * Check that current round is not higher than amount of players
     */
    private fun checkRound(round: Int, numPlayers: Int): Boolean {
        return round < numPlayers!!
    }

    /**
     * Dice roll animation
     */
    private fun animateDice(diceButton: ImageButton){
        diceButton.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext, // Context
                R.drawable.dice3droll // Drawable
            )
        )
    }

    /**
     * Roll the dice and change picture accordingly
     */
    private fun throwDice(min: Int, max: Int, diceButton: ImageButton){
        when ((min..max).random()) {
            1 -> diceButton.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, // Context
                    R.drawable.one // Drawable
                )
            )
            2 -> diceButton.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, // Context
                    R.drawable.two // Drawable
                )
            )
            3 -> diceButton.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, // Context
                    R.drawable.three // Drawable
                )
            )
            4 -> diceButton.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, // Context
                    R.drawable.four // Drawable
                )
            )
            5 -> diceButton.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, // Context
                    R.drawable.five // Drawable
                )
            )
            6 -> diceButton.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, // Context
                    R.drawable.six // Drawable
                )
            )
        }
    }
}
