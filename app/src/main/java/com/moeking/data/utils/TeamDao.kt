package com.moeking.data.utils

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moeking.data.models.Team
import kotlinx.coroutines.flow.Flow

/**
 * 队伍数据访问对象
 */
@Dao
interface TeamDao {

    /**
     * 获取所有队伍
     */
    @Query("SELECT * FROM teams")
    fun getAll(): Flow<List<Team>>

    /**
     * 根据ID获取队伍
     */
    @Query("SELECT * FROM teams WHERE id = :id")
    suspend fun getById(id: String): Team?

    /**
     * 获取所有推荐队伍
     */
    @Query("SELECT * FROM teams WHERE recommended = 1")
    fun getRecommended(): Flow<List<Team>>

    /**
     * 搜索队伍
     */
    @Query("SELECT * FROM teams WHERE name LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<Team>

    /**
     * 插入队伍
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(teams: List<Team>)

    /**
     * 插入单个队伍
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: Team)

    /**
     * 更新队伍
     */
    @Update
    suspend fun update(team: Team)

    /**
     * 删除队伍
     */
    @Delete
    suspend fun delete(team: Team)

    /**
     * 删除所有队伍
     */
    @Query("DELETE FROM teams")
    suspend fun deleteAll()

    /**
     * 删除推荐队伍
     */
    @Query("DELETE FROM teams WHERE recommended = 1")
    suspend fun deleteRecommended()

    /**
     * 检查队伍是否存在
     */
    @Query("SELECT COUNT(*) FROM teams WHERE id = :id")
    suspend fun exists(id: String): Int
}
