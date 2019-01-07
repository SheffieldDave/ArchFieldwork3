package org.wit.archfieldwork3.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long{
    return lastId++
}

class SiteMemStore: SiteStore, AnkoLogger{

    val sites = ArrayList<SiteModel>()

    override fun findAll(): List<SiteModel> {
        return sites
    }

    override fun create(site: SiteModel) {
        sites.add(site)
        logAll()
    }

    override fun update(site: SiteModel) {
        var foundSite: SiteModel? = sites.find { p -> p.id == site.id}
        if (foundSite != null){
            foundSite.name = site.name
            foundSite.description = site.description
            foundSite.image = site.image
            logAll()
        }

    }

    fun logAll(){
        sites.forEach{info("${it}")}
    }


}