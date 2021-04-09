package com.CM.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.CM.myapplication.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_nota_especifica2.*
class NotaEspecifica : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota_especifica2)

        val titulo = intent.getStringExtra("titulo")
        val nota = intent.getStringExtra("nota")

        //criamos variáveis do tipo textView para nas linhas 32 e 33, atribuirmos os valormos a esses textviews que queremos
        val tituloItemView: TextView = findViewById(R.id.tituloEspecifico)
        val notaItemView: TextView = findViewById(R.id.notaEspecifica)

        tituloItemView.text = titulo
        notaItemView.text = nota

        //inicializamos o noteviewmodel para usarmos as funções que estão lá
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    fun deletarNota(view: View) {

        val id = intent.getIntExtra("id",-1)

        val dialog = AlertDialog.Builder(this)

        dialog.setTitle(R.string.delete_note_confirmation)

        dialog.setMessage(R.string.delete_note_confirmation_text)



        dialog.setPositiveButton("Yes"){dialogInterface, which ->
            noteViewModel.deleteById(id)
            Toast.makeText(applicationContext,R.string.nota_apagada,Toast.LENGTH_LONG).show()
            finish()
        }

        dialog.setNegativeButton(R.string.cancel_apagar){dialogInterface , which ->
            Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
        }
        val deleteDialog: AlertDialog = dialog.create()
        deleteDialog.setCancelable(false)
        deleteDialog.show()

    }

    fun updateNota(view: View) {
        val id = intent.getIntExtra("id", -1)

            val novoTitulo = tituloEspecifico.text.toString()
            val novaNota = notaEspecifica.text.toString()

            if (novoTitulo.isNullOrEmpty() || novaNota.isNullOrEmpty()) {
                Toast.makeText(
                        applicationContext,
                        R.string.erro_edit,
                        Toast.LENGTH_LONG).show()
            } else {
                noteViewModel.updateNotaFromId(id, novoTitulo, novaNota)
                finish()

            }


    }

}