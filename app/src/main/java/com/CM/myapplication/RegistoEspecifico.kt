package com.CM.myapplication


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.CM.myapplication.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.CM.myapplication.api.EndPoints
import com.CM.myapplication.api.OpRegisto
import com.CM.myapplication.api.ServiceBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_registo_especifico.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class RegistoEspecifico : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registo_especifico)

        val botaoApagar: Button = findViewById<View>(R.id.botaoApagarRegisto) as Button
        val botaoUpdate: Button = findViewById<View>(R.id.botaoUpdate) as Button

        val sharedPreferences: SharedPreferences =
                getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE)

        val idUtilizador: Int? = sharedPreferences.getInt(getString(R.string.idUtilizador), -1)

        var registo: String? = intent.getStringExtra("info")

        var infoRegisto: List<String> = registo!!.split("_")


        findViewById<TextView>(R.id.nomeRegistoEspecifico).text = intent.getStringExtra("nome")
        findViewById<TextView>(R.id.descricaoRegistoEspecifico).text = infoRegisto[1]
        findViewById<TextView>(R.id.tipoRegistoEspecifico).text = infoRegisto[2]

        Picasso.get()
                .load("http://10.0.2.2/myslim/api/imagens/" + infoRegisto[4] + ".png")
                .into(findViewById<ImageView>(R.id.imagemRegistoEspecifico))


        if (infoRegisto[3].toInt() != idUtilizador) {
            botaoApagar.setVisibility(View.INVISIBLE)
            botaoUpdate.setVisibility(View.INVISIBLE)
        } else {
            botaoApagar.setVisibility(View.VISIBLE)
            botaoUpdate.setVisibility(View.VISIBLE)
        }

        botaoApagar.setOnClickListener {

            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.deleteRegisto(infoRegisto[0].toInt(), idUtilizador!!)

            call.enqueue(object : Callback<OpRegisto> {
                override fun onResponse(call: Call<OpRegisto>, response: Response<OpRegisto>) {
                    if(response.isSuccessful && response.body()!!.error == null){
                        Toast.makeText(this@RegistoEspecifico, getString(R.string.registoApagado), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegistoEspecifico, MapsActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@RegistoEspecifico, infoRegisto[0], Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<OpRegisto>, t: Throwable) {
                    Toast.makeText(this@RegistoEspecifico, getString(R.string.serverError), Toast.LENGTH_SHORT).show()
                }
            })


        }


        botaoUpdate.setOnClickListener {

            val intent = Intent(this, UpdateRegisto::class.java).apply {
                putExtra("nome", intent.getStringExtra("nome"))
                putExtra("info", registo)
            }
            startActivity(intent)
            finish()

        }

        botaoVoltar.setOnClickListener {

            val intent = Intent(this@RegistoEspecifico, MapsActivity::class.java)
            startActivity(intent)
            finish()

        }




        }


    }
