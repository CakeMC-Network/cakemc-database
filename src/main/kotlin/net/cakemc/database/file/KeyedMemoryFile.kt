package net.cakemc.database.file

import net.cakemc.database.AbstractDatabase
import net.cakemc.database.encryption.AbstractKey
import net.cakemc.database.encryption.FileEncryption
import java.nio.file.Path

/**
 * The type Keyed memory file.
 */
class KeyedMemoryFile(abstractKey: AbstractKey?, path: Path, data: ByteArray, compress: Boolean) :
    DefaultMemoryFile(path, data, compress) {
    /**
     * Gets encryption.
     *
     * @return the encryption
     */
    val encryption: FileEncryption = AbstractDatabase.DEFAULT_ENCRYPTION.accept(abstractKey!!)

    /**
     * Instantiates a new Keyed memory file.
     *
     * @param abstractKey the abstract key
     * @param path        the path
     * @param data        the data
     * @param compress    the compress
     */
    init {
        this.data = encryption.decrypt(data)
    }

}
