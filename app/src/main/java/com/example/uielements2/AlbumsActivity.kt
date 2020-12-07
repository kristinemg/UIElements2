package com.example.uielements2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageButton
import com.example.uielements2.models.Album

class AlbumsActivity : AppCompatActivity() {
    var modal = ArrayList<Modal>()
    var titlesArray : ArrayList<String> = ArrayList()
    var titles = arrayOf("Lover", "Memories... Do Not Open" , "The Greatest Showman")
    var images = intArrayOf(R.drawable.lover, R.drawable.coldplay, R.drawable.tgsm)

    lateinit var albumsTableHandler: AlbumsTableHandler
    lateinit var albums : MutableList<Album>
    lateinit var album_titles : MutableList<String>
    lateinit var gridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        albumsTableHandler = AlbumsTableHandler(this)
        albums = albumsTableHandler.read()
        album_titles = albumsTableHandler.getTitles()

        for(i in titles){
            titlesArray.add(i)
        }
        for(i in album_titles){
            titlesArray.add(i)
        }
        for(i in images.indices){
            modal.add(Modal(titlesArray[i],images[i]))
        }
        for(i in titlesArray.indices){
            if(i>3){
                modal.add(Modal(titlesArray[i], images[3]))
            }
        }
        val gridView = findViewById<GridView>(R.id.gridView)
        var adapter = CustomAdapter(modal, this)
        gridView.adapter = adapter
        adapter.notifyDataSetChanged()

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, id ->
            val intent = Intent(this, AlbumDetails::class.java)
            intent.putExtra("position", titles[position])
            startActivity(intent)
        }
        registerForContextMenu(gridView)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.go_to_queues -> {
                val intent = Intent(this, QueueActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.go_to_songs -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.go_to_albums -> {
                startActivity(Intent(this, AlbumsActivity::class.java))
                true
            }
            R.id.add_song -> {
                startActivity(Intent(this, AddSongsActivity::class.java))
                true
            }
            R.id.add_album -> {
                startActivity(Intent(this, CreateAlbumActivity::class.java))
                true
            }
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.album_edit_menu, menu)

    }override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.edit_album -> {
                val album_id = albums[info.position-3].id
                val intent = Intent(this,EditAlbumActivity::class.java)
                intent.putExtra("album_id",album_id)
                startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}