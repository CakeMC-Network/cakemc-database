package net.cakemc.database.file;

import java.nio.file.Path;

/**
 * The type Memory file.
 */
public abstract class MemoryFile {
    /**
     * Gets path.
     *
     * @return the path
     */
    public abstract Path getPath();

    /**
     * Get data byte [ ].
     *
     * @return the byte [ ]
     */
    public abstract byte[] getData();

    /**
     * Sets data.
     *
     * @param data the data
     */
    public abstract void setData(byte[] data);
}
