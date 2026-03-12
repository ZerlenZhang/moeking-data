package com.moeking.data.models

import androidx.compose.ui.graphics.Color

/**
 * 武器类型
 */
enum class WeaponType(
    val displayName: String,
    val color: Color,
    val description: String
) {
    SWORD("单手剑", Color.White, "单手剑，攻速快，灵活性高"),
    BOW("弓", Color(0xFF8B4513), "弓，远程攻击，适合风筝"),
    CLAYMORE("双手剑", Color(0xFF8B0000), "双手剑，高伤害，攻速慢"),
    POLEARM("长柄武器", Color(0xFF006400), "长柄武器，范围攻击，攻速中等"),
    CATALYST("法器", Color(0xFFFF69B4), "法器，元素伤害，远程施法");

    companion object {
        fun fromString(value: String): WeaponType {
            return values().find { it.name == value } ?: SWORD
        }
    }
}
