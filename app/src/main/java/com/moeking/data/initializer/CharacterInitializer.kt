package com.moeking.data.initializer

import android.content.Context
import com.moeking.data.models.*
import com.moeking.data.utils.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * 角色数据初始化器
 */
class CharacterInitializer(
    private val context: Context,
    private val database: AppDatabase
) {

    /**
     * 初始化角色数据
     */
    suspend fun initialize() = withContext(Dispatchers.IO) {
        // 检查是否已经初始化
        val characterDao = database.characterDao()
        if (characterDao.exists("amber") > 0) {
            return@withContext
        }

        // 加载JSON数据
        val characters = loadCharactersFromJson()

        // 插入数据库
        characterDao.insertAll(characters)

        println("成功初始化 ${characters.size} 个角色")
    }

    /**
     * 从JSON文件加载角色数据
     */
    private fun loadCharactersFromJson(): List<Character> {
        val characters = mutableListOf<Character>()

        try {
            val inputStream = context.assets.open("characters.json")
            val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            val jsonText = reader.readText()
            reader.close()

            val jsonArray = JSONArray(jsonText)

            for (i in 0 until jsonArray.length()) {
                val json = jsonArray.getJSONObject(i)

                val character = Character(
                    id = json.getString("id"),
                    name = json.getString("name"),
                    element = ElementType.valueOf(json.getString("element")),
                    rarity = json.getInt("rarity"),
                    weaponType = WeaponType.valueOf(json.getString("weaponType")),
                    stats = CharacterStats(
                        hp = json.getJSONObject("stats").getInt("hp"),
                        atk = json.getJSONObject("stats").getInt("atk"),
                        def = json.getJSONObject("stats").getInt("def"),
                        speed = json.getJSONObject("stats").getInt("speed"),
                        subStats = emptyList()
                    ),
                    skills = loadSkills(json.getJSONArray("skills")),
                    ratings = loadRatings(json.getJSONArray("ratings")),
                    imageUrl = json.optString("imageUrl", ""),
                    description = json.optString("description", "")
                )

                characters.add(character)
            }
        } catch (e: Exception) {
            println("加载角色数据失败: ${e.message}")
        }

        return characters
    }

    /**
     * 加载技能列表
     */
    private fun loadSkills(jsonArray: JSONArray): List<Skill> {
        val skills = mutableListOf<Skill>()

        for (i in 0 until jsonArray.length()) {
            val json = jsonArray.getJSONObject(i)

            skills.add(Skill(
                id = json.getString("id"),
                name = json.getString("name"),
                type = SkillType.valueOf(json.getString("type")),
                description = json.getString("description"),
                level = 1
            ))
        }

        return skills
    }

    /**
     * 加载评级列表
     */
    private fun loadRatings(jsonArray: JSONArray): List<Rating> {
        val ratings = mutableListOf<Rating>()

        for (i in 0 until jsonArray.length()) {
            val json = jsonArray.getJSONObject(i)

            ratings.add(Rating(
                game = json.getString("game"),
                tier = json.getString("tier"),
                reason = json.optString("reason", "")
            ))
        }

        return ratings
    }
}
