package net.cakemc.database.api

/**
 * The type Database record.
 */
abstract class DatabaseRecord
/**
 * Instantiates a new Database record.
 *
 * @param index the index
 * @param id    the id
 */ protected constructor(
    /**
     * The Index.
     */
    @JvmField val index: Int,
    /**
     * The Id.
     */
    @JvmField val id: Long
) {
    /**
     * Gets index.
     *
     * @return the index
     */
    /**
     * Gets id.
     *
     * @return the id
     */

    /**
     * Size int.
     *
     * @return the int
     */
    abstract fun size(): Int
}
