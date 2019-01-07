package org.wit.archfieldwork3.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.archfieldwork3.models.SiteMemStore
import org.wit.archfieldwork3.models.SiteStore


class MainApp: Application(), AnkoLogger {

    lateinit var sites: SiteStore

    override fun onCreate() {
        super.onCreate()
        sites = SiteMemStore()
        //sites = SiteJSONStore(applicationContext)
        info ("ArchFieldwork Started (MainApp)")

    }
}