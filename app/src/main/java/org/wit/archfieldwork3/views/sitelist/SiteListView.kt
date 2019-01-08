package org.wit.archfieldwork3.views.sitelist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_site_list.*
import org.wit.archfieldwork3.R
import org.wit.archfieldwork3.views.sitelist.ArchFieldworkAdapter
import org.wit.archfieldwork3.views.sitelist.SiteListener
import org.wit.archfieldwork3.models.SiteModel
import org.wit.archfieldwork3.views.sitelist.SiteListPresenter

class SiteListView : AppCompatActivity(), SiteListener {

    lateinit var presenter: SiteListPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_list)
        toolbarMain.title = title
        setSupportActionBar(toolbarMain)

        presenter = SiteListPresenter(this)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ArchFieldworkAdapter(presenter.getSites(), this)
        recyclerView.adapter?.notifyDataSetChanged()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.item_add-> presenter.doAddSite()
            R.id.item_map-> presenter.doShowSitesMap()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSiteClick(site: SiteModel) {
        presenter.doEditSite(site)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }




}


