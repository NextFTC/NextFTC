package dev.nextftc.core.commands.utility

import dev.nextftc.core.commands.Command

/**
 * A command that runs a command repeatedly until it is interrupted.
 *
 * @param command the command to run repeatedly
 */
class RepeatCommand(val command: Command) : Command() {
    override val isDone: Boolean = false

    private var ended = false

    init {
        named("Repeat({$command.name})")
        setRequirements(command.requirements)
    }

    override fun start() {
        command.start()
    }

    override fun update() {
        if (ended) {
            command.start()
            ended = false
        }

        command.update()

        if (command.isDone) {
            ended = true
            command.stop(false)
        }
    }

    override fun stop(interrupted: Boolean) {
        if (!ended) {
            command.stop(interrupted)
            ended = true
        }
    }
}