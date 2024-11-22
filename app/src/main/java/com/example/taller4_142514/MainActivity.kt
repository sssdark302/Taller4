package com.example.taller4_142514

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.Button
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val greetingTextView = findViewById<TextView>(R.id.greetingTextView)
        val buttonStart = findViewById<Button>(R.id.buttonStart)

        // Cambiar el saludo según la hora del día
        val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greeting = when {
            currentTime in 0..11 -> "Buenos días"
            currentTime in 12..17 -> "Buenas tardes"
            else -> "Buenas noches"
        }
        greetingTextView.text = greeting

        //Navegar a la principal
        buttonStart.setOnClickListener {
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
        }
    }
}