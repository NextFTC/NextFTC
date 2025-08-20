/*
 * NextFTC: a user-friendly control library for FIRST Tech Challenge
 *     Copyright (C) 2025 Rowan McAlpin
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.nextftc.core.command

import dev.nextftc.core.command.groups.ParallelDeadlineGroup
import dev.nextftc.core.command.groups.ParallelGroup
import dev.nextftc.core.command.groups.ParallelRaceGroup
import dev.nextftc.core.command.groups.SequentialGroup
import dev.nextftc.core.command.utility.ForcedParallelCommand
import dev.nextftc.core.command.utility.PerpetualCommand
import dev.nextftc.core.command.utility.delays.Delay
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.core.units.TimeSpan
import dev.nextftc.core.units.sec

/**
 * A discrete unit of functionality that runs simultaneous to all other commands.
 */
abstract class Command : Runnable {

    /**
     * Whether this command has completed running. Often implemented using a getter function,
     *      although it can be set directly for commands that stop instantly or never stop. Please
     *      note that in certain circumstances the command will be stopped *before* this evaluates
     *      to true, most notably during subsystem conflicts or when the OpMode has been stopped.
     */
    abstract val isDone: Boolean

    /**
     * Whether this command can be stopped due to a conflict of [Subsystem]s.
     */
    var interruptible = true
        private set

    /**
     * A set of all Subsystems this command implements.
     */
    val subsystems: MutableSet<Subsystem> = mutableSetOf()

    /**
     * The name of this command, used for debugging and telemetry.
     */
    @get:JvmName("name")
    var name: String = this::class.simpleName ?: "Command"
        private set

    override fun toString() = name

    /**
     * Called once when the command is first started
     */
    open fun start() {}

    /**
     * Called repeatedly while the command is running.
     *
     *  IMPORTANT: the time this function takes to
     *      run should be as close to 0 as possible, to maximize loop speed and increase
     *      responsiveness.
     */
    open fun update() {}

    /**
     * Called once when the command is stopped.
     * @param interrupted whether this command was interrupted
     */
    open fun stop(interrupted: Boolean) {}

    /**
     * Schedules the command
     */
    fun schedule() {
        CommandManager.scheduleCommand(this)
    }

    /**
     * Allows you to directly "call" a command
     * For example,
     *
     * ```kotlin
     * val command = MyCommand()
     * command()
     * ```
     *
     * Note: only works in Kotlin.
     * In Java, use `schedule()`
     * @see schedule
     */
    operator fun invoke() = schedule()

    override fun run() = schedule()

    fun cancel() = CommandManager.cancelCommand(this)

    // region Property Setters

    open fun setSubsystems(vararg subsystems: Subsystem) = apply {
        this.subsystems.clear()
        this.subsystems.addAll(subsystems)
    }

    open fun setSubsystems(subsystems: Set<Subsystem>) = apply {
        this.subsystems.clear()
        this.subsystems.addAll(subsystems)
    }

    fun addSubsystems(vararg subsystems: Subsystem) = apply {
        this.subsystems.addAll(subsystems)
    }

    fun addSubsystems(subsystems: Set<Subsystem>) = apply {
        this.subsystems.addAll(subsystems)
    }

    open fun setInterruptible(interruptible: Boolean) = apply {
        this.interruptible = interruptible
    }

    /**
     * Sets the name of this command for use in debugging and telemetry.
     */
    open fun named(name: String) = apply {
        this.name = name
    }

    /**
     * Sets the name of this command for use in debugging and telemetry.
     */
    open fun setName(name: String) = named(name)

    // endregion

    // region Command Utilities

    /**
     * Returns a [ParallelRaceGroup] with this command and a [Delay] of [time]
     * @param time the time-span for the [Delay]
     */
    fun endAfter(time: TimeSpan) = ParallelRaceGroup(
        this,
        Delay(time)
    )

    /**
     * Returns a [ParallelRaceGroup] with this command and a [Delay] of [time]
     * @param time the time-span for the [Delay], in seconds
     */
    fun endAfter(time: Double) = endAfter(time.sec)

    /**
     * Returns a [ParallelRaceGroup] with this command and a [Delay] of [time]
     * @param time the time-span for the [Delay], in seconds
     */
    fun endAfter(time: Int) = endAfter(time.sec)

    /**
     * Returns a [SequentialGroup] with this command and an arbitrary number of other commands
     * @param commands the other commands to create a [SequentialGroup] with
     */
    fun then(vararg commands: Command) = SequentialGroup(
        this,
        *commands
    )

    /**
     * Returns a [ParallelGroup] with this command and an arbitrary number of other commands
     * @param commands the other commands to create a [ParallelGroup] with
     */
    fun and(vararg commands: Command) = ParallelGroup(
        this,
        *commands
    )

    /**
     * Returns a [ParallelRaceGroup] with this command and an arbitrary number of other commands
     * @param commands the other commands to create a [ParallelRaceGroup] with
     */
    fun raceWith(vararg commands: Command) = ParallelRaceGroup(
        this,
        *commands
    )

    /**
     * Returns a [PerpetualCommand] that wraps this command
     */
    fun perpetually() = PerpetualCommand(this)

    /**
     * Returns a [SequentialGroup] with a [Delay] and then this command
     * @param time the time-span for the [Delay]
     */
    fun afterTime(time: TimeSpan) = SequentialGroup(
        Delay(time),
        this
    )

    /**
     * Returns a [SequentialGroup] with a [Delay] and then this command
     * @param time the time-span for the [Delay], in seconds
     */
    fun afterTime(time: Double) = afterTime(time.sec)

    /**
     * Returns a [SequentialGroup] with a [Delay] and then this command
     * @param time the time-span for the [Delay], in seconds
     */
    fun afterTime(time: Int) = afterTime(time.sec)

    /**
     * Returns a [SequentialGroup] with this command and then a [Delay]
     * @param time the time-span for the [Delay]
     */
    fun thenWait(time: TimeSpan) = SequentialGroup(
        this,
        Delay(time)
    )

    /**
     * Returns a [SequentialGroup] with this command and then a [Delay]
     * @param time the time-span for the [Delay], in seconds
     */
    fun thenWait(time: Double) = thenWait(time.sec)

    /**
     * Returns a [SequentialGroup] with this command and then a [Delay]
     * @param time the time-span for the [Delay]
     */
    fun thenWait(time: Int) = thenWait(time.sec)

    /**
     * Returns a [ParallelDeadlineGroup] with this command and the passed command as the deadline
     * @param deadline the [Command] to use as the deadline
     */
    fun withDeadline(deadline: Command) = ParallelDeadlineGroup(
        deadline,
        this
    )

    /**
     * Returns a [ParallelDeadlineGroup] with this command as the deadline
     * @param commands the other commands to create a [ParallelDeadlineGroup] with
     */
    fun asDeadline(vararg commands: Command) = ParallelDeadlineGroup(
        this,
        *commands
    )

    /**
     * Returns a [ForcedParallelCommand] with this command
     */
    fun forcedParallel() = ForcedParallelCommand(this)

    // endregion
}