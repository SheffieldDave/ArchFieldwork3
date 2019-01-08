package org.wit.archfieldwork3.views.sitelist

import org.wit.archfieldwork3.models.SiteModel
import org.wit.archfieldwork3.views.BasePresenter
import org.wit.archfieldwork3.views.BaseView
import org.wit.archfieldwork3.views.VIEW

class SiteListPresenter (view: BaseView): BasePresenter(view){

    fun doAddSite() {
        view?.navigateTo(VIEW.SITE)
    }

    fun doEditSites(placemark: SiteModel) {
        view?.navigateTo(VIEW.SITE, 0, "placemark_edit", placemark)
    }

    fun doShowSitesMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun loadSites() {
        view?.showSites(app.sites.findAll())
    }
}