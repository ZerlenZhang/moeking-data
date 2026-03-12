package com.moeking.data.models

import androidx.compose.ui.graphics.Color

/**
 * 元素类型
 */
enum class ElementType(
    val displayName: String,
    val color: Color,
    val description: String
) {
    FIRE("火", Color(0xFFFF4500), "火元素，造成火元素伤害"),
    WATER("水", Color(0xFF1E90FF), "水元素，造成水元素伤害"),
    ELECTRIC("雷", Color(0xFFFFD700), "雷元素，造成雷元素伤害"),
    WIND("风", Color(0xFF32CD32), "风元素，造成风元素伤害"),
    ICE("冰", Color(0xFF00FFFF), "冰元素，造成冰元素伤害"),
    GEO("岩", Color(0xFF8B4513), "岩元素，造成岩元素伤害"),
    GRASS("草", Color(0xFF32CD32), "草元素，造成草元素伤害");

    companion object {
        fun fromString(value: String): ElementType {
            return values().find { it.name == value } ?: FIRE
        }
    }
}
