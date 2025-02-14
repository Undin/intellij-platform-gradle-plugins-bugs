plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.intellij.platform") version "2.2.2-SNAPSHOT"
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
        clion("2024.3", useInstaller = false)
        jetbrainsRuntime()

        bundledPlugins(
            "org.jetbrains.plugins.clion.radler",
            "com.intellij.clion",
        )
    }
    testImplementation(kotlin("test"))
}
