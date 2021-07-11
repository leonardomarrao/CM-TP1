package com.CM.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_adicionar_registo.*
import kotlinx.android.synthetic.main.activity_maps.*

class AdicionarRegisto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_registo)

        botaoVoltar2.setOnClickListener {

            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)

        }

        botaoAdicionarRegisto.setOnClickListener {

        }

    }
}