# 萌王数据 - APK构建完整指南

## 快速构建（推荐）

### 方法1：使用在线构建服务 ⭐

**优点**：无需本地环境，一键构建

#### 使用 GitHub Actions（推荐）

1. 创建 `.github/workflows/build.yml` 文件：

```yaml
name: Build APK

on:
  push:
    branches: [ master ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Grant execute permission for gradlew
        run: chmod +x gradlew

      - name: Build APK
        run: ./gradlew assembleRelease

      - name: Upload APK
        uses: actions/upload-artifact@v3
        with:
          name: app-release
          path: app/build/outputs/apk/release/app-release.apk
```

2. 推送到GitHub后，Actions会自动构建
3. 在Actions页面下载APK

---

### 方法2：使用Android Studio（最稳定）

#### 步骤1：安装Android Studio

1. 下载：https://developer.android.com/studio
2. 安装（推荐Windows版本，在WSL中运行）

#### 步骤2：打开项目

1. 打开Android Studio
2. 选择 "Open an Existing Project"
3. 选择：`/home/zerlen/.openclaw/workspace/projects/moeking-data`

#### 步骤3：同步Gradle

- Android Studio会自动检测Gradle文件
- 点击 "Sync Now"

#### 步骤4：运行APP

**方式A：使用模拟器**
1. 点击工具栏的 "Device Manager"
2. 创建或选择模拟器
3. 点击 "Run" 按钮（绿色三角形）

**方式B：使用真机**
1. 连接Android手机
2. 开启USB调试
3. 在Device Manager中确认设备
4. 点击 "Run"

#### 步骤5：打包APK

1. 菜单栏选择：`Build` -> `Build Bundle(s) / APK(s)` -> `Build APK(s)`
2. 等待构建完成
3. 在通知中点击 "locate" 打开APK位置

**APK位置**：
```
app/build/outputs/apk/release/app-release.apk
```

---

### 方法3：命令行构建（需要完整环境）

#### 前提条件

- JDK 17
- Android SDK 34
- Gradle 8.0+
- Android SDK Platform 34
- Build Tools 34.0.0

#### 步骤

1. **安装JDK 17**
   ```bash
   sudo apt install openjdk-17-jdk
   export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
   export PATH=$JAVA_HOME/bin:$PATH
   ```

2. **安装Android SDK命令行工具**
   ```bash
   sudo apt install android-sdk
   ```

3. **安装必需的SDK组件**
   ```bash
   sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
   ```

4. **配置环境变量**
   ```bash
   echo 'export ANDROID_HOME=$HOME/Android/Sdk' >> ~/.bashrc
   echo 'export PATH=$ANDROID_HOME/platform-tools:$PATH' >> ~/.bashrc
   source ~/.bashrc
   ```

5. **构建APK**
   ```bash
   cd /home/zerlen/.openclaw/workspace/projects/moeking-data
   ./gradlew assembleRelease
   ```

6. **安装APK**
   ```bash
   adb install app/build/outputs/apk/release/app-release.apk
   ```

---

## 快速开始（5分钟）

### 如果你有Android Studio

1. 打开项目（1分钟）
2. 点击Run按钮（30秒）
3. 等待安装（1分钟）

**总计：约2-3分钟**

---

## 常见问题

### 1. Gradle同步失败

```bash
# 清理缓存
./gradlew clean

# 删除缓存
rm -rf .gradle app/build

# 重新同步
./gradlew build --refresh-dependencies
```

### 2. 构建失败

```bash
# 检查JDK版本
java -version  # 需要 JDK 17

# 检查Android SDK
sdkmanager --list_installed

# 重新安装缺失组件
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

### 3. APK安装失败

```bash
# 卸载旧版本
adb uninstall com.moeking.data

# 重新安装
adb install app/build/outputs/apk/release/app-release.apk
```

---

## 项目特点

✅ **已完成功能**
- 角色列表（带筛选和搜索）
- 角色详情展示
- 队伍搭配
- **元素反应计算器**（惊喜功能！）

✅ **技术栈**
- Kotlin
- Jetpack Compose
- MVVM架构
- Room数据库
- Koin依赖注入

✅ **数据**
- 6个角色示例数据
- 完整的元素反应计算

---

## 下一步

1. 构建APK
2. 安装到手机
3. 体验惊喜功能！🎉

---

## 需要帮助？

如果遇到问题，查看：
- BUILD_APK.md - 构建指南
- docs/DESIGN.md - 设计文档
- docs/ENGINEERING.md - 工程文档
