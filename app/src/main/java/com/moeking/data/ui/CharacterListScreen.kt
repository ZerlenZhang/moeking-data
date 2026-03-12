package com.moeking.data.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moeking.data.models.Character
import com.moeking.data.models.ElementType
import com.moeking.data.models.WeaponType
import com.moeking.data.utils.CharacterViewModel

/**
 * 角色列表屏幕
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    viewModel: CharacterViewModel = viewModel()
) {
    val characters by viewModel.characters.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("萌王数据") },
                actions = {
                    IconButton(onClick = { viewModel.resetFilters() }) {
                        Icon(Icons.Default.Search, contentDescription = "重置筛选")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 搜索栏
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.search(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("搜索角色...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                }
            )

            // 元素筛选
            ElementFilterChipRow(
                selected = null,
                onElementSelected = { viewModel.filterByElement(it) }
            )

            // 武器类型筛选
            WeaponTypeFilterChipRow(
                selected = null,
                onWeaponTypeSelected = { viewModel.filterByWeaponType(it) }
            )

            // 稀有度筛选
            RarityFilterChipRow(
                selected = null,
                onRaritySelected = { viewModel.filterByRarity(it) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 角色列表
            if (characters.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("暂无数据")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(characters) { character ->
                        CharacterItem(character)
                    }
                }
            }
        }
    }
}

/**
 * 角色筛选芯片行
 */
@Composable
fun ElementFilterChipRow(
    selected: ElementType?,
    onElementSelected: (ElementType?) -> Unit
) {
    val elements = ElementType.values().toList()

    FilterChipRow(
        items = elements,
        selected = selected,
        onSelect = onElementSelected
    )
}

/**
 * 武器类型筛选芯片行
 */
@Composable
fun WeaponTypeFilterChipRow(
    selected: WeaponType?,
    onWeaponTypeSelected: (WeaponType?) -> Unit
) {
    val weaponTypes = WeaponType.values().toList()

    FilterChipRow(
        items = weaponTypes,
        selected = selected,
        onSelect = onWeaponTypeSelected
    )
}

/**
 * 稀有度筛选芯片行
 */
@Composable
fun RarityFilterChipRow(
    selected: Int?,
    onRaritySelected: (Int?) -> Unit
) {
    val rarities = listOf(1, 2, 3, 4, 5, 6)

    FilterChipRow(
        items = rarities,
        selected = selected,
        onSelect = onRaritySelected
    )
}

/**
 * 通用筛选芯片行
 */
@Composable
private fun <T> FilterChipRow(
    items: List<T>,
    selected: T?,
    onSelect: (T?) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items.forEach { item ->
            FilterChip(
                selected = selected == item,
                onClick = { onSelect(if (selected == item) null else item) },
                label = {
                    when (item) {
                        is ElementType -> Text(item.displayName)
                        is WeaponType -> Text(item.displayName)
                        is Int -> Text("★".repeat(item))
                        else -> Text(item.toString())
                    }
                }
            )
        }
    }
}

/**
 * 角色项
 */
@Composable
fun CharacterItem(character: Character) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* 跳转详情页 */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 角色图片占位符
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = character.element.color.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = character.name.take(1),
                    style = MaterialTheme.typography.headlineMedium,
                    color = character.element.color
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 角色信息
            Column {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${character.element.displayName} · ${character.weaponType.displayName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = character.getStarString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )

                // 最佳评级
                character.getBestRating()?.let { rating ->
                    Text(
                        text = "${rating.game}: ${rating.tier}",
                        style = MaterialTheme.typography.bodySmall,
                        color = rating.tier
                    )
                }
            }
        }
    }
}
