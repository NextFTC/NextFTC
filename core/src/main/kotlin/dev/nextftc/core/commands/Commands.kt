@file:JvmName("Commands")
package dev.nextftc.core.commands

import dev.nextftc.core.commands.delays.Delay
import dev.nextftc.core.commands.utility.InstantCommand
import dev.nextftc.core.commands.utility.LambdaCommand
import kotlin.time.Duration

/**
 * Creates a command that runs [action] once.
 */
fun instant(name: String, action: Runnable) = InstantCommand(name, action)

/**
 * Creates a command that runs [action] once.
 */
fun instant(action: Runnable) = InstantCommand(action)

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

/**
 * Creates a command that schedules [command] to run.
 *
 * **Note:** if a proxy command is used in a group,
 * the original command's requirements will not be part of the group's requirements.
 */
fun proxy(command: Command): Command = instant("Proxy(${command.name})") { command.schedule() }

/**
 * Creates a command that schedules [command] to run,
 * and then waits for it to complete.
 *
 * @see proxy
 */
fun await(command: Command): Command = proxy(command).until { !command.isScheduled }
