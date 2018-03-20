package com.zjw.mykotlinproject

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        toolbar.setTitleTextColor(Color.GREEN)
        toolbar.title = "AppBarLayout"
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerview.adapter = ContentAdapter()

        //通过CollapsingToolbarLayout设置title
        collapsing_toolbar_layout.title = "AppBarLayout"
        //通过CollapsingToolbarLayout修改字体颜色
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE)//设置还没收缩时状态下字体颜色
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.RED)//设置收缩后Toolbar上字体的颜色

    }

    private inner class ContentAdapter : RecyclerView.Adapter<ContentAdapter.ContentHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentAdapter.ContentHolder {
            return ContentHolder(LayoutInflater.from(this@MainActivity).inflate(android.R.layout.simple_list_item_1, parent, false))
        }

        override fun onBindViewHolder(holder: ContentAdapter.ContentHolder, position: Int) {
            holder.itemTv.text = "item"
        }

        override fun getItemCount(): Int {
            return 35
        }

        internal inner class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val itemTv: TextView = itemView.findViewById<View>(android.R.id.text1) as TextView

        }
    }
}
