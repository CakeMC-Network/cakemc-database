package net.cakemc.database.file;

import net.cakemc.database.encryption.AbstractKey;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Nio folder.
 */
public class NioFolder extends AbstractDatabaseFolder {

    private final Path folder;

    /**
     * Instantiates a new Nio folder.
     *
     * @param folder the folder
     */
    public NioFolder(Path folder) {
        this.folder = folder;
    }

    @Override
    public boolean exists() {
        return Files.exists(folder, LinkOption.NOFOLLOW_LINKS);
    }

    @Override
    public boolean create() throws Throwable {
        if (exists())
            return true;

        return Files.exists(Files.createDirectory(folder), LinkOption.NOFOLLOW_LINKS);
    }

    @Override
    public List<NioFile> listFiles() throws Throwable {
        List<NioFile> nioFiles = new ArrayList<>();
        try (DirectoryStream<Path> pathIterator = Files.newDirectoryStream(folder)) {
            for (Path path : pathIterator) {
                nioFiles.add(new NioFile(path, true));
            }
        }

        return nioFiles;
    }

    @Override
    public AbstractDatabaseFile getFile(String name) throws Throwable {
        Path path = Path.of(folder.toAbsolutePath().toString(), name);
        AbstractDatabaseFile file = new NioFile(path, true);

        if (file.exists()) {
            file.read();
            return file;
        }

        // write empty
        file.write();
        return file;
    }

    @Override
    public AbstractDatabaseFile getEncryptedFile(AbstractKey key, String name) throws Throwable {
        Path path = Path.of(folder.toAbsolutePath().toString(), name);
        AbstractDatabaseFile file = new KeyedNioFile(path, key, true);

        if (file.exists()) {
            file.read();
            return file;
        }

        // write empty
        file.write();
        return file;
    }
}
