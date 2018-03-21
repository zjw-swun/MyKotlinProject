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
import android.widget.Toast
import com.bumptech.glide.Glide
import com.zjw.bean.MeiZiBean
import com.zjw.net.RetrofitServer
import com.zjw.utils.Constant
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit

    private var meiZiBean: MeiZiBean? = null

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

        retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl(Constant.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()

        val server = retrofit.create(RetrofitServer::class.java)
        val meiZi = server.getMeiZi("10", "1")

        doSubscribe(meiZi, object : Subscriber<MeiZiBean>() {
            override fun onNext(t: MeiZiBean?) {
                meiZiBean = t
            }

            override fun onCompleted() {
                //请求成功
                meiZiBean?.let {
                    Glide.with(this@MainActivity)
                        .load(meiZiBean!!.results!![0].url)
                        .into(image)
                }
            }

            override fun onError(e: Throwable?) {
                //请求失败
                Toast.makeText(this@MainActivity, "下载失败", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun <T> doSubscribe(observable: Observable<T>, subscriber: Subscriber<T>): Subscription =
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber)

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
