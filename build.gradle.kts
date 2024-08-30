import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.intellij.platform") version "2.0.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

val ideaVersion: String by project

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
    buildSearchableOptions = false
}


dependencies {
    intellijPlatform {
        intellijIdeaUltimate(ideaVersion, useInstaller = false)
        jetbrainsRuntime()

        testFramework(TestFrameworkType.Platform)

        bundledPlugins(
            "com.intellij.java",
            "JUnit",
            "org.jetbrains.plugins.gradle",
            "com.intellij.gradle"
        )

        pluginModule(implementation(project(":module")))
    }
    testImplementation(kotlin("test"))
    testImplementation("org.opentest4j:opentest4j:1.3.0")
}

project(":module") {
    apply {
        plugin("kotlin")
        plugin("org.jetbrains.intellij.platform.module")
    }

    repositories {
        mavenCentral()

        intellijPlatform {
            defaultRepositories()
        }
    }

    intellijPlatform {
        instrumentCode = false
    }

    dependencies {
        intellijPlatform {
            intellijIdeaUltimate(ideaVersion, useInstaller = false)

            bundledPlugins(
                "com.intellij.java",
                "JUnit",
                "org.jetbrains.plugins.gradle",
                "com.intellij.gradle"
            )

            testFramework(TestFrameworkType.Platform)
        }
        testImplementation("org.opentest4j:opentest4j:1.3.0")
    }
}
