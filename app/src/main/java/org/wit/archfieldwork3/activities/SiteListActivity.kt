package org.wit.archfieldwork3.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_site_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.archfieldwork3.R
import org.wit.archfieldwork3.adapters.ArchFieldworkAdapter
import org.wit.archfieldwork3.adapters.SiteListener
import org.wit.archfieldwork3.main.MainApp
import org.wit.archfieldwork3.models.SiteModel

class SiteListActivity : AppCompatActivity(), SiteListener {

    lateinit var app:MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_list)
        app = application as MainApp

        toolbarMain.title = title
        setSupportActionBar(toolbarMain)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadSites()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.item_add-> startActivityForResult<ArchFieldworkActivity>(200)
            R.id.item_map-> startActivity<SiteMapsActivity>()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSiteClick(site: SiteModel) {
        startActivityForResult(intentFor<ArchFieldworkActivity>().putExtra("site_edit", site),0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadSites()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadSites(){
        showSites(app.sites.findAll())
    }

    fun showSites (sites: List<SiteModel>){
        recyclerView.adapter = ArchFieldworkAdapter(sites, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}


