package net.cakemc.database.cursor;

import java.util.List;

/**
 * The interface Cursor supplier.
 *
 * @param <E> the type parameter
 * @param <T> the type parameter
 */
public interface CursorSupplier<E, T extends Cursor<E>> {

    /**
     * Create t.
     *
     * @param elements the elements
     * @return the t
     */
    public T create(List<E> elements);

}
