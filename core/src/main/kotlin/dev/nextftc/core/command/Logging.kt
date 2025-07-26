package dev.nextftc.core.command

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object CommandSerializer : KSerializer<Command> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Command") {
        element()
    }

    override fun serialize(encoder: Encoder, value: Command) {
        TODO("Not yet implemented")
    }

    override fun deserialize(decoder: Decoder): Command {
        TODO("Not yet implemented")
    }
}