package org.spbu.app

class PhoneBook {
    private val data = mutableMapOf<String, String>()

    fun add(name: String, phone: String) {
        data[name] = phone
    }

    fun find(name: String): String? {
        return data[name]
    }

    fun remove(name: String): Boolean {
        return data.remove(name) != null
    }

    fun size(): Int {
        return data.size
    }
}