plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.intellij.platform") version "2.0.0-SNAPSHOT"
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
        intellijIdeaUltimate("2024.1")
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
