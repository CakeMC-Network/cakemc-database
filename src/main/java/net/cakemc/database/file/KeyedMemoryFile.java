package net.cakemc.database.file;

import net.cakemc.database.AbstractDatabase;
import net.cakemc.database.encryption.AbstractKey;
import net.cakemc.database.encryption.FileEncryption;

import java.nio.file.Path;

/**
 * The type Keyed memory file.
 */
public class KeyedMemoryFile extends DefaultMemoryFile {

    private final FileEncryption encryption;

    /**
     * Instantiates a new Keyed memory file.
     *
     * @param abstractKey the abstract key
     * @param path        the path
     * @param data        the data
     * @param compress    the compress
     */
    public KeyedMemoryFile(AbstractKey abstractKey, Path path, byte[] data, boolean compress) {
        super(path, data, compress);

        encryption = AbstractDatabase.DEFAULT_ENCRYPTION.accept(abstractKey);
        this.data = encryption.decrypt(data);
    }

    @Override
    public byte[] getData() {
        return encryption.encrypt(super.getData());
    }

    /**
     * Gets encryption.
     *
     * @return the encryption
     */
    public FileEncryption getEncryption() {
        return encryption;
    }
}
