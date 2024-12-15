plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.intellij.platform") version "2.2.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
        jetbrainsRuntime()
    }
}

intellijPlatform {
    projectName = "IntelliJPlatformPluginExample"
    pluginConfiguration {
        id = "org.jetbrains.intellij.platform.example"
        name = "IntelliJ Platform Plugin Example"
        version = project.version.toString()
    }

  instrumentCode = false
}


dependencies {
    intellijPlatform {
        androidStudio("2024.2.1.11")
        jetbrainsRuntime()

        bundledPlugins(
            "com.intellij.java",
            "JUnit",
            "org.jetbrains.plugins.gradle",
            "com.intellij.gradle",
            "org.jetbrains.android"
        )
    }
    testImplementation(kotlin("test"))
}
