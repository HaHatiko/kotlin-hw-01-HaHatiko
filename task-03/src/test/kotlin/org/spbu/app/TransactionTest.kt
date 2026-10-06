package org.spbu.app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TransactionTest {
    @Test
    fun `commit applies all operations atomically`() {
        val db = FakeDatabase(
            balances = mapOf(42 to 1_000),
            stock = mapOf("SKU-1" to 10)
        )

        val res = withTransaction(db) { tx ->
            tx.addOperation(Charge(userId = 42, amount = 100))
            tx.addOperation(ReserveStock(sku = "SKU-1", qty = 1))
            tx.addOperation(Audit("order created"))
            "ok"
        }

        assertEquals("ok", res)
        assertEquals(900, db.balanceOf(42))
        assertEquals(9, db.stockOf("SKU-1"))
        assertEquals(listOf("order created"), db.auditLog)
    }

    @Test
    fun `commit fails - nothing is applied`() {
        val db = FakeDatabase(
            balances = mapOf(42 to 50),
            stock = mapOf("SKU-1" to 10)
        )

        assertFailsWith<IllegalStateException> {
            withTransaction(db) { tx ->
                tx.addOperation(Charge(userId = 42, amount = 100)) // not enough funds
                tx.addOperation(ReserveStock(sku = "SKU-1", qty = 1))
            }
        }

        // atomicity
        assertEquals(50, db.balanceOf(42))
        assertEquals(10, db.stockOf("SKU-1"))
        assertEquals(emptyList(), db.auditLog)
    }

    @Test
    fun `multiple transactions see committed state`() {
        val db = FakeDatabase(
            balances = mapOf(1 to 500),
            stock = mapOf("SKU-1" to 2)
        )

        withTransaction(db) { tx ->
            tx.addOperation(ReserveStock("SKU-1", 1))
        }

        withTransaction(db) { tx ->
            tx.addOperation(ReserveStock("SKU-1", 1))
        }

        assertEquals(0, db.stockOf("SKU-1"))

        // Third transaction must fail and not change state
        assertFailsWith<IllegalStateException> {
            withTransaction(db) { tx ->
                tx.addOperation(ReserveStock("SKU-1", 1))
            }
        }
        assertEquals(0, db.stockOf("SKU-1"))
    }
}
