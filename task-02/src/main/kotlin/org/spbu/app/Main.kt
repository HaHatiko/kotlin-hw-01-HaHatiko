package org.spbu.app

import kotlin.math.PI

private const val PRIME_SUFFIX = " is prime"

fun main() {
    val n = readln().trim().toLong()
    val list = primeFactors(n)
    
    if (list.size == 1) {
        println("$n$PRIME_SUFFIX")
    } else {
        print("$n = ")
        for (i in list.indices) {
            print(list[i])
            if (i != list.size - 1) print(" * ")
        }
        println()
    }
}

internal fun primeFactors(n: Long): List<Long> {
    var num = n
    val out = mutableListOf<Long>()
    
    var d = 2L
    while (num % d == 0L) {
        out.add(d)
        num /= d
    }
    
    d = 3L
    while (d * d <= num) {
        while (num % d == 0L) {
            out.add(d)
            num /= d
        }
        d += 2L
    }
    
    if (num > 1L) {
        out.add(num)
    }
    
    return out
}

fun multiplyMod(a: Array<LongArray>, b: Array<LongArray>, mod: Long): Array<LongArray> {
    val n = a.size
    val m = a[0].size
    val k = b[0].size
    
    require(b.size == m)
    
    val c = Array(n) { LongArray(k) }
    
    for (i in 0 until n) {
        for (j in 0 until k) {
            var s = 0L
            for (t in 0 until m) {
                s = (s + a[i][t] * b[t][j]) % mod
            }
            c[i][j] = s
        }
    }
    
    return c
}

fun fibMod(n: Long, mod: Long): Long {
    if (n == 0L) return 0L
    if (n == 1L) return 1L
    
    var r = arrayOf(
        longArrayOf(1L, 0L),
        longArrayOf(0L, 1L)
    )
    var b = arrayOf(
        longArrayOf(1L, 1L),
        longArrayOf(1L, 0L)
    )
    
    var p = n
    
    while (p > 0L) {
        if (p % 2L == 1L) {
            r = multiplyMod(r, b, mod)
        }
        b = multiplyMod(b, b, mod)
        p /= 2L
    }
    
    return r[0][1]
}

fun degToRad(deg: Double): Double {
    return deg * PI / 180.0
}

fun radToDeg(rad: Double): Double {
    return rad * 180.0 / PI
}
