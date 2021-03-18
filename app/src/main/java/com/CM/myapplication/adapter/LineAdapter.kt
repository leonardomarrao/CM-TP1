package com.CM.myapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.CM.myapplication.R
import com.CM.myapplication.dataclasses.Nota
import kotlinx.android.synthetic.main.recyclerline.view.*


class LineAdapter(val list: ArrayList<Nota>):RecyclerView.Adapter<LineViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {

        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recyclerline, parent, false);
        return LineViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val currentNota = list[position]

        holder.descricao.text = currentNota.descricao

        //holder.nhabitants.text = currentPlace.habitants.toString()
    }

}

class LineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val descricao = itemView.descricao

    //var nhabitants = itemView.habitants

}

