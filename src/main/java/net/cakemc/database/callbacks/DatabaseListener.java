package net.cakemc.database.callbacks;

/**
 * The interface Database listener.
 */
public interface DatabaseListener {

    /**
     * The enum State.
     */
    public enum State {
        /**
         * Success state.
         */
        SUCCESS,
        /**
         * Failed state.
         */
        FAILED,
        ;
    }

    /**
     * Accept.
     *
     * @param state     the state
     * @param exception the exception
     */
    public void accept(State state, Exception exception);

    /**
     * Exception.
     *
     * @param exception the exception
     */
    public default void exception(Exception exception) {
        this.accept(State.FAILED, exception);
    }

    /**
     * Success.
     */
    public default void success() {
        this.accept(State.SUCCESS, null);
    }

}
