package org.wit.archfieldwork3.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.wit.archfieldwork3.helpers.exists
import org.wit.archfieldwork3.helpers.read
import org.wit.archfieldwork3.helpers.write
import java.util.*

//Imported from moodle webpage... rework later

val JSON_FILE = "sites.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<SiteModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class SiteJSONStore : SiteStore, AnkoLogger {

    val context: Context
    var sites = mutableListOf<SiteModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    suspend override fun findAll(): MutableList<SiteModel> {
        return sites
    }

    suspend override fun findById(id: Long): SiteModel? {
        val foundSite: SiteModel? = sites.find{it.id ==id}
        return foundSite
    }

    suspend override fun create(site: SiteModel) {
        site.id = generateRandomId()
        sites.add(site)
        serialize()
    }


    suspend override fun update(site: SiteModel) {
        val sitesList = findAll() as ArrayList <SiteModel>
        var foundSite: SiteModel? = sitesList.find {p -> p.id == site.id}
        if (foundSite != null) {
            foundSite.name = site.name
            foundSite.description = site.description
            foundSite.image = site.image
            foundSite.location = site.location
        }
        serialize()
    }

    suspend override fun delete(site: SiteModel) {
        sites.remove(site)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(sites, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        sites = Gson().fromJson(jsonString, listType)
    }

    override fun clear() {
        sites.clear()
    }
}