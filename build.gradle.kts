// Top-level build file - configuración común para todos los módulos del proyecto
plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
    // Plugin de Firebase App Distribution para distribución automática de builds
    id("com.google.firebase.appdistribution") version "5.0.0" apply false
}
