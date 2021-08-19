package com.diehard04.workllama.data.network

import com.diehard04.workllama.data.model.ContactModel
import com.diehard04.workllama.data.model.ContentIsStarModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by DieHard_04 on 14-08-2021.
 */
interface ApiService {

    @GET("v1/contacts")
    suspend fun getUsers(@Query("pageNumber") pageNumber:String ): ContactModel

    @POST("v1/star/{id}")
    suspend fun updateMakeStar(@Path("id") id:String): ContentIsStarModel

    @POST("v1/unstar/{id}")
    suspend fun updateMakeUnStar(@Path("id") id:String): ContentIsStarModel

}