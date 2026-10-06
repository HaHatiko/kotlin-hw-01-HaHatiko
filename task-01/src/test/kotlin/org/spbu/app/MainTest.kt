package org.spbu.app

import kotlin.test.Test
import kotlin.test.assertEquals
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class MainTest {
    @Test
    fun `empty args - prints stranger`() {
        val output = runProgram(args = emptyArray())
        assertEquals("Привет, незнакомец\n", output)
    }

    @Test
    fun `one arg - uses it as name`() {
        val output = runProgram(args = arrayOf("Alice"))
        assertEquals("Привет, Alice\n", output)
    }

    @Test
    fun `first arg is blank - prints stranger`() {
        val output = runProgram(args = arrayOf("   "))
        assertEquals("Привет, незнакомец\n", output)
    }

    @Test
    fun `more than one arg - first one wins`() {
        val output = runProgram(args = arrayOf("Bob", "Ignored"))
        assertEquals("Привет, Bob\n", output)
    }

    private fun runProgram(args: Array<String>): String {
        val originalOut = System.out
        val outStream = ByteArrayOutputStream()

        try {
            System.setOut(PrintStream(outStream))

            main(args)

            return outStream.toString(Charsets.UTF_8)
        } finally {
            System.setOut(originalOut)
        }
    }
}
