package org.wit.archfieldwork3.activities

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.archfieldwork3.main.MainApp
import org.wit.archfieldwork3.models.SiteModel

class SiteListPresenter (val activity: SiteListActivity){

    var app: MainApp

    init {
        app = activity.application as MainApp
    }

    fun getSites()=app.sites.findAll()

    fun doAddSite(){
        activity.startActivityForResult<ArchFieldworkActivity>(0)
    }

    fun doEditSite(site: SiteModel){
        activity.startActivityForResult(activity.intentFor<ArchFieldworkActivity>().putExtra("site_edit", site),0)
    }

    fun doShowSitesMap(){
        activity.startActivity<SiteMapsActivity>()
    }
}