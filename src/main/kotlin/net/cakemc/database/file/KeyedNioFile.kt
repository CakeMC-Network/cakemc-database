package net.cakemc.database.file

import net.cakemc.database.encryption.AbstractKey
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

/**
 * The type Keyed nio file.
 */
class KeyedNioFile(path: Path, private val key: AbstractKey, compressed: Boolean) :
    NioFile(path, compressed) {

    override val compressed: Boolean

    /**
     * Instantiates a new Keyed nio file.
     *
     * @param path       the path
     * @param key        the key
     * @param compressed the compressed
     */
    init {
        this.compressed = compressed
    }

    @Throws(IOException::class)
    override fun read() {
        this.memoryFile = KeyedMemoryFile(key, path, Files.readAllBytes(path), compressed)
    }

    @Throws(Throwable::class)
    override fun write() {
        if (exists()) {
            Files.write(path, memoryFile!!.data, StandardOpenOption.CREATE_NEW)
            return
        }

        Files.write(
            Files.createFile(path), (if (this.memoryFile == null) KeyedMemoryFile(
                key, path, ByteArray(0), compressed
            ).also { memoryFile = it } else memoryFile)!!.data, StandardOpenOption.WRITE)
    }
}
