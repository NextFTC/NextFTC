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

package com.rowanmcalpin.nextftc.ftc.components

import com.rowanmcalpin.nextftc.core.Subsystem
import com.rowanmcalpin.nextftc.core.SubsystemGroup
import com.rowanmcalpin.nextftc.core.command.CommandManager

/**
 * This component adds [Subsystem]s to your OpMode
 */
class SubsystemComponent(vararg var subsystems: Subsystem): NextComponent {
    override fun preInit() {
        subsystems = expandSubsystems(subsystems.toSet()).toTypedArray()
        initSubsystems()
    }

    override fun preWaitForStart() {
        subsystems.forEach {
            it.periodic()

            // Check if there are any commands running that use the subsystem, or if we can safely
            // schedule its default command
            if (!CommandManager.hasCommandsUsing(it)) {
                CommandManager.scheduleCommand(it.defaultCommand)
            }
        }
    }

    override fun preUpdate() {
        subsystems.forEach {
            it.periodic()

            // Check if there are any commands running that use the subsystem, or if we can safely
            // schedule its default command
            if (!CommandManager.hasCommandsUsing(it)) {
                CommandManager.scheduleCommand(it.defaultCommand)
            }
        }
    }

    /**
     * Called internally to initialize subsystems.
     */
    private fun initSubsystems() {
        subsystems.forEach {
            it.initialize()
        }
    }

    /**
     * Given a set of subsystems (including groups), this extracts the subsystems from within those
     * groups and returns the set of the subsystems in the set plus the subsystems that are children
     * of the groups (if any).
     * @param subsystems the set of subsystems to expand
     * @return the expanded set of subsystems
     */
    private fun expandSubsystems(subsystems: Set<Subsystem>): Set<Subsystem> {
        val expanded = mutableListOf<Subsystem>()

        for (subsystem in subsystems) {
            if (subsystem is SubsystemGroup) {
                expanded += expandSubsystemGroup(subsystem)
            }
            expanded += subsystem
        }

        return expanded.toSet()
    }

    /**
     * Expands a subsystem group (recursively)
     */
    private fun expandSubsystemGroup(group: SubsystemGroup): List<Subsystem> {
        val expanded = mutableListOf<Subsystem>()

        for (child in group.subsystems) {
            if (child is SubsystemGroup) {
                expanded += expandSubsystemGroup(child)
            }
            expanded += child
        }

        return expanded
    }
}