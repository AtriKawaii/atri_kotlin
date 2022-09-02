import kotlinx.cinterop.*
import platform.posix.memcpy
import platform.posix.size_t
import platform.posix.uint8_t
import values.RustString

fun trace(s: String) = log(s, 0u)

fun debug(s: String) = log(s, 1u)

fun info(s: String) = log(s, 2u)

fun warn(s: String) = log(s, 3u)

fun error(s: String) = log(s, 4u)

fun log(s: String, level: uint8_t) {
    val bytes = s.encodeToByteArray()
    val pointer = platform.posix.malloc(bytes.size.convert())!!.reinterpret<ByteVar>()

    bytes.usePinned { pin ->
        memcpy(pointer, pin.addressOf(0), bytes.size.convert())
    }

    val rs = cValue<RustString> {
        val size = bytes.size.convert<size_t>()
        ptr = pointer.reinterpret()
        len = size
        capacity = size
    }

    ATRI_MANAGER.apply {
        (ATRI_VTABLE.logInfo)(handle.convert(), managerPtr, level, rs)
    }
}