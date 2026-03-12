package com.moeking.data.utils

import com.moeking.data.models.Character
import com.moeking.data.models.ElementType
import com.moeking.data.models.ReactionType
import com.moeking.data.models.Team

/**
 * 元素反应计算器
 */
class ReactionCalculator {

    /**
     * 计算队伍的最佳元素反应
     */
    fun calculateBestReaction(team: Team): ReactionType {
        val elements = team.getUsedElements()

        return when {
            // 融化（火+冰）- 双倍伤害
            elements.contains(ElementType.FIRE) && elements.contains(ElementType.ICE) -> ReactionType.MELT

            // 蒸发（水+火）- 双倍伤害
            elements.contains(ElementType.WATER) && elements.contains(ElementType.FIRE) -> ReactionType.VAPORIZE

            // 超载（雷+火）- 爆炸伤害
            elements.contains(ElementType.ELECTRIC) && elements.contains(ElementType.FIRE) -> ReactionType.OVERLOAD

            // 感电（雷+水）- 持续伤害
            elements.contains(ElementType.ELECTRIC) && elements.contains(ElementType.WATER) -> ReactionType.ELECTRO_CHARGE

            // 超导（雷+冰）- 减抗
            elements.contains(ElementType.ELECTRIC) && elements.contains(ElementType.ICE) -> ReactionType.SUPERCONDUCTOR

            // 扩散（风+其他元素）- 扩散伤害
            elements.contains(ElementType.WIND) -> ReactionType.SWIRL

            // 冻结（水+冰）- 控制
            elements.contains(ElementType.WATER) && elements.contains(ElementType.ICE) -> ReactionType.FROZEN

            // 破碎（冰+物理）- 额外伤害
            elements.contains(ElementType.ICE) -> ReactionType.SHATTER

            // 激化（草+雷）- 增伤
            elements.contains(ElementType.GRASS) && elements.contains(ElementType.ELECTRIC) -> ReactionType.QUICKEN

            // 绽放（草+水）- 生成种子
            elements.contains(ElementType.GRASS) && elements.contains(ElementType.WATER) -> ReactionType.BLOOM

            // 超绽放（草+水+雷）- 高伤害
            elements.contains(ElementType.GRASS) && elements.contains(ElementType.WATER) && elements.contains(ElementType.ELECTRIC) -> ReactionType.HYPERBLOOM

            // 蔓激化（草+雷）- 增伤
            elements.contains(ElementType.GRASS) && elements.contains(ElementType.ELECTRIC) -> ReactionType.AGGRAVATE

            else -> ReactionType.NONE
        }
    }

    /**
     * 计算队伍伤害倍率
     */
    fun calculateDamageMultiplier(team: Team): Double {
        val reaction = calculateBestReaction(team)

        return when (reaction) {
            ReactionType.MELT -> 2.0 // 融化：双倍伤害
            ReactionType.VAPORIZE -> 2.0 // 蒸发：双倍伤害
            ReactionType.OVERLOAD -> 1.5 // 超载：1.5倍伤害
            ReactionType.ELECTRO_CHARGE -> 1.3 // 感电：1.3倍伤害
            ReactionType.SUPERCONDUCTOR -> 1.2 // 超导：1.2倍伤害
            ReactionType.SWIRL -> 1.4 // 扩散：1.4倍伤害
            ReactionType.FROZEN -> 1.1 // 冻结：1.1倍伤害
            ReactionType.SHATTER -> 1.5 // 破碎：1.5倍伤害
            ReactionType.QUICKEN -> 1.2 // 激化：1.2倍伤害
            ReactionType.BLOOM -> 1.3 // 绽放：1.3倍伤害
            ReactionType.HYPERBLOOM -> 1.5 // 超绽放：1.5倍伤害
            ReactionType.AGGRAVATE -> 1.2 // 蔓激化：1.2倍伤害
            else -> 1.0
        }
    }

    /**
     * 获取反应描述
     */
    fun getReactionDescription(reaction: ReactionType): String {
        return when (reaction) {
            ReactionType.MELT -> "融化：火元素 + 冰元素，造成双倍伤害！🔥❄️"
            ReactionType.VAPORIZE -> "蒸发：水元素 + 火元素，造成双倍伤害！💧🔥"
            ReactionType.OVERLOAD -> "超载：雷元素 + 火元素，造成爆炸伤害！⚡🔥"
            ReactionType.ELECTRO_CHARGE -> "感电：雷元素 + 水元素，造成持续伤害！⚡💧"
            ReactionType.SUPERCONDUCTOR -> "超导：雷元素 + 冰元素，降低敌人抗性！⚡❄️"
            ReactionType.SWIRL -> "扩散：风元素 + 其他元素，扩散伤害！🌪️"
            ReactionType.FROZEN -> "冻结：水元素 + 冰元素，控制敌人！💧❄️"
            ReactionType.SHATTER -> "破碎：冰元素 + 物理，额外伤害！❄️💥"
            ReactionType.QUICKEN -> "激化：草元素 + 雷元素，增伤！🌿⚡"
            ReactionType.BLOOM -> "绽放：草元素 + 水元素，生成种子！🌿💧"
            ReactionType.HYPERBLOOM -> "超绽放：草元素 + 水元素 + 雷元素，高伤害！🌿⚡💧"
            ReactionType.AGGRAVATE -> "蔓激化：草元素 + 雷元素，增伤！🌿⚡"
            ReactionType.NONE -> "无元素反应，使用普通伤害！"
        }
    }

    /**
     * 智能推荐最佳队伍
     */
    fun recommendBestTeam(characters: List<Character>): TeamRecommendation? {
        if (characters.size < 4) return null

        val team = Team(
            id = java.util.UUID.randomUUID().toString(),
            name = "最佳队伍",
            characters = characters.take(4),
            recommended = true,
            reactionType = calculateBestReaction(characters.take(4))
        )

        return TeamRecommendation(
            team = team,
            reactionType = team.reactionType,
            reasoning = "根据元素反应分析，推荐使用此队伍以达到最佳伤害输出。",
            reactionDamage = calculateDamageMultiplier(characters.take(4))
        )
    }
}
