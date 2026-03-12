package com.moeking.data.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moeking.data.models.Character
import com.moeking.data.models.ElementType
import com.moeking.data.models.ReactionType
import com.moeking.data.utils.ReactionCalculator

/**
 * 元素反应演示屏幕 - 惊喜功能！🎉
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReactionDemoScreen(
    navController: NavController
) {
    val calculator = remember { ReactionCalculator() }

    // 当前选中的元素
    var selectedElements by remember { mutableStateOf<Set<ElementType>>(emptySet()) }

    // 当前队伍
    val currentTeam = remember(selectedElements) {
        selectedElements.mapNotNull { elementType ->
            // 简单映射：根据元素返回一个示例角色
            when (elementType) {
                ElementType.FIRE -> Character(
                    id = "demo_fire",
                    name = "测试角色",
                    element = ElementType.FIRE,
                    rarity = 5,
                    weaponType = com.moeking.data.models.WeaponType.SWORD,
                    stats = com.moeking.data.models.CharacterStats(1000, 100, 100, 100, emptyList()),
                    skills = emptyList(),
                    ratings = emptyList(),
                    imageUrl = "",
                    description = "火元素角色"
                )
                ElementType.WATER -> Character(
                    id = "demo_water",
                    name = "测试角色",
                    element = ElementType.WATER,
                    rarity = 5,
                    weaponType = com.moeking.data.models.WeaponType.CATALYST,
                    stats = com.moeking.data.models.CharacterStats(1000, 100, 100, 100, emptyList()),
                    skills = emptyList(),
                    ratings = emptyList(),
                    imageUrl = "",
                    description = "水元素角色"
                )
                ElementType.ELECTRIC -> Character(
                    id = "demo_electric",
                    name = "测试角色",
                    element = ElementType.ELECTRIC,
                    rarity = 5,
                    weaponType = com.moeking.data.models.WeaponType.POLEARM,
                    stats = com.moeking.data.models.CharacterStats(1000, 100, 100, 100, emptyList()),
                    skills = emptyList(),
                    ratings = emptyList(),
                    imageUrl = "",
                    description = "雷元素角色"
                )
                ElementType.WIND -> Character(
                    id = "demo_wind",
                    name = "测试角色",
                    element = ElementType.WIND,
                    rarity = 5,
                    weaponType = com.moeking.data.models.WeaponType.POLEARM,
                    stats = com.moeking.data.models.CharacterStats(1000, 100, 100, 100, emptyList()),
                    skills = emptyList(),
                    ratings = emptyList(),
                    imageUrl = "",
                    description = "风元素角色"
                )
                ElementType.ICE -> Character(
                    id = "demo_ice",
                    name = "测试角色",
                    element = ElementType.ICE,
                    rarity = 5,
                    weaponType = com.moeking.data.models.WeaponType.SWORD,
                    stats = com.moeking.data.models.CharacterStats(1000, 100, 100, 100, emptyList()),
                    skills = emptyList(),
                    ratings = emptyList(),
                    imageUrl = "",
                    description = "冰元素角色"
                )
                ElementType.GEO -> Character(
                    id = "demo_geo",
                    name = "测试角色",
                    element = ElementType.GEO,
                    rarity = 5,
                    weaponType = com.moeking.data.models.WeaponType.POLEARM,
                    stats = com.moeking.data.models.CharacterStats(1000, 100, 100, 100, emptyList()),
                    skills = emptyList(),
                    ratings = emptyList(),
                    imageUrl = "",
                    description = "岩元素角色"
                )
                ElementType.GRASS -> Character(
                    id = "demo_grass",
                    name = "测试角色",
                    element = ElementType.GRASS,
                    rarity = 5,
                    weaponType = com.moeking.data.models.WeaponType.CATALYST,
                    stats = com.moeking.data.models.CharacterStats(1000, 100, 100, 100, emptyList()),
                    skills = emptyList(),
                    ratings = emptyList(),
                    imageUrl = "",
                    description = "草元素角色"
                }
                else -> null
            }
        }.toList()
    }

    // 当前反应
    val currentReaction = remember(currentElements = selectedElements) {
        if (currentTeam.size >= 2) {
            calculator.calculateBestReaction(com.moeking.data.models.Team(
                id = "demo",
                name = "测试队伍",
                characters = currentTeam,
                recommended = true,
                reactionType = null
            ))
        } else {
            ReactionType.NONE
        }
    }

    // 伤害倍率
    val damageMultiplier = remember(currentElements = selectedElements) {
        if (currentTeam.size >= 2) {
            calculator.calculateDamageMultiplier(com.moeking.data.models.Team(
                id = "demo",
                name = "测试队伍",
                characters = currentTeam,
                recommended = true,
                reactionType = null
            ))
        } else {
            1.0
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("元素反应演示 🎉") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 欢迎语
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFE4E1)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "🌟 惊喜功能：元素反应计算器！",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF4500)
                    )
                    Text(
                        text = "选择2个或以上元素，看看会发生什么反应！",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // 当前队伍
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "当前队伍",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    if (currentTeam.isEmpty()) {
                        Text(
                            text = "点击下方元素添加角色",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.height(120.dp)
                        ) {
                            items(currentTeam) { character ->
                                ElementChip(
                                    element = character.element,
                                    selected = true,
                                    onClick = {}
                                )
                            }
                        }
                    }
                }
            }

            // 元素选择
            Text(
                text = "选择元素",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ElementType.values()) { element ->
                    ElementChip(
                        element = element,
                        selected = selectedElements.contains(element),
                        onClick = {
                            selectedElements = if (selectedElements.contains(element)) {
                                selectedElements - element
                            } else {
                                selectedElements + element
                            }
                        }
                    )
                }
            }

            // 反应结果
            if (selectedElements.size >= 2) {
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = when (currentReaction) {
                            ReactionType.MELT -> Color(0xFFFF6B6B)
                            ReactionType.VAPORIZE -> Color(0xFF4ECDC4)
                            ReactionType.OVERLOAD -> Color(0xFFFFD93D)
                            ReactionType.ELECTRO_CHARGE -> Color(0xFFFF6B9D)
                            ReactionType.SUPERCONDUCTOR -> Color(0xFF95E1D3)
                            ReactionType.SWIRL -> Color(0xFFA8E6CF)
                            ReactionType.FROZEN -> Color(0xFF74B9FF)
                            ReactionType.SHATTER -> Color(0xFFFFA502)
                            ReactionType.QUICKEN -> Color(0xFF6C5CE7)
                            ReactionType.BLOOM -> Color(0xFFFD79A8)
                            ReactionType.HYPERBLOOM -> Color(0xFFFF7675)
                            ReactionType.AGGRAVATE -> Color(0xFF00B894)
                            else -> Color(0xFFB2BEC3)
                        }
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "✨ 元素反应",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = calculator.getReactionDescription(currentReaction),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Divider(color = Color.White.copy(alpha = 0.3f))

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "伤害倍率",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "${damageMultiplier}x",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = if (damageMultiplier >= 2.0) "🔥 爆发伤害！" else if (damageMultiplier >= 1.5) "💥 高伤害！" else if (damageMultiplier >= 1.3) "⚡ 增伤！" else "✨ 稳定输出",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(16.dp))

                Card {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "💡 提示",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "至少选择2个元素来触发元素反应",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // 常见反应列表
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "常见反应",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    ReactionList(
                        reaction = ReactionType.MELT,
                        description = "融化：火+冰，双倍伤害"
                    )
                    ReactionList(
                        reaction = ReactionType.VAPORIZE,
                        description = "蒸发：水+火，双倍伤害"
                    )
                    ReactionList(
                        reaction = ReactionType.OVERLOAD,
                        description = "超载：雷+火，爆炸伤害"
                    )
                    ReactionList(
                        reaction = ReactionType.ELECTRO_CHARGE,
                        description = "感电：雷+水，持续伤害"
                    )
                    ReactionList(
                        reaction = ReactionType.SUPERCONDUCTOR,
                        description = "超导：雷+冰，减抗"
                    )
                }
            }
        }
    }
}

/**
 * 元素芯片
 */
@Composable
private fun ElementChip(
    element: ElementType,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = onClick)
            .then(
                if (selected) Modifier
                    .border(
                        width = 3.dp,
                        color = element.color,
                        shape = RoundedCornerShape(12.dp)
                    )
                else Modifier
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) element.color.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 6.dp else 2.dp
        )
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = element.color,
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = element.displayName,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * 反应列表项
 */
@Composable
private fun ReactionList(
    reaction: ReactionType,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = reaction.displayName,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        if (reaction != ReactionType.NONE) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
