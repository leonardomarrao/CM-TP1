package com.CM.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.CM.myapplication.api.EndPoints
import com.CM.myapplication.api.ServiceBuilder
import com.CM.myapplication.api.Utilizador
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_nota_especifica2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        botaoNotas.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        botaoLogin.setOnClickListener {

            val username = usernameBox.text.toString()
            val password = passwordBox.text.toString()

            if (username == "") {

                Toast.makeText(this@MainActivity, R.string.noUsername, Toast.LENGTH_SHORT).show()

            } else if (password == "") {

                Toast.makeText(this@MainActivity, R.string.noPassword, Toast.LENGTH_SHORT).show()

            } else {

                val messDig: MessageDigest = MessageDigest.getInstance("SHA-256")
                messDig.update(password.toByteArray(Charsets.UTF_8))
                val dig: ByteArray = messDig.digest()

                val hashedPassword = StringBuilder()
                dig.forEach { byte -> hashedPassword.append(String.format("%02X", byte)) }


                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.login(username, password)

                call.enqueue(object : Callback<Utilizador> {
                    override fun onResponse(call: Call<Utilizador>, response: Response<Utilizador>) {
                        if (response.isSuccessful) {

                            if (response.body()!!.error != null) {

                                Toast.makeText(this@MainActivity, getString(R.string.loginFail), Toast.LENGTH_SHORT).show()
                            } else {

                                val sharedPreferences: SharedPreferences = getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE)
                                with(sharedPreferences.edit()) {
                                    putInt(getString(R.string.idUtilizador), response.body()!!.id!!)
                                    commit()
                                }

                                val intent = Intent(this@MainActivity, MapsActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<Utilizador>, t: Throwable) {
                        Log.d("Erro", t.message.toString())
                        Toast.makeText(this@MainActivity, R.string.serverError, Toast.LENGTH_SHORT).show()
                    }

                })
            }

        }


    }

}