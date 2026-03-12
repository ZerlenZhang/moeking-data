package com.moeking.data.utils

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moeking.data.models.Character
import kotlinx.coroutines.flow.Flow

/**
 * 角色数据访问对象
 */
@Dao
interface CharacterDao {

    /**
     * 获取所有角色
     */
    @Query("SELECT * FROM characters")
    fun getAll(): Flow<List<Character>>

    /**
     * 根据ID获取角色
     */
    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getById(id: String): Character?

    /**
     * 搜索角色（按名称）
     */
    @Query("SELECT * FROM characters WHERE name LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<Character>

    /**
     * 根据元素类型获取角色
     */
    @Query("SELECT * FROM characters WHERE element = :elementType")
    suspend fun getByElement(elementType: String): List<Character>

    /**
     * 根据武器类型获取角色
     */
    @Query("SELECT * FROM characters WHERE weaponType = :weaponType")
    suspend fun getByWeaponType(weaponType: String): List<Character>

    /**
     * 根据稀有度获取角色
     */
    @Query("SELECT * FROM characters WHERE rarity = :rarity")
    suspend fun getByRarity(rarity: Int): List<Character>

    /**
     * 获取按稀有度排序的角色
     */
    @Query("SELECT * FROM characters ORDER BY rarity DESC, name ASC")
    fun getAllOrderedByRarity(): Flow<List<Character>>

    /**
     * 插入角色
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Character>)

    /**
     * 插入单个角色
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: Character)

    /**
     * 更新角色
     */
    @Update
    suspend fun update(character: Character)

    /**
     * 删除角色
     */
    @Delete
    suspend fun delete(character: Character)

    /**
     * 删除所有角色
     */
    @Query("DELETE FROM characters")
    suspend fun deleteAll()

    /**
     * 检查角色是否存在
     */
    @Query("SELECT COUNT(*) FROM characters WHERE id = :id")
    suspend fun exists(id: String): Int
}
