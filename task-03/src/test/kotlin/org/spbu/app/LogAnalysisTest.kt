package org.spbu.app

import kotlin.test.Test
import kotlin.test.assertEquals

class LogAnalysisTest {
    @Test
    fun `collects tags from error logs per service`() {
        val records = listOf(
            LogRecord("auth", LogLevel.INFO, "login ok", listOf("security")),
            LogRecord("auth", LogLevel.ERROR, "wrong password", listOf("security", "user")),
            LogRecord("auth", LogLevel.ERROR, "db timeout", listOf("db", "timeout")),
            LogRecord("payments", LogLevel.ERROR, "card declined", listOf("card", "user")),
            LogRecord("payments", LogLevel.WARN, "slow", listOf("timeout"))
        )

        val result = errorTagsByService(records)

        assertEquals(setOf("security", "user", "db", "timeout"), result["auth"])
        assertEquals(setOf("card", "user"), result["payments"])
    }

    @Test
    fun `service with no errors is absent`() {
        val records = listOf(
            LogRecord("search", LogLevel.INFO, "ok", listOf("health")),
            LogRecord("search", LogLevel.WARN, "slow", listOf("timeout"))
        )

        val result = errorTagsByService(records)

        assertEquals(emptyMap(), result)
    }
}
