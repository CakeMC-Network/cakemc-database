package net.cakemc.database.file;

import net.cakemc.database.encryption.AbstractKey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * The type Keyed nio file.
 */
public class KeyedNioFile extends NioFile {

    private final AbstractKey key;
    private final boolean compressed;

    /**
     * Instantiates a new Keyed nio file.
     *
     * @param path       the path
     * @param key        the key
     * @param compressed the compressed
     */
    public KeyedNioFile(Path path, AbstractKey key, boolean compressed) {
        super(path, compressed);
        this.key = key;
        this.compressed = compressed;
    }

    @Override
    public void read() throws IOException {
        this.defaultMemoryFile = new KeyedMemoryFile(key, path, Files.readAllBytes(path), compressed);
    }

    @Override
    public void write() throws Throwable {
        if (exists()) {
            Files.write(path, defaultMemoryFile.getData(), StandardOpenOption.CREATE_NEW);
            return;
        }

        Files.write(Files.createFile(path), (
                this.defaultMemoryFile == null ?
                        defaultMemoryFile = new KeyedMemoryFile(key, path, new byte[0], compressed) :
                        defaultMemoryFile)
                .getData(), StandardOpenOption.WRITE);
    }


}
