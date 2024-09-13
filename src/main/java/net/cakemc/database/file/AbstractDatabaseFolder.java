package net.cakemc.database.file;

import net.cakemc.database.encryption.AbstractKey;

import java.io.IOException;
import java.util.List;

/**
 * The type Abstract database folder.
 */
public abstract class AbstractDatabaseFolder {
    /**
     * Exists boolean.
     *
     * @return the boolean
     */
    public abstract boolean exists();

    /**
     * Create boolean.
     *
     * @return the boolean
     * @throws Throwable the throwable
     */
    public abstract boolean create() throws Throwable;

    /**
     * List files list.
     *
     * @return the list
     * @throws Throwable the throwable
     */
    public abstract List<NioFile> listFiles() throws Throwable;

    /**
     * Gets file.
     *
     * @param name the name
     * @return the file
     * @throws Throwable the throwable
     */
    public abstract AbstractDatabaseFile getFile(String name) throws Throwable;

    /**
     * Gets encrypted file.
     *
     * @param key  the key
     * @param name the name
     * @return the encrypted file
     * @throws Throwable the throwable
     */
    public abstract AbstractDatabaseFile getEncryptedFile(AbstractKey key, String name) throws Throwable;
}
