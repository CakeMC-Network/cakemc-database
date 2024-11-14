package net.cakemc.database

import net.cakemc.database.api.DatabaseRecord
import net.cakemc.database.collection.Collection
import net.cakemc.database.compression.FileCompression
import net.cakemc.database.encryption.AbstractKey
import net.cakemc.database.encryption.CipherEncryption
import net.cakemc.database.encryption.FileEncryption
import net.cakemc.database.units.KeySupplier
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * The type Abstract database.
 */
abstract class AbstractDatabase {
    /**
     * Gets collection.
     *
     * @param name the name
     * @return the collection
     */
    abstract fun getCollection(name: String): Collection<DatabaseRecord>

    /**
     * Save.
     */
    abstract fun save()

    /**
     * Load.
     */
    abstract fun load()

    companion object {
        /**
         * The constant VISUAL_EXECUTOR.
         */
        @JvmField
        val EXECUTOR: ExecutorService = Executors.newCachedThreadPool()
        val VISUAL_EXECUTOR: ExecutorService = Executors.newVirtualThreadPerTaskExecutor()

        /**
         * The constant DEFAULT_COMPRESSION.
         */
        @JvmField
        val DEFAULT_COMPRESSION: FileCompression = object : FileCompression() {
            override fun compress(source: ByteArray): ByteArray {
                return source
            }

            override fun decompress(source: ByteArray): ByteArray {
                return source
            }
        }

        /**
         * The constant DEFAULT_ENCRYPTION.
         */
        @JvmField
        val DEFAULT_ENCRYPTION: KeySupplier<FileEncryption, AbstractKey> =
            KeySupplier { keyFile: AbstractKey -> CipherEncryption(keyFile) }
    }
}
