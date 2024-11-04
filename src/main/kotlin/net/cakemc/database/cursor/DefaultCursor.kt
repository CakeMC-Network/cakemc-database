package net.cakemc.database.cursor

import net.cakemc.database.api.Piece
import net.cakemc.database.filter.Filter

/**
 * The type Default cursor.
 */
class DefaultCursor
/**
 * Instantiates a new Default cursor.
 *
 * @param pieces the pieces
 */(
    /**
     * Gets pieces.
     *
     * @return the pieces
     */
    var pieces: List<Piece?>
) : Cursor<Piece?>() {

    override fun limit(number: Int): Cursor<Piece?> {
        this.pieces = pieces.stream().limit(number.toLong()).toList()
        return this
    }

    override fun sort(comparator: Comparator<Piece?>): Cursor<Piece?> {
        pieces.sortedWith(comparator)
        return this
    }

    override fun index(element: Piece?): Int {
        return pieces.indexOf(element)
    }

    override fun index(filter: Filter<Piece?>): Int {
        val single = pieces.stream()
            .filter { document: Piece? -> filter.matches(document) }
            .findFirst()
            .orElse(null)

        if (single == null) return -1

        return pieces.indexOf(single)
    }

    override fun collect(): List<Piece?> {
        return pieces
    }
}
