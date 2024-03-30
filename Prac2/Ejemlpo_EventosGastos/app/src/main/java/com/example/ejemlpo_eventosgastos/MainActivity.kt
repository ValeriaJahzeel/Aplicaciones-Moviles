package com.example.ejemlpo_eventosgastos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView


class MainActivity:AppCompatActivity() {
    private var touchCount = 0
    private lateinit var touchCountTextView: TextView
    private lateinit var gestureMessageTextView: TextView
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        touchCountTextView = findViewById(R.id.touchCountTextView)
        gestureMessageTextView = findViewById(R.id.gestureMessageTextView)

        //Iniclializar detector de gestos
        gestureDetector = GestureDetector(this, GestureListener())
    }
    // Manejar eventos de toque
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event:MotionEvent):Boolean {
        gestureDetector.onTouchEvent(event)

        // Contar los toques
        touchCount++
        touchCountTextView.text = "Toques: $touchCount"

        return super.onTouchEvent(event)
    }
    // Clase interna para detectar gestos de deslizamiento hacia arriba }
    private inner class GestureListener:GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1:MotionEvent?,
            e2:MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            // Detectar un gesto de deslizamiento hacia arriba
            if (e1 != null && e2 != null && e1.y > e2.y) {
                gestureMessageTextView.text = "¡Gesto de deslizamiento hacia arriba detectado!"
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }
}