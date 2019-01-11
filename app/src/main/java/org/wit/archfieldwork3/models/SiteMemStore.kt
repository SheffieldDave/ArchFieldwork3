package org.wit.archfieldwork3.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long{
    return lastId++
}

class SiteMemStore: SiteStore, AnkoLogger{

    val sites = ArrayList<SiteModel>()

    fun logAll(){
        sites.forEach{info("${it}")}
    }

    suspend override fun findById(id: Long): SiteModel? {
        val foundSite: SiteModel? = sites.find{it.id ==id}
        return foundSite
    }

    suspend override fun findAll(): List<SiteModel> {
        return sites
    }

    suspend override fun create(site: SiteModel) {
        sites.add(site)
        logAll()
    }

    suspend override fun update(site: SiteModel) {
        var foundSite: SiteModel? = sites.find { p -> p.id == site.id}
        if (foundSite != null){
            foundSite.name = site.name
            foundSite.description = site.description
            foundSite.image = site.image
            foundSite.lat = site.lat
            foundSite.lng = site.lng
            foundSite.zoom = site.zoom
            logAll()
        }

    }

    suspend override fun delete(site: SiteModel) {
        sites.remove(site)
    }


}