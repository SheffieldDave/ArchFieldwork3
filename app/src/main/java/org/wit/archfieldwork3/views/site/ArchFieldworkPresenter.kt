package org.wit.archfieldwork3.views.site

import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.archfieldwork3.helpers.checkLocationPermission
import org.wit.archfieldwork3.helpers.isPermissionGranted
import org.wit.archfieldwork3.helpers.showImagePicker
import org.wit.archfieldwork3.models.Location
import org.wit.archfieldwork3.models.SiteModel
import org.wit.archfieldwork3.views.*

class ArchFieldworkPresenter (view: BaseView): BasePresenter(view) {


    var site = SiteModel()
    var defaultLocation = Location(49.002405, 12.097464, 15f)
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    var edit = false;
    var map: GoogleMap? = null

    init {
        if (view.intent.hasExtra("site_edit")) {
            edit = true
            site = view.intent.extras.getParcelable<SiteModel>("site_edit")
            view.showSite(site)
        }else{
            if (checkLocationPermission(view)){
                // todo get the current location
            }
        }
    }

    fun doConfigureMap(m: GoogleMap){
        map = m
        locationUpdate(site.lat, site.lng)
    }

    fun locationUpdate(lat: Double, lng: Double){
        site.lat = lat
        site.lng = lng
        site.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(site.name).position(LatLng(site.lat, site.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(site.lat,site.lng),site.zoom))
        view?.showSite(site)
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if(isPermissionGranted(requestCode, grantResults)){
            //todo get the current location
        }else{
            //permissions denied, so use the default location
            locationUpdate(defaultLocation.lat, defaultLocation.lng)
        }
    }

    fun doAddOrSave(siteName: String, siteDescription: String) {
        site.name = siteName
        site.description = siteDescription
        if (edit) {
            app.sites.update(site)
        } else {
            app.sites.create(site)
        }
        view?.finish()
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.sites.delete(site)
        view?.finish()
    }

    fun doSelectImage(){
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSetLocation(){
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
        } else {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(site.lat, site.lng, site.zoom))
        }
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent){
        when (requestCode){
            IMAGE_REQUEST -> {
                    site.image = data.data.toString()
                    view?.showSite(site)
            }
            LOCATION_REQUEST->{
                    val location = data.extras.getParcelable<org.wit.archfieldwork3.models.Location>("location")
                    site.lat = location.lat
                    site.lng = location.lng
                    site.zoom = location.zoom
                    locationUpdate(site.lat,site.lng)


            }
        }
    }

}