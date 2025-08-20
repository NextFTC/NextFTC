package dev.nextftc.core.commands.utility

import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.CommandManager

class AdvancingCommand(vararg commands: Command) : Command() {

    init {
        require(commands.isNotEmpty()) { "commands must not be empty" }
    }

    private val commands = commands.toList()

    private var currentCommandIndex = 0
    private val currentCommand get() = commands[currentCommandIndex]

    override val isDone = false

    override fun start() {
        CommandManager.scheduleCommand(currentCommand)
    }

    fun advanceNow() {
        CommandManager.cancelCommand(currentCommand)
        currentCommandIndex = (currentCommandIndex + 1) % commands.size
        CommandManager.scheduleCommand(currentCommand)

    }

    fun advance() = InstantCommand { advanceNow() }

    override fun stop(interrupted: Boolean) {
        CommandManager.cancelCommand(currentCommand)
    }
}