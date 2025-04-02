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

package com.rowanmcalpin.nextftc.core.command.utility.statemachine

import com.rowanmcalpin.nextftc.core.command.Command
import com.rowanmcalpin.nextftc.core.command.CommandManager
import com.rowanmcalpin.nextftc.core.command.StateNotSetException
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand

/**
 * A command that acts as a finite state-machine
 *
 * Note that states must be added *before* the command is scheduled.
 * @param T the type of the state that will be used
 * @throws StateNotSetException if the command is scheduled before a state has been added
 * @author BeepBot99
 */
open class StateMachineCommand<T> : Command() {
    private val stateCommandMap: MutableMap<T, Command> = mutableMapOf()

    protected var currentState: T? = null
        set(value) {
            require(value in stateCommandMap.keys) { "State must be added to the state machine with .state() before using" }
            field = value
        }
    private var previousState: T? = null
    private lateinit var currentCommand: Command
    private var currentCommandRunning = false

    override var isDone: Boolean = false

    /**
     * Adds a new state to the state machine
     *
     * Is given the [StateMachineCommand] so that it can call [end] and [setState], if needed.
     * @param state the state to set
     * @param commandFactory a factory that returns a command to run when the current state is the set state
     * @return the [StateMachineCommand] for use with a Fluent API
     */
    fun state(
        state: T,
        commandFactory: (StateMachineCommand<T>) -> Command
    ): StateMachineCommand<T> = apply {
        currentState = currentState ?: state
        stateCommandMap[state] = commandFactory(this)
    }

    /**
     * Adds a new state to the state machine
     * @param state the state to set
     * @param command the command to run when the current state is the set state
     * @return the [StateMachineCommand] for use with a Fluent API
     */
    fun state(state: T, command: Command) = state(state) { command }

    override fun start() {
        currentState ?: throw StateNotSetException()
        previousState = currentState
        currentCommand = stateCommandMap[currentState]!!
        currentCommand.start()
        currentCommandRunning = true
    }

    override fun update() {
        if (currentCommandRunning) currentCommand.update()
        if (previousState == currentState) return

        currentCommand.stop(true)
        currentCommand = stateCommandMap[currentState]!!
        currentCommand.start()

        previousState = currentState
    }

    override fun stop(interrupted: Boolean) = currentCommand.stop(interrupted)

    /**
     * Stops the state machine
     */
    fun end() = InstantCommand { isDone = true }

    /**
     * Sets the state of the state machine
     * @param state a factory that returns the state to set
     * @return a [Command] that sets the state and ends instantly
     */
    fun setState(state: () -> T) = InstantCommand { currentState = state() }

    /**
     * Sets the state of the state machine
     * @param state the state to set
     * @return a [Command] that sets the state and ends instantly
     */
    fun setState(state: T) = setState { state }
}