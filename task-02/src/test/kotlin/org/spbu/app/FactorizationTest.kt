package org.spbu.app

import kotlin.test.Test
import kotlin.test.assertEquals
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class FactorizationTest {
    @Test
    fun `prime number`() {
        assertEquals("239 is prime\n", runProgram("239\n"))
    }

    @Test
    fun `composite number`() {
        assertEquals("12 = 2 * 2 * 3\n", runProgram("12\n"))
    }

    @Test
    fun `square of prime`() {
        assertEquals("49 = 7 * 7\n", runProgram("49\n"))
    }

    @Test
    fun `large power of two`() {
        assertEquals("1024 = 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2\n", runProgram("1024\n"))
    }

    @Test
    fun `near limit`() {
        // 10^12 = 2^12 * 5^12
        assertEquals(
            "1000000000000 = 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 2 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5\n",
            runProgram("1000000000000\n")
        )
    }

    private fun runProgram(input: String): String {
        val originalIn = System.`in`
        val originalOut = System.out

        val inStream = ByteArrayInputStream(input.toByteArray())
        val outStream = ByteArrayOutputStream()

        try {
            System.setIn(inStream)
            System.setOut(PrintStream(outStream))

            main()

            return outStream.toString(Charsets.UTF_8)
        } finally {
            System.setIn(originalIn)
            System.setOut(originalOut)
        }
    }
}
