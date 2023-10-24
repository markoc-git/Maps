package com.example.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MainActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val LOCATION_REQUEST_CODE = 101
    private lateinit var getLocationButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getLocationButton = findViewById(R.id.getLocationButton)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getLocationButton.setOnClickListener {
            checkLocationPermissionAndFetchLocation()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun checkLocationPermissionAndFetchLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )
        } else {
            fetchCurrentLocation()
        }
    }

    private fun fetchCurrentLocation() {
        // Koristite Geocoder za dobijanje trenutne lookalike (koordinate)
        val geocoder = Geocoder(this, Locale.getDefault())

        // Ako želite koristiti GPS za trenutnu lokaciju:
        // val location = geocoder.getFromLocationName("Berlin", 1)
        // if (location.isNotEmpty()) {
        //     val latitude = location[0].latitude
        //     val longitude = location[0].longitude
        //     val currentLocation = LatLng(latitude, longitude)
        //     mMap.addMarker(MarkerOptions().position(currentLocation).title("Current Location"))
        //     mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
        // }

        // Ovo je samo primer za prikaz trenutne lokacije koristeći koordinate Beograda
        val currentLocation = LatLng(44.7866, 20.4489)
        mMap.addMarker(MarkerOptions().position(currentLocation).title("Current Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
    }
}
