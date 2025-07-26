package dev.nextftc.ftc.components

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import dev.nextftc.core.components.Component
import dev.nextftc.ftc.ActiveOpMode
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * A component used to generate properties upon OpMode initialization.
 * Should only be used in a NextFTCOpmode,
 * using its [dev.nextftc.ftc.NextFTCOpMode.onInit] function.
 *
 * @param block function to be evaluated upon initialization
 * @param V the type of the property
 */
class Initializer<V>(val block: OpMode.() -> V) : Component, ReadOnlyProperty<OpMode, V> {
    var value: V? = null

    override fun preInit() {
        value = ActiveOpMode.it!!.block()
    }

    override fun getValue(
        thisRef: OpMode,
        property: KProperty<*>
    ): V = value!!
}