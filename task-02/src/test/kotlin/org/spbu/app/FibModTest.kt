package org.spbu.app

import kotlin.test.Test
import kotlin.test.assertEquals

class FibModTest {
    @Test
    fun `small values`() {
        assertEquals(0L, fibMod(0, 1_000_000_007L))
        assertEquals(1L, fibMod(1, 1_000_000_007L))
        assertEquals(1L, fibMod(2, 1_000_000_007L))
        assertEquals(2L, fibMod(3, 1_000_000_007L))
        assertEquals(3L, fibMod(4, 1_000_000_007L))
        assertEquals(5L, fibMod(5, 1_000_000_007L))
    }

    @Test
    fun `works with non-prime mod too`() {
        assertEquals(55L % 1000L, fibMod(10, 1000))
    }

    @Test
    fun `large n - exact`() {
        val n = 1_000_000_000_000L
        val mod = 1_000_000_007L

        // Precomputed with Python:
        // F(1_000_000_000_000) mod 1_000_000_007 = 730695249
        assertEquals(730_695_249L, fibMod(n, mod))
    }
}
