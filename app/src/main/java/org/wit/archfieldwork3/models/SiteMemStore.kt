package org.wit.archfieldwork3.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class SiteMemStore: SiteStore, AnkoLogger{

    val sites = ArrayList<SiteModel>()

    override fun findAll(): List<SiteModel> {
        return sites
    }

    override fun create(site: SiteModel) {
        sites.add(site)
        logAll()
    }

    fun logAll(){
        sites.forEach{info("${it}")}
    }
}