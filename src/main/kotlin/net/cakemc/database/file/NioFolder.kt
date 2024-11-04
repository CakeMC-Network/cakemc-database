package net.cakemc.database.file

import net.cakemc.database.encryption.AbstractKey
import java.nio.file.Files
import java.nio.file.LinkOption
import java.nio.file.Path

/**
 * The type Nio folder.
 */
class NioFolder
/**
 * Instantiates a new Nio folder.
 *
 * @param folder the folder
 */(private val folder: Path) : AbstractDatabaseFolder() {
    override fun exists(): Boolean {
        return Files.exists(folder, LinkOption.NOFOLLOW_LINKS)
    }

    @Throws(Throwable::class)
    override fun create(): Boolean {
        if (exists()) return true

        return Files.exists(Files.createDirectory(folder), LinkOption.NOFOLLOW_LINKS)
    }

    @Throws(Throwable::class)
    override fun listFiles(): List<NioFile> {
        val nioFiles: MutableList<NioFile> = ArrayList()
        Files.newDirectoryStream(folder).use { pathIterator ->
            for (path in pathIterator) {
                nioFiles.add(NioFile(path, true))
            }
        }
        return nioFiles
    }

    @Throws(Throwable::class)
    override fun getFile(name: String): AbstractDatabaseFile {
        val path = Path.of(folder.toAbsolutePath().toString(), name)
        val file: AbstractDatabaseFile = NioFile(path, true)

        if (file.exists()) {
            file.read()
            return file
        }

        // write empty
        file.write()
        return file
    }

    @Throws(Throwable::class)
    override fun getEncryptedFile(key: AbstractKey, name: String): AbstractDatabaseFile {
        val path = Path.of(folder.toAbsolutePath().toString(), name)
        val file: AbstractDatabaseFile = KeyedNioFile(path, key, true)

        if (file.exists()) {
            file.read()
            return file
        }

        // write empty
        file.write()
        return file
    }
}
