package org.wit.archfieldwork3.views.sitelist

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_site_list.*
import org.wit.archfieldwork3.R
import org.wit.archfieldwork3.models.SiteModel
import org.wit.archfieldwork3.views.BaseView

class SiteListView : BaseView(), SiteListener {


    lateinit var presenter: SiteListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_list)
        init(toolbarMain,false)

        presenter = initPresenter(SiteListPresenter(this)) as SiteListPresenter

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadSites()
    }

    override fun showSites(sites: List<SiteModel>) {
        recyclerView.adapter = ArchFieldworkAdapter(sites, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> presenter.doAddSite()
            R.id.item_map -> presenter.doShowSitesMap()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSiteClick(placemark: SiteModel) {
        presenter.doEditSites(placemark)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadSites()
        super.onActivityResult(requestCode, resultCode, data)
    }
}
