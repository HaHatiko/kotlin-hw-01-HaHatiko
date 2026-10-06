package org.spbu.app

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertFailsWith

class MatrixMultiplyTest {
    @Test
    fun `multiplies 2x3 and 3x2 with mod`() {
        val a = arrayOf(
            longArrayOf(1, 2, 3),
            longArrayOf(4, 5, 6)
        )
        val b = arrayOf(
            longArrayOf(7, 8),
            longArrayOf(9, 10),
            longArrayOf(11, 12)
        )

        val c = multiplyMod(a, b, mod = 1_000_000_007L)

        assertMatrixEquals(
            arrayOf(
                longArrayOf(58, 64),
                longArrayOf(139, 154)
            ),
            c
        )
    }

    @Test
    fun `mod is applied`() {
        val mod = 10L
        val a = arrayOf(longArrayOf(6, 6))           // 1x2
        val b = arrayOf(longArrayOf(6), longArrayOf(6)) // 2x1

        // 6*6 + 6*6 = 72 -> 2
        val c = multiplyMod(a, b, mod)
        assertMatrixEquals(arrayOf(longArrayOf(2)), c)
    }

    @Test
    fun `incompatible sizes`() {
        val a = arrayOf(longArrayOf(1, 2)) // 1x2
        val b = arrayOf(longArrayOf(1, 2)) // 1x2

        assertFailsWith<IllegalArgumentException> {
            multiplyMod(a, b, mod = 1_000_000_007L)
        }
    }

    private fun assertMatrixEquals(expected: Array<LongArray>, actual: Array<LongArray>) {
        require(expected.size == actual.size)
        for (i in expected.indices) {
            assertContentEquals(expected[i], actual[i], "Row $i differs")
        }
    }
}
