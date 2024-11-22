package com.example.taller4_142514


import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.abs

class PrincipalActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var progressBar: ProgressBar
    private lateinit var layout: ConstraintLayout
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private val handler = Handler(Looper.getMainLooper())
    private val shakeThreshold = 15.0 // Límite para detectar movimiento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        dbHelper = DatabaseHelper(this)
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        layout = findViewById(R.id.principalLayout)
        progressBar = findViewById(R.id.progressBar)

        // Inicializar SensorManager y acelerómetro
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Elementos existentes en el diseño
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val displayTextView = findViewById<TextView>(R.id.displayTextView)
        val saveToDbButton = findViewById<Button>(R.id.saveToDbButton)
        val loadFromDbButton = findViewById<Button>(R.id.loadFromDbButton)
        val clearDbButton = findViewById<Button>(R.id.clearDbButton)
        val goToConfigButton = findViewById<Button>(R.id.goToConfigButton)
        val btnStartTask = findViewById<Button>(R.id.btnStartTask)
        val btnGoToMenu = findViewById<Button>(R.id.btnGoToMenu)
        val showFragmentsButton = findViewById<Button>(R.id.showFragmentsButton)

        // Lógica original
        saveButton.setOnClickListener {
            val userName = nameEditText.text.toString()
            saveUserNameToPreferences(userName)
            displayTextView.text = userName
        }

        saveToDbButton.setOnClickListener {
            val userName = nameEditText.text.toString()
            saveUserToDatabase(userName)
        }

        loadFromDbButton.setOnClickListener {
            displayTextView.text = getUserNames().joinToString(", ")
        }

        clearDbButton.setOnClickListener {
            clearDatabase()
            displayTextView.text = ""
        }

        goToConfigButton.setOnClickListener {
            val intent = Intent(this, ConfigActivity::class.java)
            startActivity(intent)
        }

        btnStartTask.setOnClickListener {
            simulateNetworkOperation()
        }

        btnGoToMenu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        showFragmentsButton.setOnClickListener {
            val intent = Intent(this, FragmentsActivity::class.java)
            startActivity(intent)
        }

        displayTextView.text = getUserNameFromPreferences()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Detectar movimiento basado en el límite definido
            val movement = abs(x) + abs(y) + abs(z)
            if (movement > shakeThreshold) {
                val randomColor = Color.rgb(
                    (0..255).random(),
                    (0..255).random(),
                    (0..255).random()
                )
                layout.setBackgroundColor(randomColor)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No se necesita implementar para esta funcionalidad
    }

    override fun onPause() {
        super.onPause()
        // Detener el listener del sensor cuando la actividad está en pausa
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        // Registrar el listener del sensor nuevamente al reanudar la actividad
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun simulateNetworkOperation() {
        progressBar.visibility = ProgressBar.VISIBLE
        progressBar.progress = 0

        val totalProgressTime = 2000 // 2 seconds
        val interval = 100 // 100 milliseconds

        for (i in 0..totalProgressTime step interval) {
            handler.postDelayed({
                progressBar.progress = (i * 100 / totalProgressTime)
                if (i >= totalProgressTime) {
                    progressBar.visibility = ProgressBar.GONE
                }
            }, i.toLong())
        }
    }

    private fun saveUserNameToPreferences(name: String) {
        with(sharedPreferences.edit()) {
            putString("user_name", name)
            apply()
        }
    }

    private fun getUserNameFromPreferences(): String? {
        return sharedPreferences.getString("user_name", "No name saved")
    }

    private fun saveUserToDatabase(name: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", name)
        }
        val newRowId = db.insert("users", null, values)
        Log.d("PrincipalActivity", "New user inserted with ID: $newRowId")
    }

    private fun getUserNames(): List<String> {
        val db = dbHelper.readableDatabase
        val cursor = db.query("users", arrayOf("name"), null, null, null, null, null)
        val userNames = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow("name"))
                userNames.add(name)
            }
        }
        cursor.close()
        Log.d("PrincipalActivity", "Retrieved users: $userNames")
        return userNames
    }

    private fun clearDatabase() {
        val db = dbHelper.writableDatabase
        db.delete("users", null, null)
        Log.d("PrincipalActivity", "Database cleared")
    }
}
