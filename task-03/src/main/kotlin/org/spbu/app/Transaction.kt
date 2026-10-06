package org.spbu.app

import java.io.Closeable

class FakeDatabase(
    balances: Map<Int, Int> = emptyMap(),
    stock: Map<String, Int> = emptyMap(),
    auditLog: List<String> = emptyList()
) {
    private var savedBalances = balances.toMutableMap()
    private var savedStock = stock.toMutableMap()
    private val savedAuditLog = auditLog.toMutableList()

    val auditLog: List<String>
        get() {
            return savedAuditLog.toList()
        }

    fun balanceOf(userId: Int): Int {
        val balance = savedBalances[userId]
        if (balance == null) {
            return 0
        }
        return balance
    }

    fun stockOf(sku: String): Int {
        val quantity = savedStock[sku]
        if (quantity == null) {
            return 0
        }
        return quantity
    }

    internal fun applyAll(ops: List<DbOperation>) {
        val balancesCopy = savedBalances.toMutableMap()
        val stockCopy = savedStock.toMutableMap()
        val auditCopy = savedAuditLog.toMutableList()

        for (operation in ops) {
            operation.apply(balancesCopy, stockCopy, auditCopy)
        }

        savedBalances = balancesCopy
        savedStock = stockCopy
        savedAuditLog.clear()
        savedAuditLog.addAll(auditCopy)
    }
}

sealed interface DbOperation {
    fun apply(
        balances: MutableMap<Int, Int>,
        stock: MutableMap<String, Int>,
        auditLog: MutableList<String>
    )
}

data class Charge(val userId: Int, val amount: Int) : DbOperation {
    override fun apply(
        balances: MutableMap<Int, Int>,
        stock: MutableMap<String, Int>,
        auditLog: MutableList<String>
    ) {
        val savedBalance = balances[userId]
        var currentBalance = 0
        if (savedBalance != null) {
            currentBalance = savedBalance
        }

        if (currentBalance < amount) {
            throw IllegalStateException()
        }

        balances[userId] = currentBalance - amount
    }
}

data class ReserveStock(val sku: String, val qty: Int) : DbOperation {
    override fun apply(
        balances: MutableMap<Int, Int>,
        stock: MutableMap<String, Int>,
        auditLog: MutableList<String>
    ) {
        val savedQuantity = stock[sku]
        var currentQuantity = 0
        if (savedQuantity != null) {
            currentQuantity = savedQuantity
        }

        if (currentQuantity < qty) {
            throw IllegalStateException()
        }

        stock[sku] = currentQuantity - qty
    }
}

data class Audit(val message: String) : DbOperation {
    override fun apply(
        balances: MutableMap<Int, Int>,
        stock: MutableMap<String, Int>,
        auditLog: MutableList<String>
    ) {
        auditLog.add(message)
    }
}

class Transaction(private val db: FakeDatabase) : Closeable {
    private val pendingOperations = mutableListOf<DbOperation>()
    private var completed = false

    fun addOperation(op: DbOperation) {
        pendingOperations.add(op)
    }

    fun commit() {
        db.applyAll(pendingOperations)
        completed = true
    }

    fun rollback() {
        pendingOperations.clear()
        completed = true
    }

    override fun close() {
        if (completed == false) {
            rollback()
        }
    }
}

fun <T> withTransaction(db: FakeDatabase, action: (Transaction) -> T): T {
    val transaction = Transaction(db)

    try {
        val result = action(transaction)
        transaction.commit()
        return result
    } finally {
        transaction.close()
    }
}
