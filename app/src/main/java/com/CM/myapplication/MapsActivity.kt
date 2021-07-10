package com.CM.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.CM.myapplication.api.EndPoints
import com.CM.myapplication.api.Registo
import com.CM.myapplication.api.ServiceBuilder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getRegistos(null)

        filtroTipos.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {

                var tipo: String? = when (position) {
                    0 -> null
                    1 -> "Accident"
                    2 -> "Construction"
                    3 -> "Other"
                    else -> null
                }

                mMap.clear()
                getRegistos(tipo)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setInfoWindowAdapter(CustomMapInfoWindow(this))

        mMap.setOnInfoWindowClickListener(this)

    }

    override fun onInfoWindowClick(marker: Marker) {
        val intent = Intent(this, RegistoEspecifico::class.java).apply {
            putExtra("nome", marker.title)
            putExtra("info", marker.snippet)
        }
        startActivity(intent)
    }

    private fun getLatLong() {

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            getLatLong()
            return
        } else {
            mMap.isMyLocationEnabled = true


            fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
                if(location != null) {
                    lastLocation = location
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun logout() {

        //limpar shared preferences
        //voltar ao ecra do login

        val sharedPreferences: SharedPreferences =
            getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt(getString(R.string.idUtilizador), -1)
            commit()

        }

        val intent = Intent(this@MapsActivity, MainActivity::class.java)
        startActivity(intent)
        finish()

    }

    private fun getRegistos(tipo: String?) {

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getRegistos(tipo)

        call.enqueue(object : Callback<List<Registo>> {
            override fun onResponse(call: Call<List<Registo>>, response: Response<List<Registo>>) {

                val sharedPreferences: SharedPreferences =
                    getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE)
                val idUtilizador: Int? =
                    sharedPreferences.getInt(getString(R.string.idUtilizador), -1)

                if (response.isSuccessful) {

                    var cor: BitmapDescriptor?

                    response.body()!!.forEach {

                        if (it.utilizador_id == idUtilizador) {
                            cor =
                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
                        } else {
                            cor =
                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                        }

                        var snippet: String =
                            it.id.toString() + "_" + it.descricao + "_" + it.tipo + "_" + it.utilizador_id.toString() + "_" + it.imagem

                        mMap.addMarker(
                            MarkerOptions().position(LatLng(it.latitude, it.longitude))
                                .title(it.nome)
                                .snippet(snippet)
                                .icon(cor)
                        )
                    }

                }
            }

            override fun onFailure(call: Call<List<Registo>>, t: Throwable) {
                Log.d("Erro", t.message.toString())
                Toast.makeText(this@MapsActivity, R.string.serverError, Toast.LENGTH_SHORT).show()
            }

        })

    }


}