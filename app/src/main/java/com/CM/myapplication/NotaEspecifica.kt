package com.CM.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.CM.myapplication.adapters.NoteAdapter
import com.CM.myapplication.entities.Note
import com.CM.myapplication.viewModel.NoteViewModel
import kotlinx.android.synthetic.main.activity_nota_especifica2.*

class NotaEspecifica : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nota_especifica2)

        val titulo = intent.getStringExtra("titulo")
        val nota = intent.getStringExtra("nota")

        val tituloItemView: TextView = findViewById(R.id.tituloEspecifico)
        val notaItemView: TextView = findViewById(R.id.notaEspecifica)

        tituloItemView.text = titulo
        notaItemView.text = nota
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    fun deletarNota(view: View) {

        val id = intent.getIntExtra("id",-1)

        val dialog = AlertDialog.Builder(this)

        dialog.setTitle(R.string.delete_note_confirmation)

        dialog.setMessage(R.string.delete_note_confirmation_text)


        // Set other dialog properties
        dialog.setPositiveButton("Yes"){dialogInterface, which ->
            noteViewModel.deleteById(id)
            Toast.makeText(applicationContext,"Apagada",Toast.LENGTH_LONG).show()
            finish()
        }
        //performing cancel action
        dialog.setNegativeButton("Cancel"){dialogInterface , which ->
            Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
        }
        val deleteDialog: AlertDialog = dialog.create()
        deleteDialog.setCancelable(false)
        deleteDialog.show()

    }


}