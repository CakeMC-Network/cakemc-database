package net.cakemc.database.collection

import net.cakemc.database.AbstractDatabase
import net.cakemc.database.api.DatabaseRecord
import net.cakemc.database.api.Piece
import net.cakemc.database.callbacks.DatabaseListener
import net.cakemc.database.callbacks.PieceAsyncCallBack
import net.cakemc.database.callbacks.PieceAsyncMultiCallBack
import net.cakemc.database.cursor.Cursor
import net.cakemc.database.cursor.PieceCursorSupplier
import net.cakemc.database.filter.PieceFilter
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import java.util.concurrent.ThreadLocalRandom

/**
 * Piece collection implementation.
 */
class PieceCollection(
    elements: MutableList<DatabaseRecord>,
    id: Long,
    name: String
) : AbstractCollection<DatabaseRecord>(elements, id, name) {

    override fun multiPieceAsync(filter: PieceFilter, cursorSupplier: PieceCursorSupplier, callBack: PieceAsyncMultiCallBack) {
        try {
            CompletableFuture.runAsync({
                val matchedElements = elements
                    .filter { filter.matches(it as Piece) }
                    .map { it as Piece }

                callBack.acceptFound(cursorSupplier.create(matchedElements))
            }, AbstractDatabase.EXECUTOR).get()
        } catch (exception: InterruptedException) {
            callBack.acceptException(exception)
        } catch (exception: ExecutionException) {
            callBack.acceptException(exception)
        }
    }

    override fun singlePieceAsync(supplier: PieceFilter, documentCallBack: PieceAsyncCallBack) {
        try {
            CompletableFuture.runAsync({
                val element = elements
                    .filterIsInstance<Piece>()
                    .find { supplier.matches(it) }

                if (element == null) {
                    documentCallBack.acceptNotFound()
                } else {
                    documentCallBack.acceptFound(element)
                }
            }, AbstractDatabase.EXECUTOR).get()
        } catch (exception: InterruptedException) {
            documentCallBack.acceptException(exception)
        } catch (exception: ExecutionException) {
            documentCallBack.acceptException(exception)
        }
    }

    override fun multiPiece(filter: PieceFilter, cursorSupplier: PieceCursorSupplier): Cursor<Piece> {
        val matchedElements = elements
            .filterIsInstance<Piece>()
            .filter { filter.matches(it) }
        return cursorSupplier.create(matchedElements)
    }

    override fun singlePiece(supplier: PieceFilter): Piece? {
        return elements.filterIsInstance<Piece>().find { supplier.matches(it) }
    }

    override fun replaceOnePiece(filter: PieceFilter, element: Piece) {
        elements.removeIf { filter.matches(it as Piece) }
        elements.add(element)
    }

    override fun replaceOnePieceAsync(filter: PieceFilter, element: Piece, listener: DatabaseListener) {
        try {
            CompletableFuture.runAsync({
                if (elements.removeIf { filter.matches(it as Piece) }) {
                    elements.add(element)
                    listener.success()
                } else {
                    listener.accept(DatabaseListener.State.FAILED, null)
                }
            }, AbstractDatabase.EXECUTOR).get()
        } catch (exception: InterruptedException) {
            listener.exception(exception)
        } catch (exception: ExecutionException) {
            listener.exception(exception)
        }
    }

    override fun updateOnePiece(filter: PieceFilter, other: Piece) {
        val current = this.elements.stream().filter { filter.matches(it as Piece) }.findFirst()

        if (current.isEmpty) {
            this.insertOnePiece(other)
            return
        }

        val piece: Piece = current.get() as Piece;

        for (containing in other.elements) {
            val key = containing.key;

            if (piece.contains(key)) {
                piece.remove(key)
                piece.set(containing.key, containing.value)
                continue
            }

            piece.set(containing.key, containing.value)
        }

        elements.removeIf { filter.matches(it as Piece) }
        elements.add(piece)
     }

    override fun updateOnePieceAsync(filter: PieceFilter, element: Piece, listener: DatabaseListener) {
        try {
            CompletableFuture.runAsync({
                updateOnePiece(filter, element)
            }, AbstractDatabase.EXECUTOR).get()
        } catch (exception: InterruptedException) {
            listener.exception(exception)
        } catch (exception: ExecutionException) {
            listener.exception(exception)
        }
    }

    override fun deleteOnePiece(element: Piece) {
        elements.remove(element)
    }

    override fun deleteManyPiece(vararg element: Piece) {
        element.forEach { elements.remove(it) }
    }

    override fun deleteOnePieceAsync(element: Piece, listener: DatabaseListener) {
        try {
            CompletableFuture.runAsync({
                if (elements.remove(element)) {
                    listener.success()
                } else {
                    listener.accept(DatabaseListener.State.FAILED, null)
                }
            }, AbstractDatabase.EXECUTOR).get()
        } catch (exception: InterruptedException) {
            listener.exception(exception)
        } catch (exception: ExecutionException) {
            listener.exception(exception)
        }
    }

    override fun deleteManyPieceAsync(listener: DatabaseListener, vararg element: Piece) {
        try {
            CompletableFuture.runAsync({
                element.forEach { elements.remove(it) }
                listener.success()
            }, AbstractDatabase.EXECUTOR).get()
        } catch (exception: InterruptedException) {
            listener.exception(exception)
        } catch (exception: ExecutionException) {
            listener.exception(exception)
        }
    }

    override fun insertOnePiece(element: Piece) {
        elements.add(element)
    }

    override fun insertOnePieceAsync(element: Piece, listener: DatabaseListener) {
        try {
            CompletableFuture.runAsync({
                elements.add(element)
                listener.success()
            }, AbstractDatabase.EXECUTOR).get()
        } catch (exception: InterruptedException) {
            listener.exception(exception)
        } catch (exception: ExecutionException) {
            listener.exception(exception)
        }
    }

    override fun defineOne(): DatabaseRecord {
        throw UnsupportedOperationException("Not implemented!")
    }

    override fun defineOnePeace(): Piece {
        val index = elements.size + 1
        val id = nextFreeId()

        val data = mutableMapOf<String, Any>(
            "_created_at" to System.currentTimeMillis(),
            "_auto_delete" to -1
        )

        return Piece(index, id, data)
    }

    private fun nextFreeId(): Long {
        val current = ThreadLocalRandom.current().nextLong()
        return if (elements.any { it.id == current }) nextFreeId() else current
    }

    override fun collect(): List<Piece> {
        return elements.filterIsInstance<Piece>()
    }
}
