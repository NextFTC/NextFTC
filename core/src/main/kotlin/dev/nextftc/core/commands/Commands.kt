@file:JvmName("Commands")
package dev.nextftc.core.commands

import dev.nextftc.core.commands.delays.Delay
import dev.nextftc.core.commands.utility.InstantCommand
import dev.nextftc.core.commands.utility.LambdaCommand
import kotlin.time.Duration

/**
 * Creates a command that runs [action] once.
 */
fun runOnce(name: String, action: Runnable) = InstantCommand(name, action)

/**
 * Creates a command that runs [action] once.
 */
fun runOnce(action: Runnable) = InstantCommand(action)

/**
 * Creates a command that runs [action] repeatedly.
 */
fun run(name: String, action: Runnable) =
    LambdaCommand(name).setUpdate(action).setIsDone { false }

/**
 * Creates a command that runs [action] repeatedly.
 */
fun run(action: Runnable) = run("PerpetualCommand", action)

/**
 * Creates a command that waits [time].
 */
fun wait(time: Duration) = Delay(time)

/**
 * Creates a command that waits [timeSeconds] seconds.
 */
fun wait(timeSeconds: Double) = Delay(timeSeconds)

