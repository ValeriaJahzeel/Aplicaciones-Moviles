package com.example.ejemploservicios

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var backgroundMusicService: BackgroundMusicService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as BackgroundMusicService.LocalBinder
            backgroundMusicService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(className: ComponentName) {
                isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playButton: ImageButton = findViewById(R.id.btnStart)
        val pauseButton: ImageButton = findViewById(R.id.btnPause)
        val stopButton: ImageButton= findViewById(R.id.btnStop)

        playButton.setOnClickListener {
            if(isBound) {
                backgroundMusicService?.startMusic(this)
            }
        }

        pauseButton.setOnClickListener {
            if(isBound) {
                backgroundMusicService?.pauseMusic(this)
            }
        }

        stopButton.setOnClickListener {
            if(isBound) {
                backgroundMusicService?.stopMusic(this)
            }
        }
        val intent = Intent(this, BackgroundMusicService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}

class BackgroundMusicService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    private val binder: IBinder = LocalBinder()

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.cancion)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer?.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }


    fun startMusic(context: Context) {
        mediaPlayer?.start()
        Toast.makeText(context, "Reproduciendo...", Toast.LENGTH_SHORT).show()
    }

    fun pauseMusic(context: Context) {
        mediaPlayer?.pause()
        Toast.makeText(context, "En pausa...", Toast.LENGTH_SHORT).show()
    }

    fun stopMusic(context: Context) {
        mediaPlayer!!.stop()
        mediaPlayer!!.prepareAsync()
        Toast.makeText(context, "Parando...", Toast.LENGTH_SHORT).show()
    }

    inner class LocalBinder : Binder() {
        fun getService(): BackgroundMusicService {
            return this@BackgroundMusicService
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }
}