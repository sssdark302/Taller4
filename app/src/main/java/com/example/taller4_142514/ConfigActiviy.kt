package com.example.taller4_142514

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity

class ConfigActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        val layout = findViewById<ConstraintLayout>(R.id.configLayout)
        val redButton = findViewById<Button>(R.id.redButton)
        val blueButton = findViewById<Button>(R.id.blueButton)
        val backToHomeButton = findViewById<Button>(R.id.backToHomeButton)

        // Cambiar color de fondo
        redButton.setOnClickListener {
            layout.setBackgroundColor(Color.RED)
        }

        blueButton.setOnClickListener {
            layout.setBackgroundColor(Color.BLUE)
        }

        // Volver a la pantalla de inicio
        backToHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
