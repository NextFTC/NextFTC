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

package dev.nextftc.core.components

import dev.nextftc.core.commands.CommandManager
import dev.nextftc.core.subsystems.Subsystem

/**
 * This component adds [Subsystem]s to your OpMode
 */
class SubsystemComponent(vararg subsystems: Subsystem) : Component {
    val subsystems = subsystems.flatMap { it.subsystems }.toSet()

    override fun preInit() {
        subsystems.forEach {
            it.initialize()
        }
    }

    override fun preWaitForStart() = updateSubsystems()

    override fun preUpdate() = updateSubsystems()

    private fun updateSubsystems() {
        subsystems.forEach {
            it.periodic()
            if (!CommandManager.hasCommandsUsing(it)) it.defaultCommand.schedule()
        }
    }
}