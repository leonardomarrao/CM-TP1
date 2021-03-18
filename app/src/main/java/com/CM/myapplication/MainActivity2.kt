package com.CM.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.CM.myapplication.adapter.LineAdapter
import com.CM.myapplication.dataclasses.Nota
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    private lateinit var myList: ArrayList<Nota>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        myList = ArrayList<Nota>()

        for (i in 0 until 500) {
            myList.add(Nota("Nota $i"))
        }

        recycler_view.adapter = LineAdapter(myList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        //recycler_view.setHasFixedSize(true)
    }


    fun insert(view: View) {
        myList.add(0, Nota("Nota XXX"))
        recycler_view.adapter?.notifyDataSetChanged()

    }
}