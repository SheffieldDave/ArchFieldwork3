package org.wit.archfieldwork3.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.archfieldwork3.models.SiteModel
import org.wit.archfieldwork3.views.BasePresenter
import org.wit.archfieldwork3.views.BaseView

class SiteMapPresenter(view: BaseView): BasePresenter(view){

    fun doPopulateMap(map: GoogleMap, sites: List<SiteModel>) {
        map.uiSettings.setZoomControlsEnabled(true)
        sites.forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.name).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as Long
        val site = app.sites.findById(tag)
        if (site != null) view?.showSite(site)

    }

    fun loadSites() {
        view?.showSites(app.sites.findAll())
    }
}