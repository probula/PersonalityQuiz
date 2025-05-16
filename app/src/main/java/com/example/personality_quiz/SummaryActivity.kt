package com.example.personality_quiz

import android.os.Bundle
import android.widget.CheckBox
import android.widget.SeekBar
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

        //PYTANIE 4
        val seekValue = intent.getIntExtra("seekValue", -1) //domyslna wartosc -1
        findViewById<TextView>(R.id.wynikSB).text = "Wartość z suwaka: $seekValue"

        //PYTANIE 5
        val selectedSpinner = intent.getStringExtra("selectedSpinner")
        findViewById<TextView>(R.id.wynikSpinner).text = "Wybrana odpowiedź z Spinnera: $selectedSpinner"

        //CZAS
        val czas = intent.getStringExtra("czas") ?: "brak danych"
        findViewById<TextView>(R.id.czas).text = "Quiz rozwiązany w: $czas"

        //WYNIK





        val opisCB = when{
            selectedCB?.contains("Opcja1") == true -> "jesteś ekstrawertykiem"
            selectedCB?.contains("Opcja2") == true -> "jesteś ambiwertykiem"
            selectedCB?.contains("Opcja3") == true -> "jesteś introwertykiem"
            else -> "błąd z opisem!"
        }
        val wynikOpisCB = opisCB

        val poziom = when{
            seekValue >= 8 -> "Na pewno $wynikOpisCB"
            seekValue >= 5 -> "Raczej $wynikOpisCB"
            seekValue > 0 -> "Nie $wynikOpisCB"
            else -> "błąd z poziomem!"
        }



        val kolor = when{
            selectedSpinner == "czerwony" -> "jesteś osobą która lubi mieć nad wszystkim kontolę."
            selectedSpinner == "zielony" -> "jesteś osobą pełną zrozumienia, ugodową."
            selectedSpinner == "niebieski" -> "jesteś osobą która jest precyzyjna, dobrze zorganizowana."
            else -> "zły kolor!"
        }

        val odp = "$poziom, $kolor"
        findViewById<TextView>(R.id.opis).text = odp

        val poCzasie = intent.getBooleanExtra("poCzasie", false)
        if(poCzasie){
            findViewById<TextView>(R.id.czas).append(" (odpowiedziano po czasie!)")
        }

    }
}