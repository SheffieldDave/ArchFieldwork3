package org.wit.archfieldwork3.room

import android.content.Context
import androidx.room.Room
import org.wit.archfieldwork3.models.SiteModel
import org.wit.archfieldwork3.models.SiteStore
import org.jetbrains.anko.coroutines.experimental.bg

class SiteStoreRoom(val context: Context) : SiteStore {

    var dao: SiteDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.siteDao()
    }

    suspend override fun findAll(): List<SiteModel> {
        return dao.findAll()
    }

    suspend override fun findById(id: Long): SiteModel? {
        return dao.findById(id)
    }

    suspend override fun create(site: SiteModel) {
        dao.create(site)
    }

    suspend override fun update(site: SiteModel) {
        dao.update(site)
    }

    suspend override fun delete(site: SiteModel) {
        dao.deleteSite(site)
    }

    fun clear() {
    }
}