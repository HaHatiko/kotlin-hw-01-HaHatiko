package org.spbu.app

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PhoneBookTest {
    @Test
    fun `add and find`() {
        val pb = PhoneBook()
        pb.add("Alice", "+7-999-111-22-33")
        assertEquals("+7-999-111-22-33", pb.find("Alice"))
    }

    @Test
    fun `add overwrites existing`() {
        val pb = PhoneBook()
        pb.add("Alice", "111")
        pb.add("Alice", "222")
        assertEquals("222", pb.find("Alice"))
        assertEquals(1, pb.size())
    }

    @Test
    fun `remove existing`() {
        val pb = PhoneBook()
        pb.add("Bob", "123")

        assertTrue(pb.remove("Bob"))
        assertNull(pb.find("Bob"))
        assertEquals(0, pb.size())
    }

    @Test
    fun `remove missing`() {
        val pb = PhoneBook()
        assertFalse(pb.remove("Missing"))
    }
}
