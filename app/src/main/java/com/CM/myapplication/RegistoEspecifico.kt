package com.CM.myapplication


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.CM.myapplication.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_registo_especifico.*


class RegistoEspecifico : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registo_especifico)

        val botaoApagar: Button = findViewById<View>(R.id.botaoApagarRegisto) as Button
        val botaoUpdate: Button = findViewById<View>(R.id.botaoUpdate) as Button

        val sharedPreferences: SharedPreferences =
                getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE)

        val idUtilizador: Int? = sharedPreferences.getInt(getString(R.string.idUtilizador), -1)

        Log.i("haha", idUtilizador.toString())

        var registo: String? = intent.getStringExtra("info")

        var infoRegisto: List<String> = registo!!.split("_")


        findViewById<TextView>(R.id.nomeRegistoEspecifico).text = intent.getStringExtra("nome")
        findViewById<TextView>(R.id.descricaoRegistoEspecifico).text = infoRegisto[1]
        findViewById<TextView>(R.id.tipoRegistoEspecifico).text = infoRegisto[2]

        Picasso.get()
                .load("http://10.0.2.2/myslim/api/imagens/" + infoRegisto[4] + ".png")
                .into(findViewById(R.id.imagemRegistoEspecifico), object : Callback {
                    override fun onSuccess() {

                    }

                    override fun onError(e: Exception?) {
                        Toast.makeText(
                                this@RegistoEspecifico,
                                getString(R.string.errorLoadingImage),
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                })


        if (infoRegisto[3].toInt() != idUtilizador) {
            botaoApagar.setVisibility(View.INVISIBLE)
            botaoUpdate.setVisibility(View.INVISIBLE)
        } else {
            botaoApagar.setVisibility(View.VISIBLE)
            botaoUpdate.setVisibility(View.VISIBLE)
        }

        botaoApagar.setOnClickListener {


        }

        botaoUpdate.setOnClickListener {


        }

        botaoVoltar.setOnClickListener {

            val intent = Intent(this@RegistoEspecifico, MapsActivity::class.java)
            startActivity(intent)
            finish()

        }


    }
}