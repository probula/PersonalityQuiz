package com.example.personality_quiz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Chronometer
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var running = false
    private var pauseOffset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val spinner: Spinner = findViewById(R.id.spinnerODP)
        val countries = arrayOf("cos1", "cos2", "cos3", "cos4")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val chronometer: Chronometer = findViewById(R.id.myChronometer)
        val startBtn: Button = findViewById(R.id.startButton)
        val stopBtn: Button = findViewById(R.id.stopButton)

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

        val timerTextView: TextView = findViewById(R.id.timerCounter)

        val countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                timerTextView.text = "Pozostało $secondsLeft sekund"
            }

            override fun onFinish() {
                timerTextView.text = "Czas minął!"
            }
        }

        countDownTimer.start()


        //PRZEJSCIE DO DRUGIEJ AKTYWNOSCI
        val finishButton = findViewById<Button>(R.id.koniec)
        finishButton.setOnClickListener {
            finishButton.setOnClickListener {
                val datePicker = findViewById<DatePicker>(R.id.dzien)
                val timePicker = findViewById<TimePicker>(R.id.godzina)
                val day = datePicker.dayOfMonth
                val month = datePicker.month + 1
                val year = datePicker.year
                val hour = timePicker.hour
                val minute = timePicker.minute
                val selectedDate = "$day.$month.$year"
                val selectedTime = "$hour:$minute"

                val checkbox1 = findViewById<CheckBox>(R.id.checkboxOne)
                val checkbox2 = findViewById<CheckBox>(R.id.checkboxTwo)
                val checkbox3 = findViewById<CheckBox>(R.id.checkboxThree)

                val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
                val selectedId = radioGroup.checkedRadioButtonId

                // Walidacja checkboxów
                if (!checkbox1.isChecked && !checkbox2.isChecked && !checkbox3.isChecked) {
                    Toast.makeText(this, "Proszę wybrać odpowiedź 0/3", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Walidacja radio buttona
                if (selectedId == -1) {
                    Toast.makeText(this, "Proszę wybrać odpowiedź, TAK/NIE", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val selectedCheckBoxes = mutableListOf<String>()
                if (checkbox1.isChecked) selectedCheckBoxes.add("Opcja 1")
                if (checkbox2.isChecked) selectedCheckBoxes.add("Opcja 2")
                if (checkbox3.isChecked) selectedCheckBoxes.add("Opcja 3")
                val selectedCheckBoxesString = selectedCheckBoxes.joinToString(", ")

                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val answer = selectedRadioButton.text.toString()

                //SEEKBAR
                val seekBar = findViewById<SeekBar>(R.id.poziom)
                val seekValue = seekBar.progress

                //SPINNER
                val mySpinner = findViewById<Spinner>(R.id.spinnerODP)
                val text: String? = mySpinner.getSelectedItem().toString()



                //WYSLANIE
                val intent = Intent(this, SummaryActivity::class.java)
                intent.putExtra("seekValue", seekValue)
                intent.putExtra("answer", answer)
                intent.putExtra("selectedDate", selectedDate)
                intent.putExtra("selectedTime", selectedTime)
                intent.putExtra("selectedCheckBoxes", selectedCheckBoxesString)
                intent.putExtra("selectedSpinner", text)

                startActivity(intent)
            }
        }





    }
}
