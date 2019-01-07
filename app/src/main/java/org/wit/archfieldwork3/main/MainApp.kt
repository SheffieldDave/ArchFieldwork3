package org.wit.archfieldwork3.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.archfieldwork3.models.SiteMemStore

class MainApp: Application(), AnkoLogger {

    val sites = SiteMemStore()

    override fun onCreate() {
        super.onCreate()
        info ("ArchFieldwork Started (MainApp)")

    }
}