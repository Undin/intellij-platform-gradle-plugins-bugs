package org.jetbrains.intellij.platform.example.module

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class ModuleExampleAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        Messages.showInfoMessage("Message", "Title")
    }
}