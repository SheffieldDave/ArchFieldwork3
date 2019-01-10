package org.wit.archfieldwork3.models

interface SiteStore {
   suspend fun findAll(): List<SiteModel>
    suspend fun findById (id:Long): SiteModel?
    suspend fun create(site: SiteModel)
   suspend fun update (site: SiteModel)
    suspend fun delete (site: SiteModel)
    fun clear()


}