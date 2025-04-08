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

package com.rowanmcalpin.nextftc.pedro

import com.pedropathing.follower.Follower
import com.rowanmcalpin.nextftc.core.components.Component
import com.rowanmcalpin.nextftc.ftc.OpModeData

/**
 * This component adds PedroPathing to your OpMode. It automatically sets the constants and
 * instantiates the follower.
 */
class PedroComponent(val fConstants: Class<*>, val lConstants: Class<*>) : Component {
    override fun preInit() {
        _follower = Follower(OpModeData.hardwareMap, fConstants, lConstants)
    }

    override fun preUpdate() {
        follower.update()
    }

    override fun postStop() {
        _follower = null
    }

    companion object PedroFollower {
        private var _follower: Follower? = null

        @JvmStatic
        @get:JvmName("follower")
        val follower: Follower
            get() = _follower
                ?: error("Follower is not initialized. Make sure to add PedroComponent to your OpMode!")
    }
}