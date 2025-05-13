package com.example.personality_quiz

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.widget.*
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


        val spinner: Spinner = findViewById(R.id.spinnerCountries)
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
//            val explicitIntent = Intent(this, SummaryActivity::class.java)
//            startActivity(explicitIntent)


            val datePicker = findViewById<DatePicker>(R.id.dzien)
            val timePicker = findViewById<TimePicker>(R.id.godzina)
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1
            val year = datePicker.year
            val hour = timePicker.hour
            val minute = timePicker.minute

            val selectedDate = "$day.$month.$year"
            val selectedTime = "$hour:$minute"


            val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
            val selectedId = radioGroup.checkedRadioButtonId

            if (selectedId != -1) {
                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val answer = selectedRadioButton.text.toString()
                val intent = Intent(this, SummaryActivity::class.java)
                intent.putExtra("answer", answer)
                intent.putExtra("selectedDate", selectedDate)
                intent.putExtra("selectedTime", selectedTime)
                startActivity(intent)}
            else{
                Toast.makeText(this, "Proszę wybrać odpowiedź, TAK/NIE", Toast.LENGTH_SHORT).show()
            }




        }





    }
}
