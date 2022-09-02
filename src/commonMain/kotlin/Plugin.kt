import atri_plugin.PluginInstance
import kotlinx.cinterop.*
import kotlin.random.Random

abstract class Plugin(
    name: String,
) {
    abstract fun enable()

    open fun disable() {

    }

    open fun unload() {

    }
}

// Thread Safety: safe because only one single thread manage plugin
private val plugins = mutableMapOf<Long, Plugin>()

fun Plugin.toNativeInstance(): CValue<PluginInstance> {
    val rand = Random.nextLong()

    plugins.put(rand, this)?.let {

    }
    return cValue {
        instance.ptr = rand.toCPointer()
        instance.drop = staticCFunction { self ->
            kotlin.runCatching {
                plugins.remove(self.toLong())?.unload() ?: throw IllegalStateException()
            }.onFailure {
                it.printStackTrace()
            }
        }

        vtb.new = staticCFunction { -> null }
        vtb.enable = staticCFunction { self ->
            kotlin.runCatching {
                plugins[self.toLong()]?.enable() ?: throw IllegalStateException()
            }.onFailure {
                it.printStackTrace()
            }
        }
        vtb.disable = staticCFunction { self ->
            kotlin.runCatching {
                plugins[self.toLong()]?.disable() ?: throw IllegalStateException()
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}