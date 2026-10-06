package org.spbu.app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class CheckoutRequestBuilderTest {
    @Test
    fun `build success - normalizes fields`() {
        val req = CheckoutRequestBuilder().apply {
            userId = 42
            sku = "  SKU-1  "
            qty = 2
            comment = "  hello  "
        }.build()

        assertEquals(42, req.userId)
        assertEquals("SKU-1", req.sku)
        assertEquals(2, req.qty)
        assertEquals("hello", req.comment)
    }

    @Test
    fun `comment blank becomes null`() {
        val req = CheckoutRequestBuilder().apply {
            userId = 1
            sku = "SKU"
            qty = 1
            comment = "   "
        }.build()

        assertNull(req.comment)
    }

    @Test
    fun `missing required field`() {
        assertFailsWith<IllegalArgumentException> {
            CheckoutRequestBuilder().apply {
                sku = "SKU"
                qty = 1
            }.build()
        }
    }

    @Test
    fun `invalid values`() {
        assertFailsWith<IllegalArgumentException> {
            CheckoutRequestBuilder().apply {
                userId = 0
                sku = "SKU"
                qty = 1
            }.build()
        }

        assertFailsWith<IllegalArgumentException> {
            CheckoutRequestBuilder().apply {
                userId = 1
                sku = "   "
                qty = 1
            }.build()
        }

        assertFailsWith<IllegalArgumentException> {
            CheckoutRequestBuilder().apply {
                userId = 1
                sku = "SKU"
                qty = 0
            }.build()
        }
    }
}
