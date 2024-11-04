package net.cakemc.database.file

import net.cakemc.database.AbstractDatabase
import java.nio.file.Path

/**
 * The type Default memory file.
 */
open class DefaultMemoryFile (
    /**
     * The Path.
     */
    override val path: Path, data: ByteArray, private val compress: Boolean
) : MemoryFile() {
    /**
     * The Data.
     */
    override var data: ByteArray = ByteArray(0)

    private val compression = AbstractDatabase.DEFAULT_COMPRESSION

    /**
     * Instantiates a new Default memory file.
     *
     * @param path     the path
     * @param data     the data
     * @param compress the compress
     */
    init {
        this.data = if (compress) compression.decompress(data) else data
    }

}
