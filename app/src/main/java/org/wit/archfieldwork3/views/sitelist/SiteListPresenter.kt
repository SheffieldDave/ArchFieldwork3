package org.wit.archfieldwork3.views.sitelist

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.archfieldwork3.views.map.SiteMapView
import org.wit.archfieldwork3.main.MainApp
import org.wit.archfieldwork3.models.SiteModel
import org.wit.archfieldwork3.views.site.ArchFieldworkView

class SiteListPresenter (val view: SiteListView){

    var app: MainApp

    init {
        app = view.application as MainApp
    }

    fun getSites()=app.sites.findAll()

    fun doAddSite(){
        view.startActivityForResult<ArchFieldworkView>(0)
    }

    fun doEditSite(site: SiteModel){
        view.startActivityForResult(view.intentFor<ArchFieldworkView>().putExtra("site_edit", site),0)
    }

    fun doShowSitesMap(){
        view.startActivity<SiteMapView>()
    }
}