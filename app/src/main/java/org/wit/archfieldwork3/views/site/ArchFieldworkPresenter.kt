package org.wit.archfieldwork3.views.site

import android.annotation.SuppressLint
import android.content.Intent
import android.provider.Contacts
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.archfieldwork3.helpers.checkLocationPermission
import org.wit.archfieldwork3.helpers.createDefaultLocationRequest
import org.wit.archfieldwork3.helpers.isPermissionGranted
import org.wit.archfieldwork3.helpers.showImagePicker
import org.wit.archfieldwork3.models.Location
import org.wit.archfieldwork3.models.SiteModel
import org.wit.archfieldwork3.views.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class ArchFieldworkPresenter (view: BaseView): BasePresenter(view) {


    var site = SiteModel()
    var defaultLocation = Location(49.002405, 12.097464, 15f)
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    var edit = false;
    var map: GoogleMap? = null
    val locationRequest = createDefaultLocationRequest()

    init {
        if (view.intent.hasExtra("site_edit")) {
            edit = true
            site = view.intent.extras.getParcelable<SiteModel>("site_edit")
            view.showSite(site)
        } else {
            if (checkLocationPermission(view)) {
                doSetCurrentLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun doRestartLocationUpdates(){
        var locationCallback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null){
                    val l = locationResult.locations.last()
                    locationUpdate(l.latitude,l.longitude)
                }
            }
        }
        if(!edit){
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }


    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(site.lat, site.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        site.lat = lat
        site.lng = lng
        site.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(site.name).position(LatLng(site.lat, site.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(site.lat, site.lng), site.zoom))
        view?.showSite(site)
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            locationUpdate(defaultLocation.lat, defaultLocation.lng)
        }
    }

    fun doAddOrSave(siteName: String, siteDescription: String) {
        site.name = siteName
        site.description = siteDescription
        async(UI) {
            if (edit) {
                app.sites.update(site)
            } else {
                app.sites.create(site)
            }
            view?.finish()
        }
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        async(UI) {
            app.sites.delete(site)
            view?.finish()
        }
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSetLocation() {
        view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(site.lat, site.lng, site.zoom))
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                site.image = data.data.toString()
                view?.showSite(site)
            }
            LOCATION_REQUEST -> {
                val location = data.extras.getParcelable<org.wit.archfieldwork3.models.Location>("location")
                site.lat = location.lat
                site.lng = location.lng
                site.zoom = location.zoom
                locationUpdate(site.lat, site.lng)


            }
        }
    }
}


