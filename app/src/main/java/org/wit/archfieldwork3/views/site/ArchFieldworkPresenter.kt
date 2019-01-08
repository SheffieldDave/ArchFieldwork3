package org.wit.archfieldwork3.views.site

import android.content.Intent
import org.jetbrains.anko.intentFor
import org.wit.archfieldwork3.helpers.showImagePicker
import org.wit.archfieldwork3.main.MainApp
import org.wit.archfieldwork3.models.Location
import org.wit.archfieldwork3.models.SiteModel
import org.wit.archfieldwork3.views.editlocation.EditLocationView

class ArchFieldworkPresenter (val view: ArchFieldworkView) {

    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    var site = SiteModel()
    var location = Location(49.002405, 12.097464, 15f)
    var app: MainApp
    var edit = false;

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("site_edit")) {
            edit = true
            site = view.intent.extras.getParcelable<SiteModel>("site_edit")
            view.showSite(site)
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
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.sites.delete(site)
        view.finish()
    }

    fun doSelectImage(){
        showImagePicker(view, IMAGE_REQUEST)
    }

    fun doSetLocation(){
        if (site.zoom != 0f){
            location.lat =site.lat
            location.lng = site.lng
            location.zoom = site.zoom
        }
        view.startActivityForResult(view.intentFor<EditLocationView>().putExtra("location",location), LOCATION_REQUEST)
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent){
        when (requestCode){
            IMAGE_REQUEST -> {
                    site.image = data.data.toString()
                    view.showSite(site)
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