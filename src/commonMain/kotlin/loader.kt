import atri_manager.AtriManager as CAtriManager
import kotlinx.cinterop.*
import kotlinx.cinterop.internal.CStruct
import platform.posix.*
import values.RustString

@CStruct("AtriVTable")
data class AtriVTable(
    val logInfo: CPointer<CFunction<(handle: size_t,manager: COpaquePointer, level: uint8_t, log: CValue<RustString>) -> Unit>>
)

data class AtriManager(
    val handle: size_t,
    val managerPtr: COpaquePointer,
    val getFun: CPointer<CFunction<(uint16_t) -> COpaquePointer>>
)

lateinit var ATRI_MANAGER: AtriManager
    private set

lateinit var ATRI_VTABLE: AtriVTable

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
        logInfo = getFun(20000u).reinterpret()
    )

    ATRI_VTABLE = vtable
}