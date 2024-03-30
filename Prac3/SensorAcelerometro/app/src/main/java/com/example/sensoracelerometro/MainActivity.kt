package com.example.sensoracelerometro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private lateinit var sensorManager: SensorManager
    private lateinit var acelerometroSensor: Sensor
    private lateinit var texto : TextView

    private var sensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent?) {
            if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                if (y > 9.0)
                    texto.text = "Movimiento hacia arriba"
                else if (y < -9.0)
                    texto.text = "Movimiento hacia abajo"
                else if (x > 9.0)
                    texto.text = "Movimiento hacia la derecha"
                else if (x < -9.0)
                    texto.text = "Movimiento hacia la izquierda"
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        texto = findViewById(R.id.texto)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        acelerometroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!

        if (acelerometroSensor == null) {
            texto.text = "Error en utilizar el acelerometro"
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
            sensorEventListener,
            acelerometroSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(sensorEventListener)
    }
}
