package com.CM.myapplication.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @DELETE("registos/{id}")
    fun deleteRegisto(
            @Path("id") id: Int,
            @Query("utilizador_id") utilizador_id: Int
    ): Call<OpRegisto>

    @Multipart
    @POST("registos")
    fun criarRegisto(
        @Part("nome") nome: RequestBody,
        @Part("descricao") descricao: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("id") id: RequestBody,
        @Part("tipo") tipo: RequestBody,
        @Part imagem: MultipartBody.Part
    ): Call<OpRegisto>

    @FormUrlEncoded
    @PUT("registos/{id}")
    fun updateRegisto(
        @Path("id") id: Int,
        @Field("nome") nome: String,
        @Field("descricao") descricao: String,
        @Field("utilizador_id") utilizador_id: Int,
    ): Call<OpRegisto>

}