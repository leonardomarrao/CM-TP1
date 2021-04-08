package com.CM.myapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.CM.myapplication.NotaEspecifica
import com.CM.myapplication.R
import com.CM.myapplication.entities.Note
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class NoteAdapter internal constructor(
        context: Context
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Note>()

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloItemView: TextView = itemView.findViewById(R.id.titulo)
        val notaItemView: TextView = itemView.findViewById(R.id.nota)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return NoteViewHolder(itemView)
    }




    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current: Note = notes[position]

        val id = current.id
        holder.tituloItemView.text = current.titulo
        holder.notaItemView.text = current.nota

        holder.itemView.linhaNota.setOnClickListener {

            val context = holder.tituloItemView.context

            val titulo = holder.tituloItemView.text.toString()
            val nota = holder.notaItemView.text.toString()

            val intent = Intent(context, NotaEspecifica::class.java)
            intent.putExtra("id", id)
            intent.putExtra("titulo", titulo)
            intent.putExtra("nota", nota)

            context.startActivity(intent)
        }
    }

    internal fun setNotes(notes: List<Note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size


}