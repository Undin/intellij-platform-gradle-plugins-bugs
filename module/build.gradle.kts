plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.intellij.platform") version "2.0.0-beta3"
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
        intellijIdeaUltimate("2024.1")
    }
}
