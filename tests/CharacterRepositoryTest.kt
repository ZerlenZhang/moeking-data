package com.moeking.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.moeking.data.models.*
import com.moeking.data.utils.AppDatabase
import com.moeking.data.utils.CharacterDao
import com.moeking.data.utils.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * 角色仓库单元测试
 */
class CharacterRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var characterDao: CharacterDao
    private lateinit var repository: CharacterRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()

        characterDao = database.characterDao()
        repository = CharacterRepository(characterDao)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `insert character and get it by id`() = runTest {
        val character = createTestCharacter("安柏")
        repository.insertCharacter(character)

        val retrieved = repository.getCharacterById("amber")
        assertNotNull(retrieved)
        assertEquals("安柏", retrieved?.name)
        assertEquals(4, retrieved?.rarity)
    }

    @Test
    fun `search characters by name`() = runTest {
        val characters = listOf(
            createTestCharacter("安柏"),
            createTestCharacter("丽莎"),
            createTestCharacter("凯亚")
        )
        repository.insertCharacters(characters)

        val results = repository.searchCharacters("安")
        assertEquals(1, results.size)
        assertEquals("安柏", results[0].name)
    }

    @Test
    fun `get characters by element`() = runTest {
        val fireCharacters = listOf(
            createTestCharacter("安柏"),
            createTestCharacter("迪卢克")
        )
        val waterCharacters = listOf(
            createTestCharacter("芭芭拉"),
            createTestCharacter("行秋")
        )

        repository.insertCharacters(fireCharacters)
        repository.insertCharacters(waterCharacters)

        val fireResults = repository.getCharactersByElement("FIRE")
        assertEquals(2, fireResults.size)
        assertEquals("FIRE", fireResults[0].element.name)

        val waterResults = repository.getCharactersByElement("WATER")
        assertEquals(2, waterResults.size)
        assertEquals("WATER", waterResults[0].element.name)
    }

    @Test
    fun `get characters by weapon type`() = runTest {
        val swordCharacters = listOf(
            createTestCharacter("安柏"),
            createTestCharacter("凯亚")
        )
        val polearmCharacters = listOf(
            createTestCharacter("魈"),
            createTestCharacter("雷电将军")
        )

        repository.insertCharacters(swordCharacters)
        repository.insertCharacters(polearmCharacters)

        val swordResults = repository.getCharactersByWeaponType("BOW")
        assertEquals(1, swordResults.size)
        assertEquals("BOW", swordResults[0].weaponType.name)

        val polearmResults = repository.getCharactersByWeaponType("POLEARM")
        assertEquals(2, polearmResults.size)
    }

    @Test
    fun `get characters by rarity`() = runTest {
        val fourStarCharacters = listOf(
            createTestCharacter("安柏"),
            createTestCharacter("凯亚")
        )
        val fiveStarCharacters = listOf(
            createTestCharacter("魈"),
            createTestCharacter("雷电将军")
        )

        repository.insertCharacters(fourStarCharacters)
        repository.insertCharacters(fiveStarCharacters)

        val fourStarResults = repository.getCharactersByRarity(4)
        assertEquals(2, fourStarResults.size)
        assertEquals(4, fourStarResults[0].rarity)

        val fiveStarResults = repository.getCharactersByRarity(5)
        assertEquals(2, fiveStarResults.size)
        assertEquals(5, fiveStarResults[0].rarity)
    }

    @Test
    fun `update character`() = runTest {
        val character = createTestCharacter("安柏")
        repository.insertCharacter(character)

        val updatedCharacter = character.copy(name = "安柏（测试）")
        repository.updateCharacter(updatedCharacter)

        val retrieved = repository.getCharacterById("amber")
        assertNotNull(retrieved)
        assertEquals("安柏（测试）", retrieved?.name)
    }

    @Test
    fun `delete character`() = runTest {
        val character = createTestCharacter("安柏")
        repository.insertCharacter(character)

        repository.deleteCharacter(character)

        val retrieved = repository.getCharacterById("amber")
        assertNull(retrieved)
    }

    @Test
    fun `delete all characters`() = runTest {
        val characters = listOf(
            createTestCharacter("安柏"),
            createTestCharacter("魈")
        )
        repository.insertCharacters(characters)

        repository.deleteAllCharacters()

        val allCharacters = repository.getAllOrderedByRarity()
        assertEquals(0, allCharacters.size)
    }

    /**
     * 创建测试角色
     */
    private fun createTestCharacter(name: String = "测试角色"): Character {
        return Character(
            id = name.lowercase(),
            name = name,
            element = ElementType.FIRE,
            rarity = 4,
            weaponType = WeaponType.BOW,
            stats = CharacterStats(
                hp = 1000,
                atk = 60,
                def = 50,
                speed = 100,
                subStats = emptyList()
            ),
            skills = listOf(
                Skill("1", "测试技能", SkillType.NORMAL_ATTACK, "这是一个测试技能", 1)
            ),
            ratings = listOf(
                Rating("原神", "T4", "测试评级")
            ),
            imageUrl = "",
            description = "这是一个测试角色"
        )
    }
}
