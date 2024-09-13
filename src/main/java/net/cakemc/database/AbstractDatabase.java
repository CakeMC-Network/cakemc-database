package net.cakemc.database;

import net.cakemc.database.api.DatabaseRecord;
import net.cakemc.database.collection.Collection;
import net.cakemc.database.compression.FileCompression;
import net.cakemc.database.encryption.AbstractKey;
import net.cakemc.database.encryption.CipherEncryption;
import net.cakemc.database.encryption.FileEncryption;
import net.cakemc.database.units.KeySupplier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The type Abstract database.
 */
public abstract class AbstractDatabase {

    /**
     * The constant VISUAL_EXECUTOR.
     */
    public static final ExecutorService VISUAL_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();

    /**
     * The constant DEFAULT_COMPRESSION.
     */
    public static final FileCompression DEFAULT_COMPRESSION = new FileCompression() {
        @Override
        public byte[] compress(byte[] source) {
            return source;
        }

        @Override
        public byte[] decompress(byte[] source) {
            return source;
        }
    };

    /**
     * The constant DEFAULT_ENCRYPTION.
     */
    public static final KeySupplier<FileEncryption, AbstractKey> DEFAULT_ENCRYPTION = CipherEncryption::new;

    /**
     * Gets collection.
     *
     * @param name the name
     * @return the collection
     */
    public abstract Collection<DatabaseRecord> getCollection(String name);

    /**
     * Save.
     */
    public abstract void save();

    /**
     * Load.
     */
    public abstract void load();

}
