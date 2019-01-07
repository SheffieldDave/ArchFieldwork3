package org.wit.archfieldwork3.models

interface SiteStore {
    fun findAll(): List<SiteModel>
    fun create(site: SiteModel)
    fun update (site: SiteModel)
}