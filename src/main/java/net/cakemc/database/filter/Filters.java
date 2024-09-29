package net.cakemc.database.filter;

import net.cakemc.database.callbacks.ConditionalConsumer;

/**
 * The type Filters.
 */
public class Filters {

    /**
     * Eq piece piece filter.
     *
     * @param key   the key
     * @param value the value
     * @return the piece filter
     */
    public static PieceFilter eq(String key, Object value) {
        return piece -> {
            if (piece.getElements().get(key) == null)
                return false;

            return piece.getElements().get(key).equals(value);
        };
    }

    /**
     * Contains piece filter.
     *
     * @param key the key
     * @return the piece filter
     */
    public static PieceFilter contains(String key) {
        return piece -> piece.contains(key);
    }

    /**
     * Id piece filter.
     *
     * @param id the id
     * @return the piece filter
     */
    public static PieceFilter id(long id) {
        return piece -> piece.getId() == id;
    }

    /**
     * Index piece filter.
     *
     * @param index the index
     * @return the piece filter
     */
    public static PieceFilter index(int index) {
        return piece -> piece.getIndex() == index;
    }

    /**
     * Custom piece filter.
     *
     * @param <T>      the type parameter
     * @param key      the key
     * @param consumer the consumer
     * @return the piece filter
     */
    public static <T> PieceFilter custom(String key, ConditionalConsumer<T> consumer) {
        return piece -> {
            if (!piece.contains(key))
                return false;

            Object value = piece.getElements().get(key);
            return consumer.expect((T) value);
        };
    }

}
