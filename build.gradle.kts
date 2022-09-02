plugins {
    kotlin("multiplatform") version "1.7.10"
}

group = "org.laolittle"
version = "0.1.0"

repositories {
    maven("https://maven.aliyun.com/repository/central")
    mavenCentral()
}

kotlin {
    arrayOf(
        //macosX64(),
        macosArm64(),/*
        mingwX64(),
        linuxX64(), linuxArm64(),
        androidNativeArm64(), androidNativeX64()*/
    ).forEach { target ->
        target.apply {
            compilations
                .getByName("main")
                .cinterops
                .apply {
                    create("atri_manager")
                    create("atri_plugin")
                    create("values")
                }

            binaries {
                staticLib()
            }
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}