package com.CM.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botaoLogin.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }
    /*fun botaoNotas(view: View) {
        val intent = Intent(this, MainActivity2::class.java).apply {
            //putExtra(EXTRA_MESSAGE, message) passagem de parametros
        }
        startActivity(intent)
    }

    fun botaoLogin(view: View) {
        val intent = Intent(this, MapsActivity::class.java).apply {
            //putExtra(EXTRA_MESSAGE, message) passagem de parametros
        }
        startActivity(intent)
    }*/
}