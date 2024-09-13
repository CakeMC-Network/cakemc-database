package net.cakemc.database.callbacks;

/**
 * The interface Async call back.
 *
 * @param <Document> the type parameter
 */
public interface AsyncCallBack<Document> {

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
     * @param document  the document
     * @param state     the state
     * @param exception the exception
     */
    public void accept(Document document, State state, Exception exception);

    /**
     * Accept not found.
     */
    public default void acceptNotFound() {
        this.accept(null, State.NOT_FOUND, new IllegalArgumentException("piece not found!"));
    }

    /**
     * Accept found.
     *
     * @param document the document
     */
    public default void acceptFound(Document document) {
        if (document == null) {
            this.accept(document, State.ERROR, new IllegalArgumentException("piece is null but system tells its there!"));
            return;
        }

        this.accept(document, State.FOUND, null);
    }

    /**
     * Accept exception.
     *
     * @param exception the exception
     */
    public default void acceptException(Exception exception) {
        this.accept(null, State.ERROR, exception);
    }

}
