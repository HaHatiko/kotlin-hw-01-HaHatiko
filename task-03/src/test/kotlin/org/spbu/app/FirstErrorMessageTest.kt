package org.spbu.app

import kotlin.test.Test
import kotlin.test.assertEquals

class FirstErrorMessageTest {
    @Test
    fun `returns first error message for service`() {
        val records = listOf(
            LogRecord("auth", LogLevel.ERROR, "first", emptyList()),
            LogRecord("auth", LogLevel.ERROR, "second", emptyList()),
            LogRecord("payments", LogLevel.ERROR, "pay", emptyList())
        )

        assertEquals("first", firstErrorMessageOrDefault(records, "auth"))
    }

    @Test
    fun `returns default when no errors`() {
        val records = listOf(
            LogRecord("search", LogLevel.INFO, "ok", emptyList()),
            LogRecord("search", LogLevel.WARN, "slow", emptyList())
        )

        assertEquals("No errors", firstErrorMessageOrDefault(records, "search"))
    }
}
