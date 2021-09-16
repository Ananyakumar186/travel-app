package com.example.travelapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class cityFinder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_finder)
        val editText = findViewById<EditText>(R.id.searchCity)
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            val backintent = Intent(this, Weather::class.java)
            startActivity(backintent)}
        editText.setOnEditorActionListener { v, actionId, event ->
            val newCity = editText.text.toString()
            val intent = Intent(this@cityFinder, Weather::class.java)
            intent.putExtra("City", newCity)
            startActivity(intent)
            false
        }
    }

}