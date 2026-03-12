# 萌王数据 - 设计文档

## 1. 产品定位

### 1.1 目标用户
- 热爱二次元游戏的玩家
- 原神、崩铁、绝区零、明日方舟玩家
- 希望优化角色养成的玩家
- 喜欢分享和交流的玩家

### 1.2 用户场景
- 查询角色属性和技能
- 搭配最强队伍
- 分享自己的攻略
- 获取游戏建议

## 2. 功能设计

### 2.1 核心功能

#### 2.1.1 角色查询
```
界面：
- 搜索框（支持角色名、元素）
- 角色列表（分页加载）
- 角色详情页
  - 基本信息（名称、星级、元素）
  - 属性面板（生命、攻击、防御、速度）
  - 技能列表（技能名称、描述、效果）
  - 评级（T0-T5）
  - 角色壁纸
```

#### 2.1.2 队伍搭配
```
界面：
- 队伍编辑器
- 元素反应提示
- 缺位角色建议
- 队伍预览

算法逻辑：
1. 根据游戏类型选择元素反应类型
2. 分析当前队伍元素
3. 推荐最佳配队方案
4. 显示反应伤害倍率
```

#### 2.1.3 社区功能
```
界面：
- 攻略列表
- 攻略详情
- 发布攻略表单
- 评论互动
```

### 2.2 数据结构

#### 2.2.1 角色数据模型
```kotlin
data class Character(
    val id: String,
    val name: String,
    val element: ElementType,  // 火、水、雷、风、冰、岩、草
    val rarity: Int,           // 1-6星
    val weaponType: WeaponType,// 单手剑、双手剑、弓、法器、长柄武器
    val stats: CharacterStats,
    val skills: List<Skill>,
    val ratings: List<Rating>,
    val imageUrl: String
)

data class CharacterStats(
    val hp: Int,
    val atk: Int,
    val def: Int,
    val speed: Int,
    val subStats: List<SubStat>
)
```

#### 2.2.2 队伍数据模型
```kotlin
data class Team(
    val id: String,
    val name: String,
    val characters: List<Character>,
    val recommended: Boolean,
    val reactionType: ReactionType
)

data class TeamRecommendation(
    val team: Team,
    val reasoning: String,
    val reactionDamage: Double
)
```

## 3. UI/UX设计

### 3.1 设计风格
- **主题色**: 活力粉 + 霓虹蓝
- **字体**: 思源黑体
- **圆角**: 16dp
- **阴影**: 柔和阴影

### 3.2 页面流转
```
启动页 -> 主页（角色列表）
         -> 角色详情
         -> 队伍搭配
         -> 社区
```

## 4. 技术架构

### 4.1 架构模式
采用MVVM + Clean Architecture

```
┌─────────────────────────────────────┐
│         View (UI Layer)             │
├─────────────────────────────────────┤
│  ViewModel + State Management       │
├─────────────────────────────────────┤
│  Repository (Data Layer)            │
├─────────────────────────────────────┤
│  DataSource (Local/Remote)          │
└─────────────────────────────────────┘
```

### 4.2 模块划分
- **app**: 主应用模块
- **core**: 核心模块（工具、常量）
- **data**: 数据模块（Repository、DataSource）

## 5. 数据流

### 5.1 角色查询流程
```
UI -> ViewModel -> Repository -> DataSource -> Database/Network
             ↓
         更新State
             ↓
         UI刷新
```

### 5.2 队伍推荐流程
```
UI输入队伍 -> ViewModel -> RecommendationEngine
                                  ↓
                            计算最佳方案
                                  ↓
                            更新State
                                  ↓
                            UI显示
```

## 6. 性能优化

### 6.1 数据加载
- 分页加载
- 缓存机制
- 预加载

### 6.2 UI优化
- Compose重组优化
- 图片懒加载
- 列表虚拟化

## 7. 安全性

### 7.1 数据安全
- 本地数据库加密
- 敏感信息脱敏

### 7.2 网络安全
- HTTPS
- 证书固定

## 8. 扩展性

### 8.1 游戏支持
- 插件化设计
- 数据结构统一
- 接口抽象

### 8.2 功能扩展
- 模块化开发
- 依赖注入
- 插件系统

## 9. 开发规范

### 9.1 命名规范
- 类名：PascalCase
- 函数名：camelCase
- 常量：UPPER_SNAKE_CASE
- 布尔值：is/isHas

### 9.2 代码注释
- 类注释：KDoc格式
- 函数注释：说明参数和返回值
- 关键逻辑：行内注释

## 10. 测试策略

### 10.1 单元测试
- Repository层测试
- 业务逻辑测试
- 工具类测试

### 10.2 UI测试
- Compose UI测试
- 交互流程测试

## 11. 部署方案

### 11.1 开发环境
- Android Studio Hedgehog
- Gradle 8.0+

### 11.2 发布流程
- 内测版 -> 公测版 -> 正式版
- 自动化CI/CD
- 版本管理
