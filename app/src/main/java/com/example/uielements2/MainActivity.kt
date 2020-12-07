package com.example.uielements2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.uielements2.models.Song
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    var songsArray : ArrayList<String> = ArrayList()
    lateinit var songsListView : ListView
    lateinit var songsTableHandler: SongsTableHandler
    lateinit var songs : MutableList<Song>
    lateinit var adapter: ArrayAdapter<String>
    lateinit var titles : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        songsTableHandler = SongsTableHandler(this)
        songs = songsTableHandler.read()
        titles = songsTableHandler.getTitles()

        var songList = arrayOf("The Greatest Show", "A Million Dreams", "A Million Dreams - Reprise", "Come Alive", "The Other Side",
            "Never Enough", "This Is Me", "Rewrite The Stars", "Tightrope", "Never Enough - Reprise", "From Now On", "The One",
            "Break Up Every Night", "Bloodstream", "Don't Say", "Something Just Like This", "My Type", "It Won't Kill Ya", "Paris",
            "Honest", "Wake Up Alone", "Young", "Last Day Alive", "Big Plans", "8 Letters", "Cruel Summer", "Lover", "The Man", "I Think He Knows", "Miss Americana & The Heartbreak Prince",
            "Paper Rings", "Cornelia Street", "London Boy", "ME!", "Daylight")

        for(song in songList){
            songsArray.add(song)
        }
        for(song in titles){
            songsArray.add(song)
        }

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songsArray)
        val songsListView = findViewById<ListView>(R.id.songsList)
        songsListView.adapter = adapter

        val songs = findViewById<ListView>(R.id.songsList)
        registerForContextMenu(songs)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.add_to_queue, menu)

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.go_to_songs -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.go_to_albums ->{
                startActivity(Intent(this, AlbumsActivity::class.java))
                true
            }
            R.id.go_to_queues -> {
                val intent = Intent(this, QueueActivity::class.java)
                startActivity(intent)
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    val loverList = ArrayList<String>()
    val tcList = ArrayList<String>()
    val tgsmList = ArrayList<String>()
    val songList = ArrayList<String>()
    var index = -1

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        return when (item.itemId){
            R.id.addSongToQueue -> {
                val songsList = findViewById<ListView>(R.id.songsList)
                val snackbar: Snackbar = Snackbar.make(songsList , "Song added to queue." , Snackbar.LENGTH_SHORT)
                snackbar.setAction("Go To Song Queue" , View.OnClickListener {
                    startActivity(Intent(applicationContext , QueueActivity::class.java))
                })
                snackbar.show()
                val info = item.getMenuInfo() as AdapterView.AdapterContextMenuInfo
                queuedSongs.add(songsArray[info.position])
                true
            }
            R.id.edit_song -> {
                val song_id = songs[info.position-23].id
                val intent = Intent(applicationContext, EditSongActivity::class.java)
                intent.putExtra("song_id",song_id)
                startActivity(intent)
                true
            }
            R.id.delete_song -> {
                val song = songs[info.position-23]
                if(songsTableHandler.delete(song)){
                    songsArray.removeAt(info.position)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(applicationContext, "Song was deleted.", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.add_to_album -> {
                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                index = info.position

                true
            }
            R.id.lover -> {
                loverList.add(songList[index])
                true
            }
            R.id.tcAlbum -> {
                tcList.add(songList[index])
                true
            }
            R.id.tgsmAlbum -> {
                tgsmList.add(songList[index])
                true
            }

            else -> super.onContextItemSelected(item)
        }
    }


}

var queuedSongs = ArrayList<String>()
