package org.jetbrains.intellij.platform.example

import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ExampleTest : BasePlatformTestCase() {

    @Test
    fun `simple test`() {
        val text = FileTemplateManager.getInstance(project).findInternalTemplate("template.txt").text
        assertEquals("template", text)
    }
}