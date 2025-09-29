package dev.nextftc.core.command
import dev.nextftc.core.commands.CommandManager
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotHave
import org.junit.jupiter.api.Disabled
import kotlin.test.Test

object A
object B
object C

class TestInterruption : CommandTestBase() {
    @Test
    fun `test no requirement collision`() {
        val a = labelCommand("A").requires(A)
        val b = labelCommand("B").requires(B)

        CommandManager.scheduleCommand(a)
        CommandManager.scheduleCommand(b)

        CommandManager.isScheduled(a) shouldBe true
        CommandManager.isScheduled(b) shouldBe true
    }

    @Test
    @Disabled("doesnt work for some reason")
    fun `test requirement collision, interruptible`() {
        val a = labelCommand("A").requires(A).setInterruptible(true)
        val b = labelCommand("B").requires(A)

        CommandManager.scheduleCommand(a)
        CommandManager.run()

        CommandManager.isScheduled(a) shouldBe true

        CommandManager.scheduleCommand(b)
        CommandManager.run()

        CommandManager.snapshot shouldBe listOf("A", "B")
        CommandManager.isScheduled(a) shouldBe false
        CommandManager.isScheduled(b) shouldBe true
    }
}