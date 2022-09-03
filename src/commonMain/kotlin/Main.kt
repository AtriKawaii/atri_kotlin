import atri_plugin.PluginInstance
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValue
import kotlinx.cinterop.staticCFunction

object MyPlugin : Plugin("Name") {
    override fun enable() {
        info("From Kotlin/Native!")
    }

    override fun unload() {

    }
}

@Suppress("unused")
@CName("kotlin_init")
fun onInit(): CPointer<CFunction<() -> CValue<PluginInstance>>> {
    return staticCFunction { ->
        MyPlugin.toNativeInstance()
    }
}