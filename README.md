# 宝宝安睡 - Android APP

一个极轻量化的哄孩子睡觉安卓应用。

## 功能特性

- 轻柔的摇篮曲播放
- 梦幻星空动画背景
- 可调节音量控制
- 定时关闭功能（1-120分钟）
- 极简设计，轻量化

## 项目结构

```
哄孩子程序/
├── app/
│   ├── build.gradle
│   ├── proguard-rules.pro
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/babysleep/app/
│           │   └── MainActivity.java
│           └── res/
│               ├── layout/
│               │   └── activity_main.xml
│               └── values/
│                   ├── colors.xml
│                   ├── strings.xml
│                   └── styles.xml
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## 使用说明

### 1. 添加摇篮曲音频文件

将摇篮曲音频文件命名为 `lullaby.mp3` 或 `lullaby.wav`，并放置到以下目录：

```
app/src/main/res/raw/lullaby.mp3
```

如果没有 `raw` 目录，请创建它。

**推荐的摇篮曲：**
- 勃拉姆斯摇篮曲
- 舒伯特摇篮曲
- 莫扎特摇篮曲
- 任何轻柔的纯音乐

### 2. 添加应用图标

将应用图标文件放置到以下目录：

```
app/src/main/res/mipmap-xxxhdpi/ic_launcher.png (192x192)
app/src/main/res/mipmap-xxhdpi/ic_launcher.png (144x144)
app/src/main/res/mipmap-xhdpi/ic_launcher.png (96x96)
app/src/main/res/mipmap-hdpi/ic_launcher.png (72x72)
app/src/main/res/mipmap-mdpi/ic_launcher.png (48x48)

app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png (192x192)
app/src/main/res/mipmap-xxhdpi/ic_launcher_round.png (144x144)
app/src/main/res/mipmap-xhdpi/ic_launcher_round.png (96x96)
app/src/main/res/mipmap-hdpi/ic_launcher_round.png (72x72)
app/src/main/res/mipmap-mdpi/ic_launcher_round.png (48x48)
```

### 3. 编译和安装

#### 使用 Android Studio：
1. 打开 Android Studio
2. 选择 "Open an Existing Project"
3. 选择此项目目录
4. 等待 Gradle 同步完成
5. 点击 "Run" 按钮或按 Shift+F10

#### 使用命令行：
```bash
# 编译 Debug 版本
./gradlew assembleDebug

# 安装到连接的设备
./gradlew installDebug

# 编译 Release 版本
./gradlew assembleRelease
```

## 应用功能说明

1. **播放/停止按钮**：控制摇篮曲的播放和停止
2. **定时器滑块**：设置自动停止时间（1-120分钟）
3. **音量滑块**：调节播放音量（0-100%）
4. **星空动画**：背景显示梦幻星空动画，帮助宝宝入睡

## 系统要求

- Android 5.0 (API 21) 或更高版本
- 最小 10MB 可用空间

## 注意事项

- 首次使用前请确保已添加摇篮曲音频文件
- 建议在安静的环境中使用
- 可根据宝宝的喜好调节音量和定时时间

## 许可证

本项目仅供个人使用和学习参考。
