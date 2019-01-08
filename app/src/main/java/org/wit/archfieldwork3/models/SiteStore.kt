package org.wit.archfieldwork3.models

interface SiteStore {
    fun findAll(): List<SiteModel>
    fun findById (id:Long): SiteModel?
    fun create(site: SiteModel)
    fun update (site: SiteModel)
    fun delete (site: SiteModel)


}