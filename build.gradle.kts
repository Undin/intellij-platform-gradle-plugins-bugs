import groovy.xml.XmlParser
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformExtension
import org.jetbrains.intellij.platform.gradle.tasks.PrepareSandboxTask
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

plugins {
    kotlin("jvm") version "1.9.23"
    id("org.jetbrains.intellij.platform") version "2.0.0-beta5"
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
    buildSearchableOptions = false
}


dependencies {
    intellijPlatform {
        intellijIdeaUltimate("2024.1")
        jetbrainsRuntime()

        testFramework(TestFrameworkType.Platform.Bundled)

        bundledPlugins(
            "com.intellij.java",
            "JUnit",
            "org.jetbrains.plugins.gradle",
            "com.intellij.gradle"
        )

        pluginModule(implementation(project(":module")))
        pluginModule(implementation(project(":module2")))
    }
    testImplementation(kotlin("test"))
}

tasks {
    withType<PrepareSandboxTask> {
        val projectName = project.the<IntelliJPlatformExtension>().projectName
        doLast {
            val libraryDir = destinationDir.resolve("${projectName.get()}/lib")
//            mergePluginJars(libraryDir, projectName.get())
        }
    }
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
            intellijIdeaUltimate("2024.1")

            bundledPlugins(
                "com.intellij.java",
                "JUnit",
                "org.jetbrains.plugins.gradle",
                "com.intellij.gradle"
            )

            testFramework(TestFrameworkType.Platform.Bundled)
        }
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.3")
    }
}

project(":module2") {
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
            intellijIdeaUltimate("2024.1")

            bundledPlugins(
                "com.intellij.java",
                "JUnit",
                "org.jetbrains.plugins.gradle",
                "com.intellij.gradle"
            )

            testFramework(TestFrameworkType.Platform.Bundled)
        }
        implementation(project(":module"))
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.3")
    }
}

fun mergePluginJars(pluginLibDir: File, baseArchiveName: String) {
    val pluginJars = pluginLibDir.listFiles().orEmpty().filter { it.isPluginJar() }
    val finalJarName = "$baseArchiveName-${version}.jar"
    // You can already have `finalJarName` file in destination directory among plugin jar files.
    // So let's create temp file first, process and drop all initial plugin jars and rename temp file to the desired name
    val mergedJarFile = File.createTempFile(baseArchiveName, ".jar", pluginLibDir)

    ZipOutputStream(mergedJarFile.outputStream()).use { mergedStream ->
        val addedEntries = mutableSetOf<String>()
        for (jarFile in pluginJars) {
            ZipFile(jarFile).use { zip ->
                for (entry in zip.entries()) {
                    if (entry.name == "META-INF/MANIFEST.MF" || entry.name == "classpath.index") {
                        continue
                    }
                    if (!addedEntries.add(entry.name)) {
                        if (entry.isDirectory) continue
                        error("Duplicated `${entry.name}` file")
                    }
                    mergedStream.putNextEntry(ZipEntry(entry.name))
                    zip.getInputStream(entry).use { input ->
                        input.copyTo(mergedStream)
                    }
                }
            }
        }
    }

    pluginJars.forEach(File::delete)
    mergedJarFile.renameTo(File(pluginLibDir, finalJarName))
}

fun File.isPluginJar(): Boolean {
    if (!isFile) return false
    if (extension != "jar") return false
    return zipTree(this).files.any {
        if (it.extension != "xml") return@any false
        val node = XmlParser().parse(it)
        return node.name() == "idea-plugin"
    }
}
