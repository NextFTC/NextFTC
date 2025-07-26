package dev.nextftc.core.command

import dev.nextftc.core.command.groups.CommandGroup
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.encodeStructure
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit

/**
 * Generates a serialized format of a Command using its class name
 * and its requirements' class names.
 *
 * Cannot be used for deserialization.
 */
internal object CommandLogSerializer : KSerializer<Command> {
    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Command") {
        element<String>("name")
        element<String>("requirements")
        element<String>("children", isOptional=true)
    }

    override fun serialize(encoder: Encoder, value: Command) =
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.name)
            encodeStringElement(descriptor, 1, value.requirements.map { it::class.simpleName?: "Unknown Requirement" }.toString())

            if (value is CommandGroup) {
                encodeSerializableElement(descriptor, 2, ListSerializer(CommandLogSerializer), value.commands.toList())
            }
        }

    override fun deserialize(decoder: Decoder): Command {
        error { "THIS SHOULD ONLY BE USED AS A SERIALIZER FOR LOGGING; DO NOT USE FOR DESERIALIZATION" }
    }
}

/**
 * Serializes a command snapshot
 * (a list of commands running at an instance),
 * using [CommandLogSerializer].
 *
 * Cannot be used for deserialization.
 */
internal object CommandSnapshotSerializer : KSerializer<List<Command>> {
    val ls = ListSerializer(CommandLogSerializer)

    override val descriptor: SerialDescriptor = ls.descriptor

    override fun serialize(encoder: Encoder, value: List<Command>) {
        ls.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): List<Command> {
        error { "THIS SHOULD ONLY BE USED AS A SERIALIZER FOR LOGGING; DO NOT USE FOR DESERIALIZATION" }
    }
}

/**
 * Serializes and deserializes a duration represented in JSON as "<number>ms"
 */
internal object TimestampLogSerializer : KSerializer<Duration> {
    val primitive = PrimitiveSerialDescriptor("Timestamp", PrimitiveKind.STRING)

    override val descriptor = SerialDescriptor("Timestamp", primitive)

    override fun serialize(encoder: Encoder, value: Duration) {
        encoder.encodeString("${value.toDouble(DurationUnit.SECONDS)}s")
    }

    override fun deserialize(decoder: Decoder): Duration {
        return decoder.decodeString().removeSuffix("ms").toDouble().milliseconds
    }

}

/**
 * Represents a serializable snapshot of commands.
 *
 * @param timestamp duration since OpMode start
 * @param commands commands scheduled at this timestamp
 */
@Serializable
class CommandSnapshot(
    @Serializable(with = TimestampLogSerializer::class) val timestamp: Duration,
    @Serializable(with = CommandSnapshotSerializer::class) val commands: List<Command>
)