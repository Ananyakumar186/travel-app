package com.example.travelapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity()  {

    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth


    }

    fun currency(v: View) {
        val currencyb = findViewById<Button>(R.id.currency_converter)
        currencyb.setOnClickListener {
            val currencyI = Intent(this, currencyActivity::class.javaObjectType)
            startActivity(currencyI)
            
        }
    }
    fun back(view: View?) {
        val backintent = Intent(this, LoginActivity::class.java)
        startActivity(backintent)
    }


     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu,menu)
            return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.main_menulogout){
            Log.i("MainActivity","Logout")
            //logout the user
            auth.signOut()
            //Auth.GoogleSignInApi.signOut(apiClient);


            }
            val logoutintent = Intent(this,LoginActivity::class.java)
            logoutintent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutintent)

        return super.onOptionsItemSelected(item)
    }

    fun weather(view: View) {
        val weatherb = findViewById<Button>(R.id.weather_forcast)
        weatherb.setOnClickListener {
            val weatherI = Intent(this, Weather::class.java)
            startActivity(weatherI)

        }
    }


}
