package com.CM.myapplication.api

data class Utilizador(
        val id: Int?,
        val username: String?,
        val error: String?
)

data class Registo(
        val id: Int,
        val nome: String,
        val descricao: String,
        val latitude: Double,
        val longitude: Double,
        val utilizador_id: Int,
        val tipo: String,
        val imagem: String
)

data class OpRegisto(
        val error: String?,
        val result: String?
)