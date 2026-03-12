# GitHub仓库配置

## 1. 创建GitHub仓库

访问 https://github.com/new 创建新仓库：

- **Repository name**: moeking-data
- **Description**: 萌王数据 - 二次元游戏数据分析与社交平台
- **Visibility**: Public（或Private，根据需要）
- **不要**勾选 "Add a README file"
- **不要**勾选 "Add .gitignore"
- **不要**勾选 "Choose a license"

## 2. 推送代码到GitHub

创建仓库后，在终端执行：

```bash
# 添加远程仓库
git remote add origin https://github.com/你的用户名/moeking-data.git

# 推送代码到main分支
git branch -M main
git push -u origin main
```

## 3. 后续提交

每次有重要进展后，执行：

```bash
git add .
git commit -m "feat: 你的提交信息"
git push
```

## 4. 提交信息规范

使用语义化提交信息：

- `feat:` 新功能
- `fix:` 修复bug
- `docs:` 文档更新
- `style:` 代码格式调整
- `refactor:` 代码重构
- `test:` 测试相关
- `chore:` 构建/工具链相关

## 5. 项目结构

```
moeking-data/
├── app/
│   ├── src/main/
│   │   ├── java/com/moeking/data/
│   │   │   ├── ui/           # UI界面
│   │   │   ├── utils/        # 工具类
│   │   │   └── models/       # 数据模型
│   │   └── res/              # 资源文件
│   └── build.gradle
├── docs/                     # 文档
├── tests/                    # 单元测试
├── build.gradle
├── settings.gradle
└── README.md
```
