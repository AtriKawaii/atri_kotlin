# AtriKotlin
使用Kotlin编写AtriQQ插件
(未完善)

## 示例
```kotlin
object MyPlugin: Plugin("Name") {
    override fun enable() {
        info("Plugin from Kotlin/Native")
    }

    override fun unload() {
        info("Unloading...")
    }
}

// 未来将会优化
@Suppress("unused")
@CName("kotlin_init")
fun onInit(): CPointer<CFunction<() -> CValue<PluginInstance>>> {
    return staticCFunction { ->
        MyPlugin.toNativeInstance()
    }
}
```

## Build
首先在[gradle](build.gradle.kts)添加自己的平台, 如macosArm64,
将其编译为静态库: `./gradlew linkDebugStaticXXX`

然后运行`cargo build`即可, 生成的动态库位于`target/debug(release)/`目录下,
将动态库放入`AtriQQ`的`plugins`目录下即可