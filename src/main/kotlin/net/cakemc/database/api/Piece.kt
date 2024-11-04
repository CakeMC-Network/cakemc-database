package net.cakemc.database.api

import java.io.Serializable

/**
 * The type Piece.
 */
class Piece
/**
 * Instantiates a new Piece.
 *
 * @param index    the index
 * @param id       the id
 * @param elements the elements
 */(index: Int, id: Long, val elements: MutableMap<String, Any>) :
    DatabaseRecord(index, id), Serializable {
    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    fun add(key: String, value: Int): Piece {
        elements[key] = value
        return this
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    fun add(key: String, value: Long): Piece {
        elements[key] = value
        return this
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    fun add(key: String, value: Double): Piece {
        elements[key] = value
        return this
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    fun add(key: String, value: Float): Piece {
        elements[key] = value
        return this
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    fun add(key: String, value: Boolean): Piece {
        elements[key] = value
        return this
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    fun add(key: String, value: Char): Piece {
        elements[key] = value
        return this
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    fun add(key: String, value: Byte): Piece {
        elements[key] = value
        return this
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    fun add(key: String, value: Short): Piece {
        elements[key] = value
        return this
    }

    /**
     * Add.
     *
     * @param key   the key
     * @param value the value
     */
    fun add(key: String, value: String): Piece {
        elements[key] = value
        return this
    }

    /**
     * Set.
     *
     * @param <T>   the type parameter
     * @param key   the key
     * @param value the value
    </T> */
    fun <T : Any> set(key: String, value: T): Piece {
        elements[key] = value
        return this
    }

    /**
     * Get t.
     *
     * @param <T>  the type parameter
     * @param key  the key
     * @param type the type
     * @return the t
    </T> */
    fun <T> get(key: String, type: Class<T>): T {
        val value = elements[key]
        if (type.isInstance(value)) {
            return type.cast(value)
        }
        throw ClassCastException("Value for key '" + key + "' is not of type " + type.name)
    }


    /**
     * Gets int.
     *
     * @param key the key
     * @return the int
     */
    fun getInt(key: String): Int {
        val value = elements[key]
        if (value is Int) {
            return value
        }
        throw ClassCastException("Value for key '$key' is not of type int.")
    }

    /**
     * Gets long.
     *
     * @param key the key
     * @return the long
     */
    fun getLong(key: String): Long {
        val value = elements[key]
        if (value is Long) {
            return value
        }
        throw ClassCastException("Value for key '$key' is not of type long.")
    }

    /**
     * Gets double.
     *
     * @param key the key
     * @return the double
     */
    fun getDouble(key: String): Double {
        val value = elements[key]
        if (value is Double) {
            return value
        }
        throw ClassCastException("Value for key '$key' is not of type double.")
    }

    /**
     * Gets float.
     *
     * @param key the key
     * @return the float
     */
    fun getFloat(key: String): Float {
        val value = elements[key]
        if (value is Float) {
            return value
        }
        throw ClassCastException("Value for key '$key' is not of type float.")
    }

    /**
     * Gets boolean.
     *
     * @param key the key
     * @return the boolean
     */
    fun getBoolean(key: String): Boolean {
        val value = elements[key]
        if (value is Boolean) {
            return value
        }
        throw ClassCastException("Value for key '$key' is not of type boolean.")
    }

    /**
     * Gets char.
     *
     * @param key the key
     * @return the char
     */
    fun getChar(key: String): Char {
        val value = elements[key]
        if (value is Char) {
            return value
        }
        throw ClassCastException("Value for key '$key' is not of type char.")
    }

    /**
     * Gets byte.
     *
     * @param key the key
     * @return the byte
     */
    fun getByte(key: String): Byte {
        val value = elements[key]
        if (value is Byte) {
            return value
        }
        throw ClassCastException("Value for key '$key' is not of type byte.")
    }

    /**
     * Gets short.
     *
     * @param key the key
     * @return the short
     */
    fun getShort(key: String): Short {
        val value = elements[key]
        if (value is Short) {
            return value
        }
        throw ClassCastException("Value for key '$key' is not of type short.")
    }

    /**
     * Gets string.
     *
     * @param key the key
     * @return the string
     */
    fun getString(key: String): String {
        val value = elements[key]
        if (value is String) {
            return value
        }
        throw ClassCastException("Value for key '$key' is not of type short.")
    }

    /**
     * Contains boolean.
     *
     * @param key the key
     * @return the boolean
     */
    fun contains(key: String): Boolean {
        return elements.containsKey(key)
    }

    /**
     * Remove.
     *
     * @param key the key
     */
    fun remove(key: String) {
        elements.remove(key)
    }

    override fun size(): Int {
        return elements.size
    }

    fun keySet(): Set<String> {
        return elements.keys
    }

    override fun toString(): String {
        return "Piece{" +
                "elements=" + elements +
                ", index=" + index +
                ", id=" + id +
                '}'
    }
}
