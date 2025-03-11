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