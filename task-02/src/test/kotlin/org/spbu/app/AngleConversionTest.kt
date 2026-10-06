package org.spbu.app

import kotlin.math.PI
import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertTrue

class AngleConversionTest {
    @Test
    fun `deg to rad`() {
        assertNear(0.0, degToRad(0.0))
        assertNear(PI, degToRad(180.0))
        assertNear(PI / 2, degToRad(90.0))
    }

    @Test
    fun `rad to deg`() {
        assertNear(0.0, radToDeg(0.0))
        assertNear(180.0, radToDeg(PI))
        assertNear(90.0, radToDeg(PI / 2))
    }

    @Test
    fun `round trip`() {
        val x = 12.345
        assertNear(x, radToDeg(degToRad(x)))
    }

    private fun assertNear(expected: Double, actual: Double, eps: Double = 1e-9) {
        assertTrue(abs(expected - actual) <= eps, "Expected $expected, got $actual")
    }
}
