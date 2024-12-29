package net.cakemc.database.filter

import net.cakemc.database.api.Piece
import net.cakemc.database.callbacks.ConditionalConsumer

/**
 * The type Filters.
 */
object Filters {
    /**
     * Eq piece piece filter.
     *
     * @param key   the key
     * @param value the value
     * @return the piece filter
     */
    @JvmStatic
    fun eq(key: String?, value: Any): PieceFilter {
        return PieceFilter { piece: Piece ->
            if (piece.elements[key] == null) return@PieceFilter false
            piece.elements[key] == value
        }
    }

    /**
     * Contains piece filter.
     *
     * @param key the key
     * @return the piece filter
     */
    @JvmStatic
    fun contains(key: String): PieceFilter {
        return PieceFilter { piece: Piece -> piece.contains(key) }
    }

    /**
     * Id piece filter.
     *
     * @param id the id
     * @return the piece filter
     */
    @JvmStatic
    fun id(id: Long): PieceFilter {
        return PieceFilter { piece: Piece -> piece.id == id }
    }

    /**
     * Index piece filter.
     *
     * @param index the index
     * @return the piece filter
     */
    @JvmStatic
    fun index(index: Int): PieceFilter {
        return PieceFilter { piece: Piece -> piece.index == index }
    }

    /**
     * Custom piece filter.
     *
     * @param <T>      the type parameter
     * @param key      the key
     * @param consumer the consumer
     * @return the piece filter
    </T> */
    @JvmStatic
    fun <T> custom(key: String, consumer: ConditionalConsumer<T?>): PieceFilter {
        return PieceFilter { piece: Piece ->
            if (!piece.contains(key)) return@PieceFilter false
            val value = piece.elements[key]
            consumer.expect(value as T?)
        }
    }

    /**
     * And piece filter
     *
     * @param filters the filters
     * @return the combined piece filter
     */
    @JvmStatic
    fun and(vararg filters: PieceFilter): PieceFilter {
        return PieceFilter { piece: Piece ->
            filters.all { it.matches(piece) }
        }
    }

    /**
     * Or piece filter
     *
     * @param filters the filters
     * @return the combined piece filter
     */
    @JvmStatic
    fun or(vararg filters: PieceFilter): PieceFilter {
        return PieceFilter { piece: Piece ->
            filters.any { it.matches(piece) }
        }
    }
}
