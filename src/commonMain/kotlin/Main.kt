import atri_plugin.PluginInstance
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValue
import kotlinx.cinterop.staticCFunction

object MyPlugin: Plugin("") {
    override fun enable() {
        info("Plugin from Kotlin/Native")
        error("This is an error")
        warn("This is warn")
        debug("Debug info")
        trace("Tracing!")
    }

    override fun unload() {
        info("Unloading...")
    }
}

@Suppress("unused")
@CName("kotlin_init")
fun onInit(): CPointer<CFunction<() -> CValue<PluginInstance>>> {
    return staticCFunction { ->
        MyPlugin.toNativeInstance()
    }
}