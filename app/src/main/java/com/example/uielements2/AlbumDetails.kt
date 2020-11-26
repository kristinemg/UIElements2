package com.example.uielements2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class AlbumDetails : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_lbum_details)

        val viewName = findViewById<TextView>(R.id.albumTitle)
        val viewImage = findViewById<ImageView>(R.id.albumImage)

        var albumSongs :Array<String> =arrayOf()
        val position = intent.extras!!.getString("position")

        if (position.equals("Lover")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.lover)
            albumSongs = arrayOf("Cruel Summer", "Lover", "The Man", "I Think He Knows", "Miss Americana & The Heartbreak Prince",
                "Paper Rings", "Cornelia Street", "London Boy", "ME!", "Daylight")
        }
        else if (position.equals("Memories... Do Not Open")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.coldplay)
            albumSongs = arrayOf("From Now On", "The One",
                "Break Up Every Night", "Bloodstream", "Don't Say", "Something Just Like This", "My Type", "It Won't Kill Ya", "Paris",
                "Honest", "Wake Up Alone", "Young")
        }
        else if (position.equals("The Greatest Showman")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.tgsm)
            albumSongs = arrayOf("The Greatest Show", "A Million Dreams", "A Million Dreams - Reprise", "Come Alive", "The Other Side",
                "Never Enough", "This Is Me", "Rewrite The Stars", "Tightrope", "Never Enough - Reprise", "From Now On")
        }
        var adapter = ArrayAdapter(this , android.R.layout.simple_list_item_1, albumSongs)
        var albumDetailListView = findViewById<ListView>(R.id.songList)
        albumDetailListView.adapter = adapter
    }
}
