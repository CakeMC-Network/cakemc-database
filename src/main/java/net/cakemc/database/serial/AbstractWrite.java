package net.cakemc.database.serial;

import net.cakemc.database.api.DatabaseRecord;
import net.cakemc.database.api.Piece;
import net.cakemc.database.collection.Collection;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * The type Abstract write.
 */
public abstract class AbstractWrite {

    /**
     * Write collection byte [ ].
     *
     * @param collection the collection
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    public abstract byte[] writeCollection(Collection<DatabaseRecord> collection, Consumer<Piece> consumer) throws IOException;

}
