package net.cakemc.database.collection

import net.cakemc.database.api.Piece
import net.cakemc.database.callbacks.*
import net.cakemc.database.cursor.Cursor
import net.cakemc.database.cursor.CursorSupplier
import net.cakemc.database.cursor.PieceCursorSupplier
import net.cakemc.database.filter.Filter
import net.cakemc.database.filter.PieceFilter

/**
 * The interface Collection.
 *
 * @param <T> the type parameter
</T> */
@Suppress("unused")
interface Collection<T> {
    /**
     * Multi piece async.
     *
     * @param filter         the filter
     * @param cursorSupplier the cursor supplier
     * @param callBack       the call back
     */
    // find
    fun multiPieceAsync(filter: PieceFilter, cursorSupplier: PieceCursorSupplier, callBack: PieceAsyncMultiCallBack)

    /**
     * Single piece async.
     *
     * @param supplier         the supplier
     * @param documentCallBack the document call back
     */
    fun singlePieceAsync(supplier: PieceFilter, documentCallBack: PieceAsyncCallBack)

    /**
     * Multi async.
     *
     * @param filter         the filter
     * @param cursorSupplier the cursor supplier
     * @param callBack       the call back
     */
    fun multiAsync(filter: Filter<T>, cursorSupplier: CursorSupplier<T, Cursor<T>>, callBack: AsyncMultiCallBack<T>)

    /**
     * Single async.
     *
     * @param supplier         the supplier
     * @param documentCallBack the document call back
     */
    fun singleAsync(supplier: Filter<T>, documentCallBack: AsyncCallBack<T>)

    /**
     * Multi piece cursor.
     *
     * @param filter         the filter
     * @param cursorSupplier the cursor supplier
     * @return the cursor
     */
    fun multiPiece(filter: PieceFilter, cursorSupplier: PieceCursorSupplier): Cursor<Piece>

    /**
     * Single piece piece.
     *
     * @param supplier the supplier
     * @return the piece
     */
    fun singlePiece(supplier: PieceFilter): Piece

    /**
     * Multi cursor.
     *
     * @param filter         the filter
     * @param cursorSupplier the cursor supplier
     * @return the cursor
     */
    fun multi(filter: Filter<T>, cursorSupplier: CursorSupplier<T, Cursor<T>>): Cursor<T>

    /**
     * Single t.
     *
     * @param supplier the supplier
     * @return the t
     */
    fun single(supplier: Filter<T>): T

    /**
     * Replace one piece.
     *
     * @param filter  the filter
     * @param element the element
     */
    // replace
    fun replaceOnePiece(filter: PieceFilter, element: Piece)

    /**
     * Replace one piece async.
     *
     * @param filter   the filter
     * @param element  the element
     * @param listener the listener
     */
    fun replaceOnePieceAsync(filter: PieceFilter, element: Piece, listener: DatabaseListener)

    /**
     * Replace one.
     *
     * @param filter  the filter
     * @param element the element
     */
    fun replaceOne(filter: Filter<T>, element: T)

    /**
     * Replace one async.
     *
     * @param filter   the filter
     * @param element  the element
     * @param listener the listener
     */
    fun replaceOneAsync(filter: Filter<T>, element: T, listener: DatabaseListener)

    /**
     * Update one piece.
     *
     * @param filter  the filter
     * @param element the element
     */
    // update
    fun updateOnePiece(filter: PieceFilter, element: Piece)

    /**
     * Update one piece async.
     *
     * @param filter   the filter
     * @param element  the element
     * @param listener the listener
     */
    fun updateOnePieceAsync(filter: PieceFilter, element: Piece, listener: DatabaseListener)

    /**
     * Update one.
     *
     * @param filter  the filter
     * @param element the element
     */
    fun updateOne(filter: Filter<T>, element: T)

    /**
     * Update one async.
     *
     * @param filter   the filter
     * @param element  the element
     * @param listener the listener
     */
    fun updateOneAsync(filter: Filter<T>, element: T, listener: DatabaseListener)

    /**
     * Delete one piece.
     *
     * @param element the element
     */
    // delete
    fun deleteOnePiece(element: Piece)

    /**
     * Delete many piece.
     *
     * @param element the element
     */
    fun deleteManyPiece(vararg element: Piece)

    /**
     * Delete one piece async.
     *
     * @param element  the element
     * @param listener the listener
     */
    fun deleteOnePieceAsync(element: Piece, listener: DatabaseListener)

    /**
     * Delete many piece async.
     *
     * @param listener the listener
     * @param element  the element
     */
    fun deleteManyPieceAsync(listener: DatabaseListener, vararg element: Piece)

    /**
     * Delete one.
     *
     * @param element the element
     */
    fun deleteOne(element: T)

    /**
     * Delete many.
     *
     * @param element the element
     */
    fun deleteMany(element: Array<T>)

    /**
     * Delete one async.
     *
     * @param element  the element
     * @param listener the listener
     */
    fun deleteOneAsync(element: T, listener: DatabaseListener)

    /**
     * Delete many async.
     *
     * @param element  the element
     * @param listener the listener
     */
    fun deleteManyAsync(element: Array<T>, listener: DatabaseListener)

    /**
     * Insert one piece.
     *
     * @param element the element
     */
    // insert
    fun insertOnePiece(element: Piece)

    /**
     * Insert one piece async.
     *
     * @param element  the element
     * @param listener the listener
     */
    fun insertOnePieceAsync(element: Piece, listener: DatabaseListener)

    /**
     * Insert one.
     *
     * @param element the element
     */
    fun insertOne(element: T)

    /**
     * Insert one async.
     *
     * @param element  the element
     * @param listener the listener
     */
    fun insertOneAsync(element: T, listener: DatabaseListener)

    /**
     * Define one t.
     *
     * @return the t
     */
    // creation
    fun defineOne(): T

    /**
     * Define one peace piece.
     *
     * @return the piece
     */
    fun defineOnePeace(): Piece


    /**
     * Gets id.
     *
     * @return the id
     */
    val id: Long

    /**
     * Gets name.
     *
     * @return the name
     */
    val name: String

    /**
     * Collect list.
     *
     * @return the list
     */
    fun collect(): List<Piece>
}
