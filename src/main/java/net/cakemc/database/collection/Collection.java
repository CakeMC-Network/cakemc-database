package net.cakemc.database.collection;

import net.cakemc.database.api.Piece;
import net.cakemc.database.callbacks.*;
import net.cakemc.database.cursor.Cursor;
import net.cakemc.database.cursor.CursorSupplier;
import net.cakemc.database.cursor.PieceCursorSupplier;
import net.cakemc.database.filter.*;

import java.util.List;

/**
 * The interface Collection.
 *
 * @param <T> the type parameter
 */
@SuppressWarnings("unused")
public interface Collection<T> {

    /**
     * Multi piece async.
     *
     * @param filter         the filter
     * @param cursorSupplier the cursor supplier
     * @param callBack       the call back
     */
// find
    void multiPieceAsync(PieceFilter filter, PieceCursorSupplier cursorSupplier, PieceAsyncMultiCallBack callBack);

    /**
     * Single piece async.
     *
     * @param supplier         the supplier
     * @param documentCallBack the document call back
     */
    void singlePieceAsync(PieceFilter supplier, PieceAsyncCallBack documentCallBack);

    /**
     * Multi async.
     *
     * @param filter         the filter
     * @param cursorSupplier the cursor supplier
     * @param callBack       the call back
     */
    void multiAsync(Filter<T> filter, CursorSupplier<T, Cursor<T>> cursorSupplier, AsyncMultiCallBack<T> callBack);

    /**
     * Single async.
     *
     * @param supplier         the supplier
     * @param documentCallBack the document call back
     */
    void singleAsync(Filter<T> supplier, AsyncCallBack<T> documentCallBack);

    /**
     * Multi piece cursor.
     *
     * @param filter         the filter
     * @param cursorSupplier the cursor supplier
     * @return the cursor
     */
    Cursor<Piece> multiPiece(PieceFilter filter, PieceCursorSupplier cursorSupplier);

    /**
     * Single piece piece.
     *
     * @param supplier the supplier
     * @return the piece
     */
    Piece singlePiece(PieceFilter supplier);

    /**
     * Multi cursor.
     *
     * @param filter         the filter
     * @param cursorSupplier the cursor supplier
     * @return the cursor
     */
    Cursor<T> multi(Filter<T> filter, CursorSupplier<T, Cursor<T>> cursorSupplier);

    /**
     * Single t.
     *
     * @param supplier the supplier
     * @return the t
     */
    T single(Filter<T> supplier);

    /**
     * Replace one piece.
     *
     * @param filter  the filter
     * @param element the element
     */
// replace
    void replaceOnePiece(PieceFilter filter, Piece element);

    /**
     * Replace one piece async.
     *
     * @param filter   the filter
     * @param element  the element
     * @param listener the listener
     */
    void replaceOnePieceAsync(PieceFilter filter, Piece element, DatabaseListener listener);

    /**
     * Replace one.
     *
     * @param filter  the filter
     * @param element the element
     */
    void replaceOne(Filter<T> filter, T element);

    /**
     * Replace one async.
     *
     * @param filter   the filter
     * @param element  the element
     * @param listener the listener
     */
    void replaceOneAsync(Filter<T> filter, T element, DatabaseListener listener);

    /**
     * Update one piece.
     *
     * @param filter  the filter
     * @param element the element
     */
// update
    void updateOnePiece(PieceFilter filter, Piece element);

    /**
     * Update one piece async.
     *
     * @param filter   the filter
     * @param element  the element
     * @param listener the listener
     */
    void updateOnePieceAsync(PieceFilter filter, Piece element, DatabaseListener listener);

    /**
     * Update one.
     *
     * @param filter  the filter
     * @param element the element
     */
    void updateOne(Filter<T> filter, T element);

    /**
     * Update one async.
     *
     * @param filter   the filter
     * @param element  the element
     * @param listener the listener
     */
    void updateOneAsync(Filter<T> filter, T element, DatabaseListener listener);

    /**
     * Delete one piece.
     *
     * @param element the element
     */
// delete
    void deleteOnePiece(Piece element);

    /**
     * Delete many piece.
     *
     * @param element the element
     */
    void deleteManyPiece(Piece... element);

    /**
     * Delete one piece async.
     *
     * @param element  the element
     * @param listener the listener
     */
    void deleteOnePieceAsync(Piece element, DatabaseListener listener);

    /**
     * Delete many piece async.
     *
     * @param listener the listener
     * @param element  the element
     */
    void deleteManyPieceAsync(DatabaseListener listener, Piece... element);

    /**
     * Delete one.
     *
     * @param element the element
     */
    void deleteOne(T element);

    /**
     * Delete many.
     *
     * @param element the element
     */
    void deleteMany(T[] element);

    /**
     * Delete one async.
     *
     * @param element  the element
     * @param listener the listener
     */
    void deleteOneAsync(T element, DatabaseListener listener);

    /**
     * Delete many async.
     *
     * @param element  the element
     * @param listener the listener
     */
    void deleteManyAsync(T[] element, DatabaseListener listener);

    /**
     * Insert one piece.
     *
     * @param element the element
     */
// insert
    void insertOnePiece(Piece element);

    /**
     * Insert one piece async.
     *
     * @param element  the element
     * @param listener the listener
     */
    void insertOnePieceAsync(Piece element, DatabaseListener listener);

    /**
     * Insert one.
     *
     * @param element the element
     */
    void insertOne(T element);

    /**
     * Insert one async.
     *
     * @param element  the element
     * @param listener the listener
     */
    void insertOneAsync(T element, DatabaseListener listener);

    /**
     * Define one t.
     *
     * @return the t
     */
// creation
    T defineOne();

    /**
     * Define one peace piece.
     *
     * @return the piece
     */
    Piece defineOnePeace();


    /**
     * Gets id.
     *
     * @return the id
     */
    long getId();

    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Collect list.
     *
     * @return the list
     */
    List<Piece> collect();
}
