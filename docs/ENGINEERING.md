# 萌王数据 - 工程文档

## 1. 项目初始化

### 1.1 创建项目
```bash
cd /home/zerlen/.openclaw/workspace/projects/moeking-data
```

### 1.2 创建基础目录
```
app/src/main/
├── java/com/moeking/data/
│   ├── ui/           # UI界面
│   ├── utils/        # 工具类
│   └── models/       # 数据模型
└── assets/           # 资源文件
```

## 2. Gradle配置

### 2.1 项目级build.gradle
```gradle
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:8.1.0'
        classpath 'org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0'
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

### 2.2 应用级build.gradle
```gradle
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    namespace 'com.moeking.data'
    compileSdk 34

    defaultConfig {
        applicationId "com.moeking.data"
        minSdk 24
        targetSdk 34
        versionCode 1
        versionName "1.0.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = '1.8'
    }

    buildFeatures {
        compose true
    }

    composeOptions {
        kotlinCompilerExtensionVersion '1.5.3'
    }
}

dependencies {
    // Jetpack Compose
    implementation 'androidx.compose.ui:ui:1.5.0'
    implementation 'androidx.compose.material3:material3:1.1.0'
    implementation 'androidx.compose.ui:ui-tooling-preview:1.5.0'
    debugImplementation 'androidx.compose.ui:ui-tooling:1.5.0'

    // ViewModel
    implementation 'androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1'
    implementation 'androidx.lifecycle:lifecycle-runtime-compose:2.6.1'

    // Navigation
    implementation 'androidx.navigation:navigation-compose:2.7.4'

    // Room Database
    implementation 'androidx.room:room-runtime:2.6.0'
    implementation 'androidx.room:room-ktx:2.6.0'
    kapt 'androidx.room:room-compiler:2.6.0'

    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

    // OkHttp
    implementation 'com.squareup.okhttp3:okhttp:4.11.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.11.0'

    // Glide
    implementation 'com.github.bumptech.glide:glide:4.16.0'
    kapt 'com.github.bumptech.glide:compiler:4.16.0'

    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3'

    // Coil (Image loading)
    implementation 'io.coil-kt:coil-compose:2.4.0'

    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    androidTestImplementation 'androidx.compose.ui:ui-test-junit4:1.5.0'
    debugImplementation 'androidx.compose.ui:ui-test-manifest:1.5.0'
}
```

### 2.3 settings.gradle
```gradle
rootProject.name = "MoeKing Data"
include ':app'
```

## 3. AndroidManifest.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.MoeKingData"
        tools:targetApi="31">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:theme="@style/Theme.MoeKingData">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

## 4. 资源文件

### 4.1 strings.xml
```xml
<resources>
    <string name="app_name">萌王数据</string>
    <string name="characters">角色列表</string>
    <string name="character_detail">角色详情</string>
    <string name="team_builder">队伍搭配</string>
    <string name="community">社区</string>
    <string name="search">搜索</string>
    <string name="element_fire">火</string>
    <string name="element_water">水</string>
    <string name="element_electric">雷</string>
    <string name="element_wind">风</string>
    <string name="element_ice">冰</string>
    <string name="element_geo">岩</string>
    <string name="element_grass">草</string>
</resources>
```

### 4.2 colors.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="purple_200">#FFBB86FC</color>
    <color name="purple_500">#FF6200EE</color>
    <color name="purple_700">#FF3700B3</color>
    <color name="teal_200">#FF03DAC5</color>
    <color name="teal_700">#FF018786</color>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>
    <color name="moe_pink">#FF69B4</color>
    <color name="moe_blue">#00BFFF</color>
    <color name="moe_purple">#DDA0DD</color>
</resources>
```

## 5. 代码结构

### 5.1 数据模型 (models)

```kotlin
// ElementType.kt
enum class ElementType(val displayName: String, val color: Color) {
    FIRE("火", Color.Red),
    WATER("水", Color.Blue),
    ELECTRIC("雷", Color(0xFFFFD700)), // 金色
    WIND("风", Color(0xFF00FF00)),     // 绿色
    ICE("冰", Color.Cyan),
    GEO("岩", Color(0xFF8B4513)),      // 棕色
    GRASS("草", Color(0xFF32CD32));    // 青绿色
}

// Character.kt
data class Character(
    val id: String,
    val name: String,
    val element: ElementType,
    val rarity: Int,
    val weaponType: WeaponType,
    val stats: CharacterStats,
    val skills: List<Skill>,
    val ratings: List<Rating>
)

// Team.kt
data class Team(
    val id: String,
    val name: String,
    val characters: List<Character>,
    val recommended: Boolean
)
```

### 5.2 Repository层

