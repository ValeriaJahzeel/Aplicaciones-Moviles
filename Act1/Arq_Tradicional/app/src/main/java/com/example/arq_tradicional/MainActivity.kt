package com.example.arq_tradicional

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var btnContador: Button
    lateinit var txtContador: TextView
    var cont = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnContador = findViewById(R.id.btnContador)
        txtContador = findViewById(R.id.txtContador)

        btnContador.setOnClickListener {
            cont++
            txtContador.text = "$cont"
        }
    }


}