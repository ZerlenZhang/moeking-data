package com.moeking.data.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moeking.data.models.Character
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 角色ViewModel
 */
class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    // 所有角色
    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    // 搜索查询
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // 筛选条件
    private val _elementFilter = MutableStateFlow<ElementType?>(null)
    val elementFilter: StateFlow<ElementType?> = _elementFilter

    private val _weaponTypeFilter = MutableStateFlow<WeaponType?>(null)
    val weaponTypeFilter: StateFlow<WeaponType?> = _weaponTypeFilter

    private val _rarityFilter = MutableStateFlow<Int?>(null)
    val rarityFilter: StateFlow<Int?> = _rarityFilter

    init {
        loadCharacters()
    }

    /**
     * 加载所有角色
     */
    fun loadCharacters() {
        viewModelScope.launch {
            repository.getAllOrderedByRarity().collect { characters ->
                _characters.value = characters
            }
        }
    }

    /**
     * 搜索角色
     */
    fun search(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            if (query.isBlank()) {
                loadCharacters()
            } else {
                _characters.value = repository.searchCharacters(query)
            }
        }
    }

    /**
     * 按元素筛选
     */
    fun filterByElement(element: ElementType?) {
        _elementFilter.value = element
        applyFilters()
    }

    /**
     * 按武器类型筛选
     */
    fun filterByWeaponType(weaponType: WeaponType?) {
        _weaponTypeFilter.value = weaponType
        applyFilters()
    }

    /**
     * 按稀有度筛选
     */
    fun filterByRarity(rarity: Int?) {
        _rarityFilter.value = rarity
        applyFilters()
    }

    /**
     * 应用所有筛选条件
     */
    private fun applyFilters() {
        viewModelScope.launch {
            when {
                _searchQuery.value.isNotBlank() && _elementFilter.value != null -> {
                    _characters.value = repository.getCharactersByElement(_elementFilter.value!!.name)
                }
                _searchQuery.value.isNotBlank() && _weaponTypeFilter.value != null -> {
                    _characters.value = repository.getCharactersByWeaponType(_weaponTypeFilter.value!!.name)
                }
                _searchQuery.value.isNotBlank() && _rarityFilter.value != null -> {
                    _characters.value = repository.getCharactersByRarity(_rarityFilter.value!!)
                }
                else -> {
                    loadCharacters()
                }
            }
        }
    }

    /**
     * 重置所有筛选条件
     */
    fun resetFilters() {
        _searchQuery.value = ""
        _elementFilter.value = null
        _weaponTypeFilter.value = null
        _rarityFilter.value = null
        loadCharacters()
    }
}
