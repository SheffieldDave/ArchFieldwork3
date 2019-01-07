package org.wit.archfieldwork3.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.archfieldwork3.models.SiteModel

class MainApp: Application(), AnkoLogger {

    val sites = ArrayList<SiteModel>()

    override fun onCreate() {
        super.onCreate()
        info ("ArchFieldwork Started (MainApp)")
        sites.add(SiteModel("One", "About one..."))
        sites.add(SiteModel("Two", "About two..."))
        sites.add(SiteModel("Three", "About three..."))
    }
}