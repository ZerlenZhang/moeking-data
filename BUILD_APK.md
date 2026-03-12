# 构建APK指南

## 环境要求

- Android Studio Hedgehog (2023.1.1 Patch 1) 或更高版本
- JDK 17
- Android SDK 34
- Gradle 8.0+

## 构建步骤

### 1. 打开项目

```bash
cd /home/zerlen/.openclaw/workspace/projects/moeking-data
```

### 2. 在Android Studio中打开

- 打开 Android Studio
- 选择 "Open an Existing Project"
- 选择项目目录：`/home/zerlen/.openclaw/workspace/projects/moeking-data`

### 3. 同步Gradle

- Android Studio会自动检测Gradle文件
- 点击 "Sync Now" 同步依赖

### 4. 运行应用

#### 方式1：使用Android Studio

1. 点击工具栏的 "Run" 按钮（绿色三角形）
2. 选择模拟器或连接的真机设备
3. 等待应用安装并启动

#### 方式2：使用命令行

```bash
# 构建Debug APK
./gradlew assembleDebug

# 构建Release APK
./gradlew assembleRelease

# 输出位置：
# Debug: app/build/outputs/apk/debug/app-debug.apk
# Release: app/build/outputs/apk/release/app-release.apk
```

### 5. 安装APK

#### 方式1：通过Android Studio

- 应用安装完成后会自动启动

#### 方式2：手动安装

```bash
# 安装Debug APK到设备
adb install app/build/outputs/apk/debug/app-debug.apk

# 卸载
adb uninstall com.moeking.data
```

## 功能说明

### 已实现功能

- ✅ 角色列表展示
- ✅ 角色筛选（按元素、武器类型、稀有度）
- ✅ 角色搜索
- ✅ 角色详情展示
- ✅ 队伍搭配
- ✅ 本地数据库存储

### 待实现功能

- ⏳ 网络API集成
- ⏳ 社区功能
- ⏳ 更多角色数据
- ⏳ 角色收藏功能
- ⏳ 队伍分享功能

## 测试

### 运行单元测试

```bash
# 运行所有测试
./gradlew test

# 运行特定测试类
./gradlew test --tests CharacterRepositoryTest
```

### 运行UI测试

```bash
# 运行UI测试
./gradlew connectedAndroidTest
```

## 常见问题

### 1. Gradle同步失败

```bash
# 清理Gradle缓存
./gradlew clean

# 删除 .gradle 文件夹
rm -rf .gradle app/build
```

### 2. 构建失败

```bash
# 检查JDK版本
java -version  # 需要 JDK 17

# 检查Android SDK
sdkmanager --list_installed

# 安装缺失的SDK组件
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

### 3. 依赖下载失败

```bash
# 清理Gradle缓存
./gradlew clean

# 重新同步Gradle
./gradlew build --refresh-dependencies
```

## 发布准备

### 1. 签名APK

在 `app/build.gradle` 中配置签名：

```gradle
android {
    signingConfigs {
        release {
            storeFile file("keystore.jks")
            storePassword "your_store_password"
            keyAlias "moeking"
            keyPassword "your_key_password"
        }
    }

    buildTypes {
        release {
            signingConfig signingConfigs.release
            // ... 其他配置
        }
    }
}
```

### 2. 生成APK

```bash
# 构建Release APK
./gradlew assembleRelease

# APK位置：app/build/outputs/apk/release/app-release.apk
```

### 3. 上传到应用商店

- Google Play Store
- 第三方应用市场

## 性能优化

### 已实现的优化

- ✅ Room数据库查询优化
- ✅ Jetpack Compose重组优化
- ✅ 图片懒加载（Glide/Coil）
- ✅ 异步数据加载

### 可进一步优化的地方

- 数据库索引优化
- 网络请求缓存
- 图片压缩
- 启动速度优化

## 开发进度

- [x] 项目初始化
- [x] 架构设计
- [x] 数据模型
- [x] 数据库
- [x] Repository层
- [x] ViewModel层
- [x] UI层
- [x] 单元测试
- [x] 初始化数据
- [ ] 网络API
- [ ] 社区功能
- [ ] 更多角色数据
- [ ] APK打包

## 技术栈

- **语言**: Kotlin
- **UI框架**: Jetpack Compose
- **架构**: MVVM + Clean Architecture
- **数据库**: Room
- **网络**: Retrofit + OkHttp
- **依赖注入**: Koin
- **构建工具**: Gradle
