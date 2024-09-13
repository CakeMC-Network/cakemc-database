package net.cakemc.database.serial;

import net.cakemc.database.api.DatabaseRecord;
import net.cakemc.database.collection.Collection;

import java.io.IOException;

/**
 * The type Abstract read.
 */
public abstract class AbstractRead {

    /**
     * Read collection.
     *
     * @param data the data
     * @return the collection
     * @throws IOException the io exception
     */
    public abstract Collection<DatabaseRecord> read(byte[] data) throws IOException;

}
