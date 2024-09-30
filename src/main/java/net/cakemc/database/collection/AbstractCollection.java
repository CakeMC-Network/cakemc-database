package net.cakemc.database.collection;

import net.cakemc.database.AbstractDatabase;
import net.cakemc.database.cursor.Cursor;
import net.cakemc.database.cursor.CursorSupplier;
import net.cakemc.database.callbacks.AsyncCallBack;
import net.cakemc.database.callbacks.AsyncMultiCallBack;
import net.cakemc.database.callbacks.DatabaseListener;
import net.cakemc.database.filter.Filter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * The type Abstract collection.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractCollection<T> implements Collection<T> {

    /**
     * The Elements.
     */
    final List<T> elements;
    private final long id;
    private final String name;

    /**
     * Instantiates a new Abstract collection.
     *
     * @param elements the elements
     * @param id       the id
     * @param name     the name
     */
    public AbstractCollection(List<T> elements, long id, String name) {
        this.elements = elements;
        this.id = id;
        this.name = name;
    }

    @Override
    public void multiAsync(Filter<T> filter, CursorSupplier<T, Cursor<T>> cursorSupplier, AsyncMultiCallBack<T> callBack) {
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

                callBack.acceptFound(cursorSupplier.create(this.elements.stream()
                        .filter(filter::matches)
                        .toList()));

            }, AbstractDatabase.VISUAL_EXECUTOR);
            completableFuture.get();
        } catch (InterruptedException | ExecutionException exception) {
            callBack.acceptException(exception);
        }
    }

    @Override
    public void singleAsync(Filter<T> supplier, AsyncCallBack<T> documentCallBack) {
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

                T element = elements.stream().filter(supplier::matches)
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
    public Cursor<T> multi(Filter<T> filter, CursorSupplier<T, Cursor<T>> cursorSupplier) {
        return cursorSupplier.create(elements.stream().filter(filter::matches).toList());
    }

    @Override
    public T single(Filter<T> supplier) {
        return elements.stream().filter(supplier::matches).findFirst().orElse(null);
    }

    @Override
    public void replaceOne(Filter<T> filter, T element) {
        elements.removeIf(filter::matches);
        elements.add(element);
    }

    @Override
    public void replaceOneAsync(Filter<T> filter, T element, DatabaseListener listener) {
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

                if (elements.removeIf(filter::matches)) {
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
    public void updateOne(Filter<T> filter, T element) {
        // todo update fields in filter
    }

    @Override
    public void updateOneAsync(Filter<T> filter, T element, DatabaseListener listener) {
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
    public void deleteOne(T element) {
        this.elements.remove(element);
    }

    @Override
    public void deleteMany(T[] element) {
        for (T current : element) {
            this.elements.remove(current);
        }
    }

    @Override
    public void deleteOneAsync(T element, DatabaseListener listener) {
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
    public void deleteManyAsync(T[] element, DatabaseListener listener) {
        try {
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {

                for (T current : elements) {
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
    public void insertOne(T element) {
        this.elements.add(element);
    }

    @Override
    public void insertOneAsync(T element, DatabaseListener listener) {
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

    /**
     * Gets id.
     *
     * @return the id
     */
    @Override
    public long getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AbstractCollection{" +
                "elements=" + elements +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
