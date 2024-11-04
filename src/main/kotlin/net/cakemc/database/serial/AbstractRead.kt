package net.cakemc.database.serial

import net.cakemc.database.api.DatabaseRecord
import net.cakemc.database.collection.Collection
import java.io.IOException

/**
 * The type Abstract read.
 */
abstract class AbstractRead {
    /**
     * Read collection.
     *
     * @param data the data
     * @return the collection
     * @throws IOException the io exception
     */
    @Throws(IOException::class)
    abstract fun read(data: ByteArray?): Collection<DatabaseRecord?>?
}
