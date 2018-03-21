package com.zjw.net

import com.zjw.bean.MeiZiBean
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Desc :
 * date : 2018/3/21 22:08
 *
 * @author : zhoujiawei
 */
interface RetrofitServer {

    //http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1
    @GET("{count}/{page}")
    fun getMeiZi(@Path("count") count :String,@Path("page") page :String): Observable<MeiZiBean>
}