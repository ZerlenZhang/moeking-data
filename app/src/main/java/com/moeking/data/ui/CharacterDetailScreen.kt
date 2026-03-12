package com.moeking.data.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.moeking.data.models.Skill
import com.moeking.data.models.SkillType
import com.moeking.data.models.StatType
import com.moeking.data.models.WeaponType

/**
 * 角色详情屏幕
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    characterId: String,
    navController: NavController
) {
    // 这里应该是从Repository获取角色数据
    // 为了演示，使用示例数据
    val character = Character(
        id = characterId,
        name = "安柏",
        element = ElementType.FIRE,
        rarity = 4,
        weaponType = WeaponType.BOW,
        stats = com.moeking.data.models.CharacterStats(
            hp = 1000,
            atk = 60,
            def = 50,
            speed = 100,
            subStats = listOf(
                com.moeking.data.models.SubStat(StatType.HP, 5.0f),
                com.moeking.data.models.SubStat(StatType.ATK, 3.0f)
            )
        ),
        skills = listOf(
            Skill("1", "普通攻击·羽箭", SkillType.NORMAL_ATTACK, "安柏的普通攻击，使用弓箭进行射击。", 1),
            Skill("2", "元素战技·重击", SkillType.ELEMENTAL_SKILL, "安柏的元素战技，可以进行连续射击并造成火元素伤害。", 1),
            Skill("3", "元素爆发·旋火轮", SkillType.ELEMENTAL_BURST, "安柏的元素爆发，召唤旋火轮对周围敌人造成火元素范围伤害。", 1)
        ),
        ratings = listOf(
            com.moeking.data.models.Rating("原神", "T4", "前期辅助角色，后期可用")
        ),
        imageUrl = "",
        description = "蒙德城的侦察骑士，擅长使用弓箭和火元素。"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(character.name) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 角色基本信息
            CharacterInfoSection(character)

            // 属性面板
            StatsSection(character.stats)

            // 技能列表
            SkillsSection(character.skills)

            // 评级
            RatingSection(character.ratings)

            // 描述
            DescriptionSection(character.description)
        }
    }
}

/**
 * 角色信息部分
 */
@Composable
private fun CharacterInfoSection(character: Character) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 角色图片占位符
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        color = character.element.color.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = character.element.color
                    )
                    Text(
                        text = character.getStarString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = character.element.color
                    )
                }
            }

            // 角色标签
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = true,
                    onClick = {},
                    label = { Text(character.element.displayName) },
                    leadingIcon = {
                        Icon(
                            imageVector = null,
                            contentDescription = null,
                            tint = character.element.color
                        )
                    }
                )
                FilterChip(
                    selected = true,
                    onClick = {},
                    label = { Text(character.weaponType.displayName) }
                )
            }
        }
    }
}

/**
 * 属性部分
 */
@Composable
private fun StatsSection(stats: com.moeking.data.models.CharacterStats) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "属性",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            AttributeRow("生命", stats.hp)
            AttributeRow("攻击", stats.atk)
            AttributeRow("防御", stats.def)
            AttributeRow("速度", stats.speed)

            if (stats.subStats.isNotEmpty()) {
                Divider()
                Text(
                    text = "副属性",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                stats.subStats.forEach { subStat ->
                    AttributeRow(subStat.statType.displayName, subStat.value.toInt())
                }
            }
        }
    }
}

/**
 * 属性行
 */
@Composable
private fun AttributeRow(label: String, value: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label)
        Text(text = value.toString())
    }
}

/**
 * 技能部分
 */
@Composable
private fun SkillsSection(skills: List<Skill>) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "技能",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            skills.forEach { skill ->
                SkillItem(skill)
            }
        }
    }
}

/**
 * 技能项
 */
@Composable
private fun SkillItem(skill: Skill) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = skill.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = skill.type.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Text(
                text = skill.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

/**
 * 评级部分
 */
@Composable
private fun RatingSection(ratings: List<com.moeking.data.models.Rating>) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "评级",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            ratings.forEach { rating ->
                RatingItem(rating)
            }
        }
    }
}

/**
 * 评级项
 */
@Composable
private fun RatingItem(rating: com.moeking.data.models.Rating) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = rating.game)
        Text(
            text = rating.tier,
            fontWeight = FontWeight.Bold,
            color = getTierColor(rating.tier)
        )
    }
}

/**
 * 获取评级颜色
 */
private fun getTierColor(tier: String): Color {
    return when (tier) {
        "T0" -> Color(0xFFFFD700) // 金色
        "T1" -> Color(0xFFFF4500) // 橙色
        "T2" -> Color(0xFFFF6347) // 番茄红
        "T3" -> Color(0xFFFFA500) // 橙色
        "T4" -> Color(0xFF808080) // 灰色
        "T5" -> Color(0xFFC0C0C0) // 银色
        else -> Color.Gray
    }
}

/**
 * 描述部分
 */
@Composable
private fun DescriptionSection(description: String) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "描述",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(text = description)
        }
    }
}
