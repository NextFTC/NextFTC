package dev.nextftc.ftc.components

import dev.nextftc.core.command.CommandManager
import dev.nextftc.core.command.CommandSnapshot
import dev.nextftc.core.components.Component
import dev.nextftc.ftc.ActiveOpMode
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.ComparableTimeMark
import kotlin.time.TimeSource

val LOG_ROOT = File(AppUtil.ROOT_FOLDER, "NextFTC/logs")

val formatter = SimpleDateFormat("MM.dd.yyyy-hh.mm.ss.a", Locale.US)

private fun SimpleDateFormat.now() = this.format(Date())

val json = Json { prettyPrint = true }

private fun ActiveOpMode.name() = it.let {
    if (it == null) "OpMode"
    else it::class.simpleName ?: "OpMode"
}

object LoggingComponent : Component {
    lateinit var start: ComparableTimeMark
    val snapshots = mutableListOf<CommandSnapshot>()

    lateinit var currentFile: FileOutputStream

    fun createFile(name: String): FileOutputStream {
        val file = File(LOG_ROOT, name)
        LOG_ROOT.mkdirs()
        file.createNewFile()
        return file.outputStream()
    }

    override fun preInit() {
        start = TimeSource.Monotonic.markNow()
        currentFile = createFile("${ActiveOpMode.name()}_${formatter.now()}.json")
    }

    override fun postWaitForStart() {
        if (CommandManager.shouldLog) {
            snapshots += CommandSnapshot(
                start.elapsedNow(),
                CommandManager.runningCommands
            )
        }
    }

    override fun postUpdate() {
        if (CommandManager.shouldLog) {
            snapshots += CommandSnapshot(
                start.elapsedNow(),
                CommandManager.runningCommands
            )
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun postStop() {
        if (CommandManager.shouldLog) {
            snapshots += CommandSnapshot(
                start.elapsedNow(),
                CommandManager.runningCommands
            )
        }

        json.encodeToStream(snapshots, currentFile)
    }
}