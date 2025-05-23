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
    var odpowiedzPoCzasie = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
            }

        val seekBar = findViewById<SeekBar>(R.id.poziom)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                //fromUser - informacja czy zmiana wywołana przez użytkownika, czy programistycznie
                Toast.makeText(this@MainActivity, "Wartość suwaka: $progress", Toast.LENGTH_SHORT).show()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                //Toast.makeText(this@MainActivity, "Zacząłeś przesuwać suwak", Toast.LENGTH_SHORT).show()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                //Toast.makeText(this@MainActivity, "Zakończyłeś przesuwanie suwaka", Toast.LENGTH_SHORT).show()
            }
        })


        val spinner: Spinner = findViewById(R.id.spinnerODP)
        val countries = arrayOf("czerwony", "zielony", "niebieski")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val chronometer: Chronometer = findViewById(R.id.myChronometer)
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
        //running = true

            if (running) {
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                chronometer.start()
                running = true
            }



        val timerTextView: TextView = findViewById(R.id.timerCounter)

        val countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                timerTextView.text = "Jak sie dziś czujesz?: (czas na udzielenie odp. $secondsLeft):"
            }



            override fun onFinish() {
                timerTextView.text = "Czas minął!"
                odpowiedzPoCzasie = true
            }
        }

        countDownTimer.start()


        //PRZEJSCIE DO DRUGIEJ AKTYWNOSCI
        val finishButton = findViewById<Button>(R.id.koniec)
        finishButton.setOnClickListener{
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
                if (checkbox1.isChecked) selectedCheckBoxes.add("Opcja1")
                if (checkbox2.isChecked) selectedCheckBoxes.add("Opcja2")
                if (checkbox3.isChecked) selectedCheckBoxes.add("Opcja3")

                val finalCB = if (selectedCheckBoxes.size >= 2){
                    selectedCheckBoxes.random()
                } else {
                    selectedCheckBoxes.joinToString(", ")
                }

                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val answer = selectedRadioButton.text.toString()



                //SEEKBAR
                val seekValue = seekBar.progress




                //SPINNER
                val mySpinner = findViewById<Spinner>(R.id.spinnerODP)
                val text: String? = mySpinner.getSelectedItem().toString()

                //TIMER
                val timer = findViewById<Chronometer>(R.id.myChronometer)
                val czas = timer.text.toString()



                //WYSLANIE
                val intent = Intent(this, SummaryActivity::class.java)
                intent.putExtra("seekValue", seekValue)
                intent.putExtra("answer", answer)
                intent.putExtra("selectedDate", selectedDate)
                intent.putExtra("selectedTime", selectedTime)
                intent.putExtra("selectedCheckBoxes", finalCB)
                intent.putExtra("selectedSpinner", text)
                intent.putExtra("czas", czas)
                intent.putExtra("poCzasie", odpowiedzPoCzasie)

                startActivity(intent)
            }
        }




    }
