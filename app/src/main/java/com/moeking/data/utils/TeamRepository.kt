package com.moeking.data.utils

import com.moeking.data.models.Team
import kotlinx.coroutines.flow.Flow

/**
 * 队伍仓库
 */
class TeamRepository(
    private val teamDao: TeamDao
) {

    /**
     * 获取所有队伍
     */
    fun getAllTeams(): Flow<List<Team>> {
        return teamDao.getAll()
    }

    /**
     * 根据ID获取队伍
     */
    suspend fun getTeamById(id: String): Team? {
        return teamDao.getById(id)
    }

    /**
     * 获取所有推荐队伍
     */
    fun getRecommendedTeams(): Flow<List<Team>> {
        return teamDao.getRecommended()
    }

    /**
     * 搜索队伍
     */
    suspend fun searchTeams(query: String): List<Team> {
        return teamDao.search(query)
    }

    /**
     * 插入队伍
     */
    suspend fun insertTeam(team: Team) {
        teamDao.insert(team)
    }

    /**
     * 插入队伍列表
     */
    suspend fun insertTeams(teams: List<Team>) {
        teamDao.insertAll(teams)
    }

    /**
     * 更新队伍
     */
    suspend fun updateTeam(team: Team) {
        teamDao.update(team)
    }

    /**
     * 删除队伍
     */
    suspend fun deleteTeam(team: Team) {
        teamDao.delete(team)
    }

    /**
     * 删除所有队伍
     */
    suspend fun deleteAllTeams() {
        teamDao.deleteAll()
    }

    /**
     * 删除推荐队伍
     */
    suspend fun deleteRecommendedTeams() {
        teamDao.deleteRecommended()
    }

    /**
     * 初始化队伍数据
     */
    suspend fun initializeTeams(teams: List<Team>) {
        if (teamDao.exists(teams.first().id) == 0) {
            teamDao.insertAll(teams)
        }
    }
}
