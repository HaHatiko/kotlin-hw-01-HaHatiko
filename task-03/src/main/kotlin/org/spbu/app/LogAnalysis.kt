package org.spbu.app

fun errorTagsByService(records: List<LogRecord>): Map<String, Set<String>> {
    return records
        .filter { it.level == LogLevel.ERROR }
        .groupBy { it.service }
        .mapValues { it.value.flatMap { r -> r.tags }.toSet() }
}

fun firstErrorMessageOrDefault(records: List<LogRecord>, service: String): String {
    return records
        .firstOrNull { it.service == service && it.level == LogLevel.ERROR }
        ?.message
        ?: "No errors"
}

enum class LogLevel { DEBUG, INFO, WARN, ERROR }

data class LogRecord(
    val service: String,
    val level: LogLevel,
    val message: String,
    val tags: List<String>
)