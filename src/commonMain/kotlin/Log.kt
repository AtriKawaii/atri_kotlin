import kotlinx.cinterop.*
import platform.posix.memcpy
import platform.posix.uint8_t
import values.RustStr

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

        val rs = cValue<RustStr>() {
            ptr = pin.addressOf(0)
            len = bytes.size.convert()
        }

        ATRI_MANAGER.apply {
            (ATRI_VTABLE.log)(handle.convert(), managerPtr, level, rs)
        }
    }
}