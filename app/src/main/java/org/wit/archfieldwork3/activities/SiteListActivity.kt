package org.wit.archfieldwork3.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import kotlinx.android.synthetic.main.activity_site_list.*
import kotlinx.android.synthetic.main.card_site.view.*
import org.jetbrains.anko.startActivityForResult
import org.wit.archfieldwork3.R
import org.wit.archfieldwork3.main.MainApp
import org.wit.archfieldwork3.models.SiteModel

class SiteListActivity : AppCompatActivity() {

    lateinit var app:MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_list)
        app = application as MainApp

        toolbarMain.title = title
        setSupportActionBar(toolbarMain)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = ArchFieldworkAdapter(app.sites)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.item_add-> startActivityForResult<ArchFieldworkActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }
}

class ArchFieldworkAdapter constructor(private var sites: List<SiteModel>): RecyclerView.Adapter<ArchFieldworkAdapter.MainHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_site,parent,false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val site = sites[holder.adapterPosition]
        holder.bind(site)
    }

    override fun getItemCount(): Int = sites.size

    class MainHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(site: SiteModel){
            itemView.siteName.text = site.name
            itemView.siteDescription.text = site.description
        }
    }


}
