package org.wit.archfieldwork3

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_archfieldwork.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class ArchFieldworkActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archfieldwork)
        info("ArchFieldwork started...")

        btnAddSite.setOnClickListener(){
            val siteName = siteName.text.toString()
            val siteDiscription = siteDescription.text.toString()
            if (siteName.isNotEmpty()|| siteDiscription.isNotEmpty()){
                toast("add site pressed: Name and Discription done")
            }else{
                toast("please Enter a Name and Discription")
            }
        }

        btnAddImage.setOnClickListener(){
                toast("add Image pressed")
        }


    }
}
