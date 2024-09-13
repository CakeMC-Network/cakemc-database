package net.cakemc.database.cursor;

import net.cakemc.database.filter.Filter;

import java.util.Comparator;
import java.util.List;

/**
 * The type Cursor.
 *
 * @param <T> the type parameter
 */
public abstract class Cursor<T> {

    /**
     * Limit cursor.
     *
     * @param number the number
     * @return the cursor
     */
    public abstract Cursor<T> limit(int number);

    /**
     * Sort cursor.
     *
     * @param comparator the comparator
     * @return the cursor
     */
    public abstract Cursor<T> sort(Comparator<T> comparator);

    /**
     * Index int.
     *
     * @param element the element
     * @return the int
     */
    public abstract int index(T element);

    /**
     * Index int.
     *
     * @param filter the filter
     * @return the int
     */
    public abstract int index(Filter<T> filter);

    /**
     * Collect list.
     *
     * @return the list
     */
    public abstract List<T> collect();
}
