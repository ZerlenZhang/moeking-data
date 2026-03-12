package com.moeking.data.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moeking.data.models.Team
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 队伍ViewModel
 */
class TeamViewModel(
    private val repository: TeamRepository
) : ViewModel() {

    // 所有队伍
    private val _teams = MutableStateFlow<List<Team>>(emptyList())
    val teams: StateFlow<List<Team>> = _teams

    // 推荐队伍
    private val _recommendedTeams = MutableStateFlow<List<Team>>(emptyList())
    val recommendedTeams: StateFlow<List<Team>> = _recommendedTeams

    // 搜索查询
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        loadTeams()
        loadRecommendedTeams()
    }

    /**
     * 加载所有队伍
     */
    fun loadTeams() {
        viewModelScope.launch {
            repository.getAllTeams().collect { teams ->
                _teams.value = teams
            }
        }
    }

    /**
     * 加载推荐队伍
     */
    fun loadRecommendedTeams() {
        viewModelScope.launch {
            repository.getRecommendedTeams().collect { teams ->
                _recommendedTeams.value = teams
            }
        }
    }

    /**
     * 搜索队伍
     */
    fun search(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isBlank()) {
                loadTeams()
            } else {
                _teams.value = repository.searchTeams(query)
            }
        }
    }

    /**
     * 重置搜索
     */
    fun resetSearch() {
        _searchQuery.value = ""
        loadTeams()
    }

    /**
     * 获取推荐队伍
     */
    fun getRecommended() {
        loadRecommendedTeams()
    }

    /**
     * 创建新队伍
     */
    fun createTeam(name: String, characters: List<Character>, recommended: Boolean = false) {
        val team = Team(
            id = java.util.UUID.randomUUID().toString(),
            name = name,
            characters = characters,
            recommended = recommended
        )
        viewModelScope.launch {
            repository.insertTeam(team)
            loadTeams()
        }
    }

    /**
     * 更新队伍
     */
    fun updateTeam(team: Team) {
        viewModelScope.launch {
            repository.updateTeam(team)
            loadTeams()
        }
    }

    /**
     * 删除队伍
     */
    fun deleteTeam(team: Team) {
        viewModelScope.launch {
            repository.deleteTeam(team)
            loadTeams()
        }
    }
}
