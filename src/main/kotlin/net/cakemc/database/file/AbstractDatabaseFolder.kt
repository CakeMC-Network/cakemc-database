package net.cakemc.database.file

import net.cakemc.database.encryption.AbstractKey

/**
 * The type Abstract database folder.
 */
abstract class AbstractDatabaseFolder {
    /**
     * Exists boolean.
     *
     * @return the boolean
     */
    abstract fun exists(): Boolean

    /**
     * Create boolean.
     *
     * @return the boolean
     * @throws Throwable the throwable
     */
    @Throws(Throwable::class)
    abstract fun create(): Boolean

    /**
     * List files list.
     *
     * @return the list
     * @throws Throwable the throwable
     */
    @Throws(Throwable::class)
    abstract fun listFiles(): List<NioFile>

    /**
     * Gets file.
     *
     * @param name the name
     * @return the file
     * @throws Throwable the throwable
     */
    @Throws(Throwable::class)
    abstract fun getFile(name: String): AbstractDatabaseFile

    /**
     * Gets encrypted file.
     *
     * @param key  the key
     * @param name the name
     * @return the encrypted file
     * @throws Throwable the throwable
     */
    @Throws(Throwable::class)
    abstract fun getEncryptedFile(key: AbstractKey, name: String): AbstractDatabaseFile
}
