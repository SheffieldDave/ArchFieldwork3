package org.wit.archfieldwork3.views.sitelist

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
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
        async(UI) {
            view?.showSites(app.sites.findAll())
        }
    }

    fun doLogout(){
        FirebaseAuth.getInstance().signOut()
        view?.navigateTo(VIEW.LOGIN)
    }
}