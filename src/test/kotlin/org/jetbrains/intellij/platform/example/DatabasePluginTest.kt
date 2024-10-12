package org.jetbrains.intellij.platform.example

import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

private const val DATABASE_PLUGIN_ID = "com.intellij.database"

@RunWith(JUnit4::class)
class DatabasePluginTest : BasePlatformTestCase() {

    @Test
    fun `sql file type`() {
        val databasePlugin = PluginManagerCore.loadedPlugins.find { it.pluginId.idString == DATABASE_PLUGIN_ID }

        assertNotNull("`$DATABASE_PLUGIN_ID` plugin is not loaded", databasePlugin)
    }
}
