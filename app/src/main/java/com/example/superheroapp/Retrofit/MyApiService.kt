package com.example.superheroapp.Retrofit

import com.example.superheroapp.model.Superhero
import retrofit2.Response
import retrofit2.http.*


interface MyApiService {
    @GET("{id}")
    suspend fun getSuperHero(
        @Path(value = "id", encoded = true) id: Int?
    ): Response<Superhero>
}