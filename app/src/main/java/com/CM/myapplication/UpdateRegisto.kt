package com.CM.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.CM.myapplication.api.EndPoints
import com.CM.myapplication.api.OpRegisto
import com.CM.myapplication.api.ServiceBuilder
import com.CM.myapplication.api.Utilizador
import kotlinx.android.synthetic.main.activity_adicionar_registo.*
import kotlinx.android.synthetic.main.activity_update_registo.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateRegisto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_registo)

        val sharedPreferences: SharedPreferences =
            getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE)

        val idUtilizador: Int? = sharedPreferences.getInt(getString(R.string.idUtilizador), -1)

        var registo: String? = intent.getStringExtra("info")

        var infoRegisto: List<String> = registo!!.split("_")

        val titulo: String? = intent.getStringExtra("nome")

        findViewById<EditText>(R.id.nomeRegistoUpdate).setText(titulo)
        findViewById<EditText>(R.id.descricaoRegistoUpdate).setText(infoRegisto[1])

        botaoSave.setOnClickListener {

            val nome: String = findViewById<EditText>(R.id.nomeRegistoUpdate).text.toString()
            val desc: String = findViewById<EditText>(R.id.descricaoRegistoUpdate).text.toString()

            if (nome == titulo && desc == infoRegisto[1]) {
                Toast.makeText(this@UpdateRegisto, R.string.naoMudouValores, Toast.LENGTH_SHORT)
                    .show()
            } else if (nome == "" || desc == "") {

                Toast.makeText(this@UpdateRegisto, R.string.emptyReport, Toast.LENGTH_SHORT)
                    .show()

            } else {

                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.updateRegisto(infoRegisto[0].toInt(), nome, desc, idUtilizador!!)

                call.enqueue(object : Callback<OpRegisto> {
                    override fun onResponse(call: Call<OpRegisto>, response: Response<OpRegisto>) {
                        if (response.isSuccessful) {

                            if (response.body()!!.error != null) {
                                Toast.makeText(
                                    this@UpdateRegisto,
                                    getString(R.string.errorUpdating),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val intent = Intent(this@UpdateRegisto, MapsActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<OpRegisto>, t: Throwable) {
                        Log.d("Erro", t.message.toString())
                        Toast.makeText(this@UpdateRegisto, R.string.serverError, Toast.LENGTH_SHORT)
                            .show()
                    }

                })

            }


        }


    }
}