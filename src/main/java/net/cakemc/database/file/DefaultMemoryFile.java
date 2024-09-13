package net.cakemc.database.file;

import net.cakemc.database.AbstractDatabase;
import net.cakemc.database.compression.FileCompression;

import java.nio.file.Path;

/**
 * The type Default memory file.
 */
public class DefaultMemoryFile extends MemoryFile {

    /**
     * The Path.
     */
    protected final Path path;
    /**
     * The Data.
     */
    protected byte[] data;

    private final FileCompression compression;
    private final boolean compress;

    /**
     * Instantiates a new Default memory file.
     *
     * @param path     the path
     * @param data     the data
     * @param compress the compress
     */
    public DefaultMemoryFile(Path path, byte[] data, boolean compress) {
        this.compression = AbstractDatabase.DEFAULT_COMPRESSION;
        this.compress = compress;

        this.path = path;
        this.data = compress ? compression.decompress(data) : data;
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public byte[] getData() {
        return compress ? compression.compress(data) : data;
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }
}
