buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.22")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:1.9.22-1.0.17")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}