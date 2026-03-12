package com.moeking.data.utils

import androidx.room.TypeConverter
import com.moeking.data.models.ElementType
import com.moeking.data.models.ReactionType
import com.moeking.data.models.Skill
import com.moeking.data.models.SkillType
import com.moeking.data.models.StatType
import com.moeking.data.models.Team
import com.moeking.data.models.Character
import com.moeking.data.models.SubStat
import com.moeking.data.models.WeaponType
import com.moeking.data.models.Rating

/**
 * Room类型转换器
 */
class Converters {

    /**
     * 将ElementType转换为String
     */
    @TypeConverter
    fun fromElementType(elementType: ElementType): String {
        return elementType.name
    }

    /**
     * 将String转换为ElementType
     */
    @TypeConverter
    fun toElementType(value: String): ElementType {
        return ElementType.valueOf(value)
    }

    /**
     * 将WeaponType转换为String
     */
    @TypeConverter
    fun fromWeaponType(weaponType: WeaponType): String {
        return weaponType.name
    }

    /**
     * 将String转换为WeaponType
     */
    @TypeConverter
    fun toWeaponType(value: String): WeaponType {
        return WeaponType.valueOf(value)
    }

    /**
     * 将SkillType转换为String
     */
    @TypeConverter
    fun fromSkillType(skillType: SkillType): String {
        return skillType.name
    }

    /**
     * 将String转换为SkillType
     */
    @TypeConverter
    fun toSkillType(value: String): SkillType {
        return SkillType.valueOf(value)
    }

    /**
     * 将StatType转换为String
     */
    @TypeConverter
    fun fromStatType(statType: StatType): String {
        return statType.name
    }

    /**
     * 将String转换为StatType
     */
    @TypeConverter
    fun toStatType(value: String): StatType {
        return StatType.valueOf(value)
    }

    /**
     * 将ReactionType转换为String
     */
    @TypeConverter
    fun fromReactionType(reactionType: ReactionType): String {
        return reactionType.name
    }

    /**
     * 将String转换为ReactionType
     */
    @TypeConverter
    fun toReactionType(value: String): ReactionType {
        return ReactionType.valueOf(value)
    }

    /**
     * 将Character列表转换为String
     */
    @TypeConverter
    fun fromCharacterList(characters: List<Character>): String {
        return characters.joinToString(",") { it.id }
    }

    /**
     * 将String转换为Character列表
     */
    @TypeConverter
    fun toCharacterList(value: String): List<Character> {
        return value.split(",").mapNotNull { id -> Character(id, "", null, 0, null, null, null, null, null, "") }
    }

    /**
     * 将Skill列表转换为String
     */
    @TypeConverter
    fun fromSkillList(skills: List<Skill>): String {
        return skills.joinToString(",") { "${it.id}:${it.name}" }
    }

    /**
     * 将String转换为Skill列表
     */
    @TypeConverter
    fun toSkillList(value: String): List<Skill> {
        return value.split(",").mapNotNull { it.split(":").takeIf { parts -> parts.size >= 2 }?.let { parts ->
            Skill(parts[0], parts[1], null, "", 1)
        } ?: null }
    }

    /**
     * 将SubStat列表转换为String
     */
    @TypeConverter
    fun fromSubStatList(subStats: List<SubStat>): String {
        return subStats.joinToString(",") { "${it.statType.name}:${it.value}" }
    }

    /**
     * 将String转换为SubStat列表
     */
    @TypeConverter
    fun toSubStatList(value: String): List<SubStat> {
        return value.split(",").mapNotNull { it.split(":").takeIf { parts -> parts.size >= 2 }?.let { parts ->
            SubStat(StatType.valueOf(parts[0]), parts[1].toFloat())
        } ?: null }
    }

    /**
     * 将Rating列表转换为String
     */
    @TypeConverter
    fun fromRatingList(ratings: List<Rating>): String {
        return ratings.joinToString(",") { "${it.game}:${it.tier}" }
    }

    /**
     * 将String转换为Rating列表
     */
    @TypeConverter
    fun toRatingList(value: String): List<Rating> {
        return value.split(",").mapNotNull { it.split(":").takeIf { parts -> parts.size >= 2 }?.let { parts ->
            Rating(parts[0], parts[1], "")
        } ?: null }
    }
}
