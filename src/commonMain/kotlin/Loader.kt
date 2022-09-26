import kotlinx.cinterop.*
import kotlinx.cinterop.internal.CStruct
import platform.posix.size_t
import platform.posix.uint16_t
import platform.posix.uint8_t
import values.RustStr
import atri_manager.AtriManager as CAtriManager

@CStruct("AtriVTable")
data class AtriVTable(
    val log: CPointer<CFunction<(handle: size_t, manager: COpaquePointer, level: uint8_t, log: CValue<RustStr>) -> Unit>>
)

data class AtriManager(
    val handle: size_t,
    val managerPtr: COpaquePointer,
    val getFun: CPointer<CFunction<(uint16_t) -> COpaquePointer>>
)

lateinit var ATRI_MANAGER: AtriManager
    private set

lateinit var ATRI_VTABLE: AtriVTable
    private set

@CName("kotlin_atri_manager_init")
@Suppress("unused")
fun atriManagerInit(m: CAtriManager) {
    ATRI_MANAGER = AtriManager(
        m.handle,
        m.manager_ptr!!,
        m.get_fun!!.reinterpret()
    )

    val getFun = ATRI_MANAGER.getFun
    val vtable = AtriVTable(
        log = getFun(20000u).reinterpret()
    )

    ATRI_VTABLE = vtable
}