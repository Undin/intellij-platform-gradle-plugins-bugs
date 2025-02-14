package org.jetbrains.intellij.platform.example

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.ui.Messages
import com.jetbrains.cidr.radler.symbols.RadMainPsiElement

class ExampleAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        Messages.showInfoMessage("Message", "Title")
        val entryPoint = e.getData(PlatformDataKeys.PSI_ELEMENT)!!
        RadMainPsiElement(entryPoint.containingFile, entryPoint.textRange)
    }
}