```kotlin
// CharacterRepository.kt
class CharacterRepository(private val characterDao: CharacterDao) {
    suspend fun getCharacters(): List<Character> = characterDao.getAll()
    suspend fun getCharacterById(id: String): Character? = characterDao.getById(id)
    suspend fun searchCharacters(query: String): List<Character> = characterDao.search(query)
}

// CharacterDao.kt
@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAll(): Flow<List<Character>>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getById(id: String): Character?

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :query || '%'")
    suspend fun search(query: String): List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<Character>)

    @Delete
    suspend fun delete(character: Character)
}
```

### 5.3 ViewModel层

```kotlin
// CharacterViewModel.kt
class CharacterViewModel(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun loadCharacters() {
        viewModelScope.launch {
            _characters.value = repository.getCharacters()
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _characters.value = repository.searchCharacters(query)
        }
    }
}
```

### 5.4 UI层

```kotlin
// CharacterListScreen.kt
@Composable
fun CharacterListScreen(viewModel: CharacterViewModel) {
    val characters by viewModel.characters.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("萌王数据") },
                actions = {
                    IconButton(onClick = { viewModel.search(searchQuery) }) {
                        Icon(Icons.Default.Search, contentDescription = "搜索")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(characters) { character ->
                CharacterItem(character)
            }
        }
    }
}

// CharacterItem.kt
@Composable
fun CharacterItem(character: Character) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { /* 跳转详情 */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 角色图片
            Image(
                painter = painterResource(id = character.imageUrl),
                contentDescription = character.name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

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
                    text = "★ ${"★".repeat(character.rarity)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
```

## 6. MainActivity

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoeKingDataTheme {
                NavigationGraph()
            }
        }
    }
}

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "characters"
    ) {
        composable("characters") {
            CharacterListScreen()
        }
        composable("character_detail/{characterId}") {
            CharacterDetailScreen(
                characterId = it.arguments?.getString("characterId") ?: ""
            )
        }
        composable("team_builder") {
            TeamBuilderScreen()
        }
        composable("community") {
            CommunityScreen()
        }
    }
}
```

## 7. 单元测试

```kotlin
// CharacterRepositoryTest.kt
class CharacterRepositoryTest {
    private lateinit var repository: CharacterRepository
    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).build()

        val characterDao = database.characterDao()
        repository = CharacterRepository(characterDao)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `insert character and get it by id`() = runTest {
        val character = createTestCharacter()
        repository.insertAll(listOf(character))

        val retrieved = repository.getCharacterById(character.id)
        assertEquals(character, retrieved)
    }

    @Test
    fun `search characters by name`() = runTest {
        val characters = listOf(
            createTestCharacter("安柏"),
            createTestCharacter("丽莎"),
            createTestCharacter("凯亚")
        )
        repository.insertAll(characters)

        val results = repository.searchCharacters("安")
        assertEquals(1, results.size)
        assertEquals("安柏", results[0].name)
    }

    private fun createTestCharacter(name: String = "测试角色"): Character {
        return Character(
            id = UUID.randomUUID().toString(),
            name = name,
            element = ElementType.FIRE,
            rarity = 4,
            weaponType = WeaponType.SWORD,
            stats = CharacterStats(
                hp = 1000,
                atk = 100,
                def = 100,
                speed = 100
            ),
            skills = listOf(),
            ratings = listOf()
        )
    }
}
```

## 8. Git仓库初始化

```bash
# 初始化Git仓库
git init

# 添加所有文件
git add .

# 创建初始提交
git commit -m "feat: 初始化萌王数据项目

- 创建项目结构
- 配置Gradle
- 实现基础数据模型
- 实现Repository层
- 实现ViewModel层
- 实现基础UI界面
- 添加单元测试"
```

## 9. 开发工具

### 9.1 Android Studio版本
- Android Studio Hedgehog | 2023.1.1 Patch 1
- JDK 17

### 9.2 依赖版本
- Kotlin: 1.9.0
- Compose: 1.5.0
- Room: 2.6.0
- Retrofit: 2.9.0

## 10. 发布准备

### 10.1 APK打包
```bash
./gradlew assembleRelease
# 输出: app/build/outputs/apk/release/app-release.apk
```

### 10.2 签名配置
- 密钥库: release.keystore
- 密码: (配置在keystore.properties)
- 别名: moeking
- 密码: (配置在keystore.properties)

## 11. 文档维护

### 11.1 更新频率
- 每完成一个功能模块更新一次
- 每次提交前更新CHANGELOG
- 定期审查和优化文档

### 11.2 文档位置
- README.md - 项目总览
- DESIGN.md - 设计文档
- ENGINEERING.md - 工程文档
- API.md - API文档（如有）
- CHANGELOG.md - 更新日志
