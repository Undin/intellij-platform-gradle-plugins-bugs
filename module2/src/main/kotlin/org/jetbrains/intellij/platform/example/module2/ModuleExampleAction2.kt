package org.jetbrains.intellij.platform.example.module2

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import org.jetbrains.intellij.platform.example.module.ModuleExampleAction

class ModuleExampleAction2 : ModuleExampleAction() {
    override fun actionPerformed(e: AnActionEvent) {
        Messages.showInfoMessage("Message 2", "Title 2")
    }
}