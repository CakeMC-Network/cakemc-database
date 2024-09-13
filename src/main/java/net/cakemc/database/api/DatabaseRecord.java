package net.cakemc.database.api;

/**
 * The type Database record.
 */
public abstract class DatabaseRecord {

    /**
     * The Index.
     */
    protected final int index;
    /**
     * The Id.
     */
    protected final long id;

    /**
     * Instantiates a new Database record.
     *
     * @param index the index
     * @param id    the id
     */
    protected DatabaseRecord(int index, long id) {
        this.index = index;
        this.id = id;
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public abstract int size();
}
