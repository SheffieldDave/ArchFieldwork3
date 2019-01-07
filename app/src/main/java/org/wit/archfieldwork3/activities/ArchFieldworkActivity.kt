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
    var edit = false


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
            if (site.name.isEmpty()) {
                toast(R.string.enter_site_name_discription)
            }else{
                if (edit){
                    app.sites.update(site.copy())
                }else{
                    app.sites.create(site.copy())
                }
                //info("add Button pressed: $siteName, $siteDescription")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }

        if(intent.hasExtra("site_edit")){
            edit = true
            site = intent.extras.getParcelable<SiteModel>("site_edit")
            siteName.setText(site.name)
            siteDescription.setText(site.description)
            btnAddSite.setText(R.string.save_site)
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
