package org.wit.archfieldwork3.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_archfieldwork.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.archfieldwork3.R
import org.wit.archfieldwork3.main.MainApp
import org.wit.archfieldwork3.models.SiteModel

class ArchFieldworkActivity : AppCompatActivity(), AnkoLogger {

    var site = SiteModel()
    lateinit var app : MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archfieldwork)
        app = application as MainApp
        info("ArchFieldwork started...")

        btnAddSite.setOnClickListener(){
            site.name=siteName.text.toString()
            site.description = siteDescription.text.toString()
            if (site.name.isNotEmpty()&& site.description.isNotEmpty()){
                app.sites.add(site.copy())
                toast("add site pressed: Name and Discription done")
                info("add Button pressed: $siteName, $siteDescription")
                app.sites.forEach {info ("add Button Pressed: {$it.site}")}
            }else{
                toast("please Enter a Name and Discription")
            }
        }

        btnAddImage.setOnClickListener(){
                toast("add Image pressed")
        }


    }
}
