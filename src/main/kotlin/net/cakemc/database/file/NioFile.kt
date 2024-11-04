package net.cakemc.database.file

import java.io.IOException
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.StandardOpenOption

/**
 * The type Nio file.
 */
open class NioFile
/**
 * Instantiates a new Nio file.
 *
 * @param path       the path
 * @param compressed the compressed
 */(
    /**
     * The Path.
     */
    val path: Path,
    /**
     * The Compressed.
     */
    protected open val compressed: Boolean
) : AbstractDatabaseFile() {
    /**
     * Gets path.
     *
     * @return the path
     */

    /**
     * The Default memory file.
     */
    override var memoryFile: MemoryFile? = null
        protected set

    @Throws(IOException::class)
    override fun read() {
        this.memoryFile = DefaultMemoryFile(path, Files.readAllBytes(path), compressed)
    }

    @Throws(Throwable::class)
    override fun write() {
        val data = (if (this.memoryFile == null) DefaultMemoryFile(
            path, ByteArray(0),
            compressed
        ).also { memoryFile = it } else memoryFile)?.data

        if (!exists()) {
            if (data != null) {
                Files.write(path, data, StandardOpenOption.CREATE_NEW)
            }
            return
        }

        if (data != null) {
            Files.write(path, data, StandardOpenOption.WRITE)
        }
    }

    @Throws(Throwable::class)
    override fun delete() {
        Files.delete(path)
    }

    override fun exists(): Boolean {
        return Files.exists(path, LinkOption.NOFOLLOW_LINKS)
    }
}
