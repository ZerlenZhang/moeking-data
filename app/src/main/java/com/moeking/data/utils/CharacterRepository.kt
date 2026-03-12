package com.moeking.data.utils

import com.moeking.data.models.Character
import kotlinx.coroutines.flow.Flow

/**
 * 角色仓库
 */
class CharacterRepository(
    private val characterDao: CharacterDao
) {

    /**
     * 获取所有角色
     */
    fun getAllCharacters(): Flow<List<Character>> {
        return characterDao.getAll()
    }

    /**
     * 根据ID获取角色
     */
    suspend fun getCharacterById(id: String): Character? {
        return characterDao.getById(id)
    }

    /**
     * 搜索角色
     */
    suspend fun searchCharacters(query: String): List<Character> {
        return characterDao.search(query)
    }

    /**
     * 根据元素类型获取角色
     */
    suspend fun getCharactersByElement(elementType: String): List<Character> {
        return characterDao.getByElement(elementType)
    }

    /**
     * 根据武器类型获取角色
     */
    suspend fun getCharactersByWeaponType(weaponType: String): List<Character> {
        return characterDao.getByWeaponType(weaponType)
    }

    /**
     * 根据稀有度获取角色
     */
    suspend fun getCharactersByRarity(rarity: Int): List<Character> {
        return characterDao.getByRarity(rarity)
    }

    /**
     * 获取按稀有度排序的角色
     */
    fun getAllOrderedByRarity(): Flow<List<Character>> {
        return characterDao.getAllOrderedByRarity()
    }

    /**
     * 插入角色
     */
    suspend fun insertCharacter(character: Character) {
        characterDao.insert(character)
    }

    /**
     * 插入角色列表
     */
    suspend fun insertCharacters(characters: List<Character>) {
        characterDao.insertAll(characters)
    }

    /**
     * 更新角色
     */
    suspend fun updateCharacter(character: Character) {
        characterDao.update(character)
    }

    /**
     * 删除角色
     */
    suspend fun deleteCharacter(character: Character) {
        characterDao.delete(character)
    }

    /**
     * 删除所有角色
     */
    suspend fun deleteAllCharacters() {
        characterDao.deleteAll()
    }

    /**
     * 初始化角色数据
     */
    suspend fun initializeCharacters(characters: List<Character>) {
        if (characterDao.exists(characters.first().id) == 0) {
            characterDao.insertAll(characters)
        }
    }
}
