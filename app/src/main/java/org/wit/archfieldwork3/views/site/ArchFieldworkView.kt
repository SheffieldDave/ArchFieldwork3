package org.wit.archfieldwork3.views.site

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_archfieldwork.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.archfieldwork3.R
import org.wit.archfieldwork3.helpers.readImageFromPath
import org.wit.archfieldwork3.models.SiteModel
import org.wit.archfieldwork3.views.BaseView


class ArchFieldworkView : BaseView(), AnkoLogger {


    lateinit var presenter: ArchFieldworkPresenter
    var site = SiteModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archfieldwork)

        init(toolbarAdd)

        presenter = initPresenter (ArchFieldworkPresenter(this)) as ArchFieldworkPresenter

        /*btnAddSite.setOnClickListener {
            if (siteName.text.toString().isEmpty()) {
                toast(R.string.enter_site_name_discription)
            } else {
                presenter.doAddOrSave(siteName.text.toString(), siteDescription.text.toString())
            }
        }*/

        btnChooseImage.setOnClickListener { presenter.doSelectImage() }

        btnAddLocation.setOnClickListener { presenter.doSetLocation() }
    }

    override fun showSite(site: SiteModel) {
        siteName.setText(site.name)
        siteDescription.setText(site.description)
        siteImage.setImageBitmap(readImageFromPath(this, site.image))
        if (site.image != null) {
            btnChooseImage.setText(R.string.change_site_image)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_site, menu)
        //if (presenter.edit) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_save -> {
                if (siteName.text.toString().isEmpty()) {
                    toast(R.string.enter_site_name_discription)
                } else {
                    presenter.doAddOrSave(siteName.text.toString(), siteDescription.text.toString())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)

        }
    }

    override fun onBackPressed() {
        presenter.doCancel()
    }
}