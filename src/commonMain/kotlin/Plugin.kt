import atri_plugin.PluginInstance
import kotlinx.cinterop.CValue
import kotlinx.cinterop.cValue
import kotlinx.cinterop.staticCFunction

abstract class Plugin(
    name: String,
) {
    abstract fun enable()

    open fun disable() {

    }

    open fun unload() {

    }
}

fun Plugin.toNativeInstance(): CValue<PluginInstance> {
    return cValue {
        instance.ptr = this@toNativeInstance.getPointer()
        instance.drop = staticCFunction { self ->
            kotlin.runCatching {
                (self?.getKotlinObject() as? Plugin)?.unload() ?: throw IllegalStateException()
            }.onFailure {
                it.printStackTrace()
            }
        }

        vtb.new = staticCFunction { -> null }
        vtb.enable = staticCFunction { self ->
            kotlin.runCatching {
                (self?.getKotlinObject() as? Plugin)?.enable() ?: throw IllegalStateException()
            }.onFailure {
                it.printStackTrace()
            }
        }
        vtb.disable = staticCFunction { self ->
            kotlin.runCatching {
                (self?.getKotlinObject() as? Plugin)?.disable() ?: throw IllegalStateException()
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}