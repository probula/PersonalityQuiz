package com.example.personality_quiz

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class SummaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_summary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //PYTANIE 1
        val answer1 = intent.getStringExtra("answer")
        findViewById<TextView>(R.id.wynikTN).text = "Twoja odpowiedź: $answer1"

        //PYTANIE 2
        val selectedDate = intent.getStringExtra("selectedDate")
        val selectedTime = intent.getStringExtra("selectedTime")

        val wynikDate = findViewById<TextView>(R.id.wynikDataCzas)
        wynikDate.text = "Data: $selectedDate\nCzas: $selectedTime"

        //PYTANIE 3
        val selectedCB = intent.getStringExtra("selectedCheckBoxes")
        findViewById<TextView>(R.id.wynikCB).text = "Wybrane odpowiedzi: $selectedCB"

    }
}