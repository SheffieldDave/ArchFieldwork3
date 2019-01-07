package org.wit.archfieldwork3.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_archfieldwork.*
import org.jetbrains.anko.toast
import org.wit.archfieldwork3.R
import org.wit.archfieldwork3.main.MainApp
import org.wit.archfieldwork3.models.SiteModel

class ArchFieldworkActivity : AppCompatActivity() {

    var site = SiteModel()
    lateinit var app : MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archfieldwork)
        app = application as MainApp
        //info("ArchFieldwork started...")
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)


        btnAddSite.setOnClickListener(){
            site.name=siteName.text.toString()
            site.description = siteDescription.text.toString()
            if (site.name.isNotEmpty()&& site.description.isNotEmpty()){
                app.sites.create(site.copy())
                //toast("add site pressed: Name and Discription done")
                //info("add Button pressed: $siteName, $siteDescription")
                //app.sites.findAll().forEach {info ("add Button Pressed: {$it.site}")}
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }else{
                toast("please Enter a Name and Discription")
            }
        }

        btnAddImage.setOnClickListener(){
                toast("add Image pressed")
        }





    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_site, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.item_cancel ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
