import org.jetbrains.intellij.platform.gradle.tasks.aware.SandboxAware

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
    }
    testImplementation(kotlin("test"))
}

abstract class MergePluginJarsTask : Jar(), SandboxAware

tasks {
    val mergePluginJarTask by registering(MergePluginJarsTask::class) {}
}
