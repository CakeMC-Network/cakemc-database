package net.cakemc.database.callbacks;

import net.cakemc.database.cursor.Cursor;

/**
 * The interface Async multi call back.
 *
 * @param <T> the type parameter
 */
public interface AsyncMultiCallBack<T> {

    /**
     * The enum State.
     */
    enum State {
        /**
         * Found state.
         */
        FOUND,
        /**
         * Not found state.
         */
        NOT_FOUND,
        /**
         * Error state.
         */
        ERROR,
        ;
    }

    /**
     * Accept.
     *
     * @param state     the state
     * @param documents the documents
     * @param exception the exception
     */
    public void accept(AsyncCallBack.State state, Cursor<T> documents, Exception exception);

    /**
     * Accept not found.
     */
    public default void acceptNotFound() {
        this.accept(AsyncCallBack.State.NOT_FOUND, null, new IllegalArgumentException("piece not found!"));
    }

    /**
     * Accept found.
     *
     * @param documents the documents
     */
    public default void acceptFound(Cursor<T> documents) {
        if (documents == null) {
            this.accept(AsyncCallBack.State.ERROR, documents, new IllegalArgumentException("piece is null but system tells its there!"));
            return;
        }

        this.accept(AsyncCallBack.State.FOUND, documents, null);
    }

    /**
     * Accept exception.
     *
     * @param exception the exception
     */
    public default void acceptException(Exception exception) {
        this.accept(AsyncCallBack.State.ERROR, null, exception);
    }

}
