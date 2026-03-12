package com.moeking.data.api

import com.moeking.data.models.Character
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 角色API接口
 */
interface CharacterApi {

    @GET("characters")
    suspend fun getCharacters(): List<Character>

    @GET("characters/{id}")
    suspend fun getCharacterById(@Path("id") id: String): Character?

    @GET("characters/search")
    suspend fun searchCharacters(@Path("query") query: String): List<Character>
}
