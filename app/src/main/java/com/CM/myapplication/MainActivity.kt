package com.CM.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun botaoNotas(view: View) {
        val intent = Intent(this, MainActivity2::class.java).apply {
            //putExtra(EXTRA_MESSAGE, message) passagem de parametros
        }
        startActivity(intent)
    }
}