package com.CM.myapplication.api

import retrofit2.Call
import retrofit2.http.*

interface EndPoints {

    @FormUrlEncoded
    @POST("login")
    fun login(
            @Field("username") username: String,
            @Field("password") password: String
    ): Call<Utilizador>

    @GET("registos")
    fun getRegistos(
            @Query("tipo") tipo: String?
    ): Call<List<Registo>>

    @DELETE("api/reports/{id}")
    fun deleteReport(
            @Path("id") id: Int,
            @Query("username") username: String
    ): Call<OpRegisto>

}