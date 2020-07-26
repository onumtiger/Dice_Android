package com.example.dice

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView



class PlayersListAdapter (val mCtx: Context, val layoutResId: Int, val players: List<Player>, val sharedPrefs: SharedPreferences) : ArrayAdapter<Player>(mCtx, layoutResId, players) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // View elements
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)
        val playerNameView = view.findViewById<TextView>(R.id.player_name)
        val deleteButton = view.findViewById<ImageButton>(R.id.friend_delete)

        playerNameView.text = players[position].playerName

        /**
         * Delete player from shared prefs
         */
        deleteButton.setOnClickListener{
            sharedPrefs.edit().remove(players[position].playerNum.toString()).commit()

            val intent = Intent(context, LandingActivity::class.java);
            intent.putExtra("numPlayers", sharedPrefs.getString("numPlayers", "1"))
            intent.putExtra("delete", position)
            mCtx.startActivity(intent);
        }
        return view
    }
}