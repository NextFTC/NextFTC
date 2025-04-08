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

import com.rowanmcalpin.nextftc.core.components.Component
import com.rowanmcalpin.nextftc.core.components.SubsystemComponent
import com.rowanmcalpin.nextftc.core.subsystems.Subsystem

/**
 * This is a collection of Components and includes a modified mutating builder
 */
class Components {
    private var components: List<Component> = listOf()

    /**
     * Adds a [com.rowanmcalpin.nextftc.core.components.SubsystemComponent] to your OpMode
     */
    fun useSubsystems(vararg subsystems: Subsystem): Components {
        components += SubsystemComponent(*subsystems)

        return this
    }


    /**
     * Adds a [BulkReadComponent] to your OpMode
     */
    fun useBulkReading(): Components {
        components += BulkReadComponent()

        return this
    }

    /**
     * Adds a custom [Component] to your OpMode
     */
    fun use(component: Component): Components {
        components += component

        return this
    }

    fun preInit() {
        components.forEach { it.preInit() }
    }

    fun postInit() {
        components.reversed().forEach { it.postInit() }
    }

    fun preWaitForStart() {
        components.forEach { it.preWaitForStart() }
    }

    fun postWaitForStart() {
        components.reversed().forEach { it.postWaitForStart() }
    }

    fun preStartButtonPressed() {
        components.forEach { it.preStartButtonPressed() }
    }

    fun postStartButtonPressed() {
        components.reversed().forEach { it.postStartButtonPressed() }
    }

    fun preUpdate() {
        components.forEach { it.preUpdate() }
    }

    fun postUpdate() {
        components.reversed().forEach { it.postUpdate() }
    }

    fun preStop() {
        components.forEach { it.preStop() }
    }

    fun postStop() {
        components.reversed().forEach { it.postStop() }
    }
}