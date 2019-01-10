package org.wit.archfieldwork3.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.wit.archfieldwork3.helpers.readImageFromPath
import org.wit.archfieldwork3.models.SiteModel
import org.wit.archfieldwork3.models.SiteStore
import java.io.ByteArrayOutputStream
import java.io.File


class SiteFireStore(val context: Context) : SiteStore, AnkoLogger {

    val sites = ArrayList<SiteModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    suspend override fun findAll(): List<SiteModel> {
        return sites
    }

    suspend override fun findById(id: Long): SiteModel? {
        val foundSite: SiteModel? = sites.find { p -> p.id == id }
        return foundSite
    }

    suspend override fun create(site: SiteModel) {
        val key = db.child("users").child(userId).child("sites").push().key
        key?.let {
            site.fbId = key
            sites.add(site)
            db.child("users").child(userId).child("sites").child(key).setValue(site)
            updateImage(site)
        }
    }

    suspend override fun update(site: SiteModel) {
        var foundSite: SiteModel? = sites.find { p -> p.fbId == site.fbId }
        if (foundSite != null) {
            foundSite.name = site.name
            foundSite.description = site.description
            foundSite.image = site.image
            foundSite.location = site.location
        }

        db.child("users").child(userId).child("sites").child(site.fbId).setValue(site)
        if ((site.image.length) > 0 && (site.image[0] != 'h')) {
            updateImage(site)
        }
    }

    suspend override fun delete(site: SiteModel) {
        db.child("users").child(userId).child("sites").child(site.fbId).removeValue()
        sites.remove(site)
    }

    override fun clear() {
        sites.clear()
    }

    fun updateImage(site: SiteModel) {
        if (site.image != "") {
            val fileName = File(site.image)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, site.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        site.image = it.toString()
                        db.child("users").child(userId).child("sites").child(site.fbId).setValue(site)
                    }
                }
            }
        }
    }

    fun fetchSites(sitesReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(sites) { it.getValue<SiteModel>(SiteModel::class.java) }
                sitesReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        sites.clear()
        db.child("users").child(userId).child("sites").addListenerForSingleValueEvent(valueEventListener)
    }
}