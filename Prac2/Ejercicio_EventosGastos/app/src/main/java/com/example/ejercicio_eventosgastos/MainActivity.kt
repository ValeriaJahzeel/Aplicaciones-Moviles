package com.example.ejercicio_eventosgastos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.Color
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.LinearLayout


class MainActivity : AppCompatActivity() {

    private lateinit var layout: LinearLayout
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        layout =findViewById(R.id.layout)
        gestureDetector = GestureDetector(this, SwipeGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    inner class SwipeGestureListener:GestureDetector.SimpleOnGestureListener(){
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100

        override fun onFling(
            downEvent: MotionEvent?,
            moveEvent: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val diffY = moveEvent!!.y - downEvent!!.y
            val diffX = moveEvent.x - downEvent.x

            if(Math.abs(diffX) > Math.abs(diffY)){
                if(Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD){
                    if(diffX > 0){
                        // Swipe hacia la derecha
                        layout.setBackgroundColor(Color.RED)
                    } else {
                        // Swipe hacia la izquierda
                        layout.setBackgroundColor(Color.BLUE)
                    }
                    return true
                }
            }
            return false
        }
    }
}