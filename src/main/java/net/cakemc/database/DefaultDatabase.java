package net.cakemc.database;

import net.cakemc.database.api.DatabaseRecord;
import net.cakemc.database.collection.Collection;
import net.cakemc.database.collection.PieceCollection;
import net.cakemc.database.file.AbstractDatabaseFolder;
import net.cakemc.database.file.NioFile;
import net.cakemc.database.file.NioFolder;
import net.cakemc.database.serial.AbstractRead;
import net.cakemc.database.serial.AbstractWrite;
import net.cakemc.database.serial.impl.DefaultCollectionReader;
import net.cakemc.database.serial.impl.DefaultCollectionWriter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The type Default database.
 */
public class DefaultDatabase extends AbstractDatabase {

    private static final String FILE_FORMAT = "%s.db_bin";
    private static final int DAY_IN_MILLIS = 86400000;

    private final Map<String, Collection<DatabaseRecord>> collectionMap;

    private final AbstractDatabaseFolder databaseFolder;

    private final AbstractWrite collectionWriter;
    private final AbstractRead collectionReader;

    /**
     * Instantiates a new Default database.
     *
     * @param folder the folder
     */
    public DefaultDatabase(Path folder) {

        this.collectionReader = new DefaultCollectionReader();
        this.collectionWriter = new DefaultCollectionWriter();

        collectionMap = new ConcurrentHashMap<>();
        this.databaseFolder = new NioFolder(folder);

        try {
            this.databaseFolder.create();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<DatabaseRecord> getCollection(String name) {
        if (collectionMap.containsKey(name)) {
            return collectionMap.get(name);
        }
        Collection<DatabaseRecord> collection = new PieceCollection(new ArrayList<>(), nextFreeId(), name);
        this.collectionMap.put(collection.getName(), collection);
        return collection;
    }

    private long nextFreeId() {
        long current = ThreadLocalRandom.current().nextLong();

        if (this.collectionMap.entrySet().stream()
                .anyMatch(entry -> entry.getValue().getId() == current))
            return nextFreeId();

        return current;
    }


    @Override
    public void save() {
        for (Map.Entry<String, Collection<DatabaseRecord>> entry : collectionMap.entrySet()) {
            //AbstractDatabase.EXECUTOR.execute(() -> {
                try {
                    NioFile nioFile = (NioFile) databaseFolder.getFile(FILE_FORMAT.formatted(entry.getKey()));
                    nioFile.getMemoryFile().setData(this.collectionWriter.writeCollection(entry.getValue(), piece -> {
                        if (piece.contains("_auto_delete") && piece.contains("_created_at") && piece.getInt("_auto_delete") != -1) {
                            int days = piece.getInt("_auto_delete");
                            long createdAt = piece.getLong("_created_at");

                            long timeDif = System.currentTimeMillis() - createdAt;

                            if (timeDif >= ((long) DAY_IN_MILLIS * days))
                                entry.getValue().deleteOnePiece(piece);
                        }

                    }));
                    nioFile.write();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            //});

        }
    }

    @Override
    public void load() {
        try {
            for (NioFile file : databaseFolder.listFiles()) {
                file.read();

                byte[] data = file.getMemoryFile().getData();
                Collection<DatabaseRecord> collection = this.collectionReader.read(data);
                this.collectionMap.put(collection.getName(), collection);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
