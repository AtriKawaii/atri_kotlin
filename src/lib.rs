use atri_ffi::plugin::PluginInstance;

#[repr(C)]
pub struct AtriManager {
    pub manager_ptr: *const (),
    pub handle: usize,
    pub get_fun: extern "C" fn(sig: u16) -> *const (),
}

#[no_mangle]
extern "C" fn on_init() -> PluginInstance {
    return unsafe {
        kotlin_init()()
    }
}

#[no_mangle]
extern "C" fn atri_manager_init(manager: AtriManager) {
    unsafe { kotlin_atri_manager_init(manager); }
}

extern "C" {
    fn kotlin_init() -> extern "C" fn() -> PluginInstance;

    fn kotlin_atri_manager_init(manager: AtriManager);
}