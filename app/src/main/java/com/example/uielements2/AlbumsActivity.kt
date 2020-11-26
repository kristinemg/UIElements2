package com.example.uielements2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageButton

class AlbumsActivity : AppCompatActivity() {
    var titles = arrayOf("Lover", "Memories... Do Not Open" , "The Greatest Showman")
    var images = intArrayOf(R.drawable.lover, R.drawable.coldplay, R.drawable.tgsm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)

        val gridView = findViewById<GridView>(R.id.gridView)
        val mainAdapter = MainAdapter(this, titles, images)
        gridView.adapter = mainAdapter
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, id ->
            val intent = Intent(this, AlbumDetails::class.java)
            intent.putExtra("position", titles[position])
            startActivity(intent)
        }
    }
}