package net.cakemc.database.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * The type Nio file.
 */
public class NioFile extends AbstractDatabaseFile {

    /**
     * The Path.
     */
    protected final Path path;
    /**
     * The Default memory file.
     */
    protected MemoryFile defaultMemoryFile;
    /**
     * The Compressed.
     */
    protected final boolean compressed;

    /**
     * Instantiates a new Nio file.
     *
     * @param path       the path
     * @param compressed the compressed
     */
    public NioFile(Path path, boolean compressed) {
        this.path = path;
        this.compressed = compressed;
    }

    @Override
    public void read() throws IOException {
        this.defaultMemoryFile = new DefaultMemoryFile(path, Files.readAllBytes(path), compressed);
    }

    @Override
    public void write() throws Throwable {
         byte[] data = (this.defaultMemoryFile == null ?
                 defaultMemoryFile = new DefaultMemoryFile(path, new byte[0], compressed) :
                 defaultMemoryFile)
                 .getData();

        if (!exists()) {
            Files.write(path, data, StandardOpenOption.CREATE_NEW);
            return;
        }

        Files.write(path, data, StandardOpenOption.WRITE);
    }

    @Override
    public void delete() throws Throwable {
        Files.delete(path);
    }

    @Override
    public boolean exists() {
        return Files.exists(path, LinkOption.NOFOLLOW_LINKS);
    }

    @Override
    public MemoryFile getMemoryFile() {
        return defaultMemoryFile;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public Path getPath() {
        return path;
    }
}
