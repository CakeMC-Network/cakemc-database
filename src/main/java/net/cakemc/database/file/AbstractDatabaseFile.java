package net.cakemc.database.file;

import java.io.IOException;

/**
 * The type Abstract database file.
 */
public abstract class AbstractDatabaseFile {
    /**
     * Read.
     *
     * @throws IOException the io exception
     */
    public abstract void read() throws IOException;

    /**
     * Write.
     *
     * @throws Throwable the throwable
     */
    public abstract void write() throws Throwable;

    /**
     * Delete.
     *
     * @throws Throwable the throwable
     */
    public abstract void delete() throws Throwable;

    /**
     * Exists boolean.
     *
     * @return the boolean
     */
    public abstract boolean exists();

    /**
     * Gets memory file.
     *
     * @return the memory file
     */
    public abstract MemoryFile getMemoryFile();
}
