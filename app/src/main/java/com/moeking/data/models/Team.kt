package com.moeking.data.models

/**
 * 元素反应类型
 */
enum class ReactionType(val displayName: String, val description: String) {
    OVERLOAD("超载", "火+雷，爆炸伤害"),
    MELT("融化", "火+冰，双倍伤害"),
    VAPORIZE("蒸发", "水+火，双倍伤害"),
    ELECTRO_CHARGE("感电", "雷+水，持续伤害"),
    SUPERCONDUCTOR("超导", "雷+冰，减抗"),
    SWIRL("扩散", "风+其他元素，扩散伤害"),
    FROZEN("冻结", "水+冰，控制敌人"),
    SHATTER("破碎", "冰+物理，额外伤害"),
    QUICKEN("激化", "草+雷，增伤"),
    BLOOM("绽放", "草+水，生成种子"),
    HYPERBLOOM("超绽放", "草+水+雷，高伤害"),
    AGGRAVATE("蔓激化", "草+雷，增伤"),
    NONE("无", "无元素反应")
}

/**
 * 队伍推荐理由
 */
data class TeamRecommendation(
    val team: Team,
    val reactionType: ReactionType,
    val reasoning: String,
    val reactionDamage: Double
)

/**
 * 队伍数据模型
 */
data class Team(
    val id: String,
    val name: String,
    val characters: List<Character>,
    val recommended: Boolean = false,
    val reactionType: ReactionType? = null
) {
    /**
     * 获取队伍中使用的元素
     */
    fun getUsedElements(): List<ElementType> {
        return characters.map { it.element }.distinct()
    }

    /**
     * 获取队伍中的角色数量
     */
    fun getCharacterCount(): Int = characters.size

    /**
     * 检查队伍是否完整（假设4人队伍）
     */
    fun isComplete(): Boolean = characters.size >= 4

    /**
     * 获取队伍总攻击力
     */
    fun getTotalAtk(): Int = characters.sumOf { it.stats.atk }

    /**
     * 获取队伍总生命值
     */
    fun getTotalHp(): Int = characters.sumOf { it.stats.hp }
}
