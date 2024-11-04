package net.cakemc.database.file

import java.io.IOException

/**
 * The type Abstract database file.
 */
abstract class AbstractDatabaseFile {
    /**
     * Read.
     *
     * @throws IOException the io exception
     */
    @Throws(IOException::class)
    abstract fun read()

    /**
     * Write.
     *
     * @throws Throwable the throwable
     */
    @Throws(Throwable::class)
    abstract fun write()

    /**
     * Delete.
     *
     * @throws Throwable the throwable
     */
    @Throws(Throwable::class)
    abstract fun delete()

    /**
     * Exists boolean.
     *
     * @return the boolean
     */
    abstract fun exists(): Boolean

    /**
     * Gets memory file.
     *
     * @return the memory file
     */
    abstract val memoryFile: MemoryFile?
}
