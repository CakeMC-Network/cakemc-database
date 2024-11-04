package net.cakemc.database.file

import java.nio.file.Path

/**
 * The type Memory file.
 */
abstract class MemoryFile {
    /**
     * Gets path.
     *
     * @return the path
     */
    abstract val path: Path

    /**
     * Get data byte [ ].
     *
     * @return the byte [ ]
     */
    /**
     * Sets data.
     *
     * @param data the data
     */
    abstract var data: ByteArray
}
