plugins {
    kotlin("jvm") version "2.1.20"
    id("org.jetbrains.intellij.platform") version "2.6.0"
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
        intellijIdeaUltimate("2025.1", useInstaller = false)
        jetbrainsRuntime()

        bundledPlugins(
            "com.intellij.java",
            "JUnit",
            "org.jetbrains.plugins.gradle",
            "com.intellij.gradle"
        )
    }
    testImplementation(kotlin("test"))
}
