package com.CM.myapplication

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CustomMapInfoWindow(context: Context) : GoogleMap.InfoWindowAdapter {

    var mapContext = context
    var mapWindow = (context as Activity).layoutInflater.inflate(R.layout.marker_info, null)


    private fun displayInfo(marker: Marker, view: View) {

        var info: List<String> = marker.snippet.split("_")

        view.findViewById<TextView>(R.id.idRegisto).text = info[0]
        view.findViewById<TextView>(R.id.nomeRegisto).text = marker.title
        view.findViewById<TextView>(R.id.descricaoRegisto).text = info[1]
        view.findViewById<TextView>(R.id.tipoRegisto).text = info[2]
        view.findViewById<TextView>(R.id.idUtilizador).text = info[3]

        Log.d("haha", info.toString())

        Picasso.get()
                .load("http://10.0.2.2/myslim/api/imagens/" + info[4] + ".png")
                .into(view.findViewById(R.id.imagemRegisto), object : Callback {
                    override fun onSuccess() {
                        if (marker.isInfoWindowShown) {
                            marker.hideInfoWindow()
                            marker.showInfoWindow()
                        }
                    }

                    override fun onError(e: Exception?) {
                        Toast.makeText(mapContext, mapContext.getString(R.string.errorLoadingImage), Toast.LENGTH_SHORT).show()
                    }
                })

    }



    override fun getInfoContents(marker: Marker): View {
        displayInfo(marker, mapWindow)
        return mapWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        displayInfo(marker, mapWindow)
        return mapWindow
    }

}
