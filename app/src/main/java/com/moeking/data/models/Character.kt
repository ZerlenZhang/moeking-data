package com.moeking.data.models

/**
 * 角色属性
 */
data class CharacterStats(
    val hp: Int,
    val atk: Int,
    val def: Int,
    val speed: Int,
    val subStats: List<SubStat>
)

/**
 * 副属性
 */
data class SubStat(
    val statType: StatType,
    val value: Float
)

/**
 * 属性类型
 */
enum class StatType(val displayName: String) {
    HP("生命"),
    ATK("攻击"),
    DEF("防御"),
    SPEED("速度"),
    ELEMENTAL_MASTERY("元素精通"),
    CRITICAL_RATE("暴击率"),
    CRITICAL_DAMAGE("暴击伤害"),
    RESISTANCE("抗性")
}

/**
 * 技能
 */
data class Skill(
    val id: String,
    val name: String,
    val type: SkillType,
    val description: String,
    val level: Int = 1
)

/**
 * 技能类型
 */
enum class SkillType(val displayName: String) {
    NORMAL_ATTACK("普通攻击"),
    ELEMENTAL_SKILL("元素战技"),
    ELEMENTAL_BURST("元素爆发"),
    PASSIVE_SKILL("被动技能")
}

/**
 * 评级
 */
data class Rating(
    val game: String,
    val tier: String,
    val reason: String
)

/**
 * 角色数据模型
 */
data class Character(
    val id: String,
    val name: String,
    val element: ElementType,
    val rarity: Int, // 1-6星
    val weaponType: WeaponType,
    val stats: CharacterStats,
    val skills: List<Skill>,
    val ratings: List<Rating>,
    val imageUrl: String,
    val description: String
) {
    /**
     * 获取星级星星
     */
    fun getStarString(): String {
        return "★".repeat(rarity)
    }

    /**
     * 获取所有评级中最好的
     */
    fun getBestRating(): Rating? {
        return ratings.maxByOrNull { getTierLevel(it.tier) }
    }

    private fun getTierLevel(tier: String): Int {
        val tierOrder = mapOf(
            "T0" to 5,
            "T1" to 4,
            "T2" to 3,
            "T3" to 2,
            "T4" to 1,
            "T5" to 0
        )
        return tierOrder[tier] ?: 0
    }
}
