import kotlinx.cinterop.CFunction
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.invoke
import kotlinx.cinterop.reinterpret

internal fun Any.getPointer() = (kotlin_object.k_obj_to_ptr!!.reinterpret<CFunction<(Any) -> COpaquePointer>>())(this)

internal fun COpaquePointer.getKotlinObject() =
    (kotlin_object.k_ptr_to_obj!!.reinterpret<CFunction<(COpaquePointer) -> Any>>())(this)