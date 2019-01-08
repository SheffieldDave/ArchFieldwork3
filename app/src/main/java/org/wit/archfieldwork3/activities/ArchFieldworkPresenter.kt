package org.wit.archfieldwork3.activities

import android.content.Intent
import org.jetbrains.anko.intentFor
import org.wit.archfieldwork3.helpers.showImagePicker
import org.wit.archfieldwork3.main.MainApp
import org.wit.archfieldwork3.models.Location
import org.wit.archfieldwork3.models.SiteModel

class ArchFieldworkPresenter (val activity: ArchFieldworkActivity) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var site = SiteModel()
    var location = Location(49.002405, 12.097464, 15f)
    var app: MainApp
    var edit = false;

    init {
        app = activity.application as MainApp
        if (activity.intent.hasExtra("site_edit")) {
            edit = true
            site = activity.intent.extras.getParcelable<SiteModel>("site_edit")
            activity.showSite(site)
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
        activity.finish()
    }

    fun doCancel() {
        activity.finish()
    }

    fun doDelete() {
        app.sites.delete(site)
        activity.finish()
    }

    fun doSelectImage(){
        showImagePicker(activity, IMAGE_REQUEST)
    }

    fun doSetLocation(){
        if (site.zoom != 0f){
            location.lat =site.lat
            location.lng = site.lng
            location.zoom = site.zoom
        }
        activity.startActivityForResult(activity.intentFor<MapsActivity>().putExtra("location",location), LOCATION_REQUEST)
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent){
        when (requestCode){
            IMAGE_REQUEST -> {
                    site.image = data.data.toString()
                    activity.showSite(site)
            }
            LOCATION_REQUEST->{
                    location = data.extras.getParcelable<org.wit.archfieldwork3.models.Location>("location")
                    site.lat = location.lat
                    site.lng = location.lng
                    site.zoom = location.zoom

            }
        }
    }

}