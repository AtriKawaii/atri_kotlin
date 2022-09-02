fn main() {
    println!("cargo:rustc-link-search=build/bin/macosArm64/debugStatic");
    println!("cargo:rustc-link-lib=static=atri_kotlin");
    println!("cargo:rustc-link-lib=c++");
    println!("cargo:rustc-link-lib=objc");
    println!("cargo:rustc-link-arg=-framework");
    println!("cargo:rustc-link-arg=Foundation");
}