package com.example.controlevanescolar.api

import com.example.controlevanescolar.model.Endereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepApiService {
    @GET("{cep}/json/")
    fun getEndereco(@Path("cep") cep: String): Call<Endereco>
}