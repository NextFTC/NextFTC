package dev.nextftc.core.command
import dev.nextftc.core.commands.CommandManager
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotHave
import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import kotlin.test.assertEquals

data object A
data object B
data object C

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
    fun `test requirement collision, interruptible`() {
        val a = labelCommand("A").requires(A).setInterruptible(true)
        val b = labelCommand("B").requires(A)

        CommandManager.scheduleCommand(a)
        CommandManager.run()

        CommandManager.isScheduled(a) shouldBe true

        CommandManager.scheduleCommand(b)
        CommandManager.run()

        CommandManager.isScheduled(a) shouldBe false
        CommandManager.isScheduled(b) shouldBe true
    }

    @Test
    fun `test requirement collision, not interruptible`() {
        val a = labelCommand("A").requires(A).setInterruptible(false)
        val b = labelCommand("B").requires(A)

        CommandManager.scheduleCommand(a)
        CommandManager.run()

        CommandManager.isScheduled(a) shouldBe true

        CommandManager.scheduleCommand(b)
        CommandManager.run()

        CommandManager.isScheduled(a) shouldBe true
        CommandManager.isScheduled(b) shouldBe false
    }

    @Test
    fun `test requirements in groups, interruptible`() {
        val a = labelCommand("A").requires(A)
        val b = labelCommand("B").requires(B)
        val ab = a.then(b).setInterruptible(true)

        val c = labelCommand("C").requires(A)

        CommandManager.scheduleCommand(ab)
        CommandManager.run()

        CommandManager.isScheduled(ab) shouldBe true
        CommandManager.isScheduled(a) shouldBe true
        CommandManager.isScheduled(b) shouldBe true
        CommandManager.isScheduled(c) shouldBe false

        CommandManager.scheduleCommand(c)
        CommandManager.run()

        CommandManager.isScheduled(ab) shouldBe false
        CommandManager.isScheduled(a) shouldBe false
        CommandManager.isScheduled(b) shouldBe false
        CommandManager.isScheduled(c) shouldBe true
    }

    @Test
    fun `test requirements in groups, not interruptible`() {
        val a = labelCommand("A").requires(A)
        val b = labelCommand("B").requires(B)
        val ab = a.then(b).setInterruptible(false)

        val c = labelCommand("C").requires(A)

        CommandManager.scheduleCommand(ab)
        CommandManager.run()

        CommandManager.isScheduled(ab) shouldBe true
        CommandManager.isScheduled(a) shouldBe true
        CommandManager.isScheduled(b) shouldBe true
        CommandManager.isScheduled(c) shouldBe false

        CommandManager.scheduleCommand(c)
        CommandManager.run()

        CommandManager.isScheduled(ab) shouldBe true
        CommandManager.isScheduled(a) shouldBe true
        CommandManager.isScheduled(b) shouldBe true
        CommandManager.isScheduled(c) shouldBe false
    }
}