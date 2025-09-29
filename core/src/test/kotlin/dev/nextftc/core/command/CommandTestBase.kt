package dev.nextftc.core.command

import dev.nextftc.core.commands.CommandManager
import dev.nextftc.core.commands.utility.LambdaCommand
import dev.nextftc.core.commands.utility.NullCommand
import org.junit.jupiter.api.BeforeEach

abstract class CommandTestBase {
    @BeforeEach
    fun setup() {
        CommandManager.cancelAll()
    }

    fun CommandManager.run(times: Int) {
        repeat(times) { run() }
    }

    fun CommandManager.runUntilEmpty() {
        while (hasCommands()) {
            run()
        }
    }
}

fun labelCommand(label: String) = LambdaCommand(label).setIsDone { false }