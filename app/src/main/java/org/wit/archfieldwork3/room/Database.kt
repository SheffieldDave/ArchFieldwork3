package org.wit.archfieldwork3.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.wit.archfieldwork3.models.SiteModel

@Database(entities = arrayOf(SiteModel::class), version = 1)
abstract class Database : RoomDatabase() {

    abstract fun siteDao(): SiteDao
}