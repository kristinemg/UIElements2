package com.example.uielements2

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

class AlbumDetails : AppCompatActivity() {

    lateinit var adapter : ArrayAdapter<String>
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "i.apps.notifications"
    private val description = "Test notification"

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_lbum_details)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val viewName = findViewById<TextView>(R.id.albumTitle)
        val viewImage = findViewById<ImageView>(R.id.albumImage)

        val position = intent.extras!!.getString("position")

        if (position.equals("Lover")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.lover)
            queuedSongs = arrayListOf("Cruel Summer", "Lover", "The Man", "I Think He Knows", "Miss Americana & The Heartbreak Prince",
                "Paper Rings", "Cornelia Street", "London Boy", "ME!", "Daylight")
        }
        else if (position.equals("Memories... Do Not Open")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.coldplay)
            queuedSongs = arrayListOf("From Now On", "The One",
                "Break Up Every Night", "Bloodstream", "Don't Say", "Something Just Like This", "My Type", "It Won't Kill Ya", "Paris",
                "Honest", "Wake Up Alone", "Young")
        }
        else if (position.equals("The Greatest Showman")){
            viewName.text = position
            viewImage.setImageResource(R.drawable.tgsm)
            queuedSongs = arrayListOf("The Greatest Show", "A Million Dreams", "A Million Dreams - Reprise", "Come Alive", "The Other Side",
                "Never Enough", "This Is Me", "Rewrite The Stars", "Tightrope", "Never Enough - Reprise", "From Now On")
        }
        adapter = ArrayAdapter(this , android.R.layout.simple_list_item_1, queuedSongs)
        var albumDetailListView = findViewById<ListView>(R.id.songList)
        albumDetailListView.adapter = adapter
        registerForContextMenu(albumDetailListView)
    }
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.remove_from_album, menu)
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = intent.extras!!.getString("position")
        return when(item.itemId){
            R.id.remove_song ->  {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Delete this song from the $position album?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", { _ , _ ->


                            val toast: Toast = Toast.makeText(applicationContext, "Song removed from the $position album.", Toast.LENGTH_SHORT)
                            toast.show()

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationChannel = NotificationChannel(
                                        channelId , description , NotificationManager.IMPORTANCE_HIGH)
                                notificationChannel.enableLights(true)
                                notificationChannel.lightColor = Color.BLUE
                                notificationChannel.enableVibration(true)
                                notificationManager.createNotificationChannel(notificationChannel)

                                builder = Notification.Builder(this , channelId)
                                        .setContentTitle("Song Removed")
                                        .setContentText("You deleted a song from the $position album.")
                                        .setSmallIcon(R.drawable.ic_launcher_background)

                            }else{
                                builder = Notification.Builder(this)
                                        .setContentTitle("Song removed.")
                                        .setContentText("You deleted a song from the $position album.")
                                        .setSmallIcon(R.drawable.ic_launcher_background)
                            }
                            notificationManager.notify(1234, builder.build())
                            adapter.notifyDataSetChanged() // REMOVE SONG AFTER CLICKING YES
                        }).setNegativeButton("No", { dialog , _ ->
                            dialog.cancel()
                        })

                val alert = dialogBuilder.create()
                alert.setTitle("Notification Manager")
                alert.show()

                val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                queuedSongs.removeAt(info.position)
                true
            }
            else -> super.onContextItemSelected(item)
        }
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
            else ->{
                super.onOptionsItemSelected(item)
            }
        }
    }
}
