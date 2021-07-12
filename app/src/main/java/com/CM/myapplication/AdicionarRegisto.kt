package com.CM.myapplication

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.CM.myapplication.api.EndPoints
import com.CM.myapplication.api.OpRegisto
import com.CM.myapplication.api.ServiceBuilder
import com.CM.myapplication.api.Utilizador
import kotlinx.android.synthetic.main.activity_adicionar_registo.*

import kotlinx.android.synthetic.main.activity_maps.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

class AdicionarRegisto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedPreferences: SharedPreferences =
            getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE)

        val utilizadorId: Int? = sharedPreferences.getInt(getString(R.string.idUtilizador), -1)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_registo)

        botaoVoltar2.setOnClickListener {

            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)

        }

        imagemBotao.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    1
                )

            } else {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                try {
                    startActivityForResult(takePictureIntent, 1)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        this@AdicionarRegisto,
                        getString(R.string.errorLoadingImage),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }


        addReportButton.setOnClickListener {

            val title = titleBox.text.toString()
            val desc = descriptionBox.text.toString()
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            val tipo = filtroTipos2.selectedItem.toString()



            if (title == "" || desc == "" || tipo == resources.getStringArray(R.array.filtroTipos2)[0]) {

                Toast.makeText(this@AdicionarRegisto, R.string.emptyReport, Toast.LENGTH_SHORT)
                    .show()

            } else {

                val nome: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), title)
                val descricao: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), desc)
                val tipoRep: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), tipo)
                val idUtilizador: RequestBody = RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    utilizadorId.toString()
                )
                val latit: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), latitude.toString())
                val longit: RequestBody =
                    RequestBody.create(MediaType.parse("multipart/form-data"), longitude.toString())

                val imgBitmap: Bitmap =
                    findViewById<ImageView>(R.id.imagemInserida).drawable.toBitmap()
                val imgFile: File = convertBitmapToFile("file", imgBitmap)
                val imgFileRequest: RequestBody =
                    RequestBody.create(MediaType.parse("image/*"), imgFile)
                val imagem: MultipartBody.Part =
                    MultipartBody.Part.createFormData("imagem", imgFile.name, imgFileRequest)


                val request = ServiceBuilder.buildService(EndPoints::class.java)
                val call = request.criarRegisto(
                    nome,
                    descricao,
                    latit,
                    longit,
                    idUtilizador,
                    tipoRep,
                    imagem
                )

                call.enqueue(object : Callback<OpRegisto> {
                    override fun onResponse(call: Call<OpRegisto>, response: Response<OpRegisto>) {
                        if (response.isSuccessful) {

                            if (response.body()!!.error != null) {

                                Toast.makeText(
                                    this@AdicionarRegisto,
                                    getString(R.string.errorCreatingReport),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val intent = Intent(this@AdicionarRegisto, MapsActivity::class.java)
                                startActivity(intent)
                                finish()

                            }

                        }
                    }


                    override fun onFailure(call: Call<OpRegisto>, t: Throwable) {
                        Log.d("Erro", t.message.toString())
                        Toast.makeText(
                            this@AdicionarRegisto,
                            R.string.serverError,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })

            }

        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {

            if (data!!.extras != null) {
                val imageBitmap = data!!.extras!!.get("data") as Bitmap
                imagemInserida.setImageBitmap(imageBitmap)
            }

        }
    }

    private fun convertBitmapToFile(fileName: String, bitmap: Bitmap): File {

        val file = File(this@AdicionarRegisto.cacheDir, fileName)
        file.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos)
        val bitMapData = bos.toByteArray()

        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }


}



