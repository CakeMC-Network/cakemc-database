package net.cakemc.database.units;

/**
 * The interface Key supplier.
 *
 * @param <T> the type parameter
 * @param <B> the type parameter
 */
public interface KeySupplier<T, B> {

    /**
     * Accept t.
     *
     * @param key the key
     * @return the t
     */
    public T accept(B key);

}

