package net.cakemc.database.collection;

import net.cakemc.database.AbstractDatabase;
import net.cakemc.database.api.DatabaseRecord;
import net.cakemc.database.api.Piece;
import net.cakemc.database.cursor.Cursor;
import net.cakemc.database.cursor.PieceCursorSupplier;
import net.cakemc.database.callbacks.DatabaseListener;
import net.cakemc.database.callbacks.PieceAsyncCallBack;
import net.cakemc.database.callbacks.PieceAsyncMultiCallBack;
import net.cakemc.database.filter.PieceFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The type Piece collection.
 */
public class PieceCollection extends AbstractCollection<DatabaseRecord> {


    /**
     * Instantiates a new Piece collection.
     *
     * @param elements the elements
     * @param id       the id
     * @param name     the name
     */
    public PieceCollection(List<DatabaseRecord> elements, long id, String name) {
        super(elements, id, name);
    }


    @Override
    public void multiPieceAsync(PieceFilter filter, PieceCursorSupplier cursorSupplier, PieceAsyncMultiCallBack callBack) {
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

                callBack.acceptFound(cursorSupplier.create(this.elements.stream()
                        .filter(databaseRecord -> filter.matches((Piece) databaseRecord))
                        .map(databaseRecord -> (Piece) databaseRecord)
                        .toList()));

            }, AbstractDatabase.VISUAL_EXECUTOR);
            completableFuture.get();
        } catch (InterruptedException | ExecutionException exception) {
            callBack.acceptException(exception);
        }
    }

    @Override
    public void singlePieceAsync(PieceFilter filter, PieceAsyncCallBack documentCallBack) {
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

                Piece element = (Piece) elements.stream()
                        .filter(databaseRecord -> filter.matches((Piece) databaseRecord))
                        .findFirst()
                        .orElse(null);

                if (element == null) {
                    documentCallBack.acceptNotFound();
                    return;
                }

                documentCallBack.acceptFound(element);

            }, AbstractDatabase.VISUAL_EXECUTOR);
            completableFuture.get();
        } catch (InterruptedException | ExecutionException exception) {
            documentCallBack.acceptException(exception);
        }
    }

    @Override
    public Cursor<Piece> multiPiece(PieceFilter filter, PieceCursorSupplier cursorSupplier) {
        return cursorSupplier.create(elements.stream()
                .filter(databaseRecord -> filter.matches((Piece) databaseRecord))
                .map(databaseRecord -> (Piece) databaseRecord).toList());
    }

    @Override
    public Piece singlePiece(PieceFilter filter) {
        return (Piece) elements.stream().filter(databaseRecord -> filter.matches((Piece) databaseRecord)).findFirst().orElse(null);
    }

    @Override
    public void replaceOnePiece(PieceFilter filter, Piece element) {
        elements.removeIf(databaseRecord -> filter.matches((Piece) databaseRecord));
        elements.add(element);
    }

    @Override
    public void replaceOnePieceAsync(PieceFilter filter, Piece element, DatabaseListener listener) {
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

                if (elements.removeIf(databaseRecord -> filter.matches((Piece) databaseRecord))) {
                    elements.add(element);
                    listener.success();
                } else
                    listener.accept(DatabaseListener.State.FAILED, null);

            }, AbstractDatabase.VISUAL_EXECUTOR);
            completableFuture.get();
        } catch (InterruptedException | ExecutionException exception) {
            listener.exception(exception);
        }
    }

    @Override
    public void updateOnePiece(PieceFilter filter, Piece element) {
        // todo update fields in filter
    }

    @Override
    public void updateOnePieceAsync(PieceFilter filter, Piece element, DatabaseListener listener) {
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

                // todo update fields in filter

            }, AbstractDatabase.VISUAL_EXECUTOR);
            completableFuture.get();
        } catch (InterruptedException | ExecutionException exception) {
            listener.exception(exception);
        }
    }

    @Override
    public void deleteOnePiece(Piece element) {
        this.elements.remove(element);
    }

    @Override
    public void deleteManyPiece(Piece... element) {
        for (Piece piece : element) {
            this.elements.remove(piece);
        }

    }

    @Override
    public void deleteOnePieceAsync(Piece element, DatabaseListener listener) {
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

                if (elements.remove(element))
                    listener.success();
                else
                    listener.accept(DatabaseListener.State.FAILED, null);

            }, AbstractDatabase.VISUAL_EXECUTOR);
            completableFuture.get();
        } catch (InterruptedException | ExecutionException exception) {
            listener.exception(exception);
        }
    }

    @Override
    public void deleteManyPieceAsync(DatabaseListener listener, Piece... element) {
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

                for (DatabaseRecord current : element) {
                    elements.remove(current);
                }

                listener.success();

            }, AbstractDatabase.VISUAL_EXECUTOR);
            completableFuture.get();
        } catch (InterruptedException | ExecutionException exception) {
            listener.exception(exception);
        }
    }

    @Override
    public void insertOnePiece(Piece element) {
        this.elements.add(element);
    }

    @Override
    public void insertOnePieceAsync(Piece element, DatabaseListener listener) {
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

                this.elements.add(element);
                listener.success();

            }, AbstractDatabase.VISUAL_EXECUTOR);
            completableFuture.get();
        } catch (InterruptedException | ExecutionException exception) {
            listener.exception(exception);
        }
    }

    private <T> Piece pieceCast(T element) {
        return (Piece) element;
    }

    @Override
    public DatabaseRecord defineOne() {
        throw new UnsupportedOperationException("no implemented!");
    }

    @Override
    public Piece defineOnePeace() {
        int index = this.elements.size() + 1;
        long id = nextFreeId();

        return new Piece(index, id, new HashMap<>());
    }

    private long nextFreeId() {
        long current = ThreadLocalRandom.current().nextLong();

        if (this.elements.stream()
                .anyMatch(databaseRecord -> databaseRecord.getId() == current))

            return nextFreeId();
        return current;
    }

    @Override
    public List<Piece> collect() {
        List<Piece> pieces = new ArrayList<>();
        for (DatabaseRecord element : this.elements) {
            if (element instanceof Piece piece)
                pieces.add(piece);
        }
        return pieces;
    }
}
