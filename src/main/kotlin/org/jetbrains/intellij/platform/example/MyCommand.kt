package org.jetbrains.intellij.platform.example

import com.github.ajalt.clikt.command.CoreSuspendingCliktCommand
import com.github.ajalt.clikt.core.MessageEchoer
import com.github.ajalt.clikt.output.PlaintextHelpFormatter
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.required
import com.github.ajalt.clikt.parameters.groups.single
import com.github.ajalt.clikt.parameters.options.option
import com.intellij.openapi.application.ex.ApplicationManagerEx
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.diagnostic.logger

abstract class MyCommand(name: String) : CoreSuspendingCliktCommand(name) {

  private val option by mutuallyExclusiveOptions(
    option("foo"),
    option("bar"),
  ).single().required()

  override val printHelpOnEmptyArgs: Boolean get() = true

  init {
    configureContext {
      helpFormatter = { ctx -> PlaintextHelpFormatter(ctx, showDefaultValues = true) }
      echoMessage = LOG.toMessageEchoer()
    }
  }

  override suspend fun run() {
    echo("option: $option")
    ApplicationManagerEx.getApplicationEx().exit(true, true, 0)
  }

  companion object {
    @JvmStatic
    protected val LOG = logger<MyCommand>()
  }
}

internal fun Logger.toMessageEchoer(): MessageEchoer = { _, message, _, err ->
  val log: (String) -> Unit = if (err) ::error else ::warn
  log(message.toString())
}
