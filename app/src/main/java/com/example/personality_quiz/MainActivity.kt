package com.example.personality_quiz

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Chronometer
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val spinner: Spinner = findViewById(R.id.spinnerCountries)
        val countries = arrayOf("Polska", "Nauru", "Tuvalu", "Eswatini")
        val adapter = ArrayAdapter( this,
            android.R.layout.simple_list_item_1,
            countries
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        lateinit var chronometer: Chronometer
        lateinit var startBtn: Button
        lateinit var stopBtn: Button
        lateinit var resetBtn: Button
        var running = false
        var pauseOffset: Long = 0

        chronometer = findViewById<Chronometer>(R.id.myChronometer)
        startBtn = findViewById(R.id.startButton)
        stopBtn = findViewById(R.id.stopButton)


        startBtn.setOnClickListener {

            if (!running) {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                chronometer.start()
                running = true
            }
        }

        stopBtn.setOnClickListener {
            if (running) {
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
                Log.i("offset", "pauseOffset: $pauseOffset")
                chronometer.stop()
                running = false
            }
        }

    }
}