package net.cakemc.database

import net.cakemc.database.api.DatabaseRecord
import net.cakemc.database.api.Piece
import net.cakemc.database.collection.Collection
import net.cakemc.database.collection.PieceCollection
import net.cakemc.database.file.AbstractDatabaseFolder
import net.cakemc.database.file.NioFile
import net.cakemc.database.file.NioFolder
import net.cakemc.database.serial.AbstractRead
import net.cakemc.database.serial.AbstractWrite
import net.cakemc.database.serial.impl.DefaultCollectionReader
import net.cakemc.database.serial.impl.DefaultCollectionWriter
import java.nio.file.Path
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ThreadLocalRandom

/**
 * The type Default database.
 */
class DefaultDatabase(folder: Path) : AbstractDatabase() {
    private val collectionMap: MutableMap<String?, Collection<DatabaseRecord?>> =
        ConcurrentHashMap()

    private val databaseFolder: AbstractDatabaseFolder = NioFolder(folder)

    private val collectionWriter: AbstractWrite = DefaultCollectionWriter()

    private val collectionReader: AbstractRead = DefaultCollectionReader()

    /**
     * Instantiates a new Default database.
     *
     * @param folder the folder
     */
    init {
        try {
            databaseFolder.create()
        } catch (e: Throwable) {
            throw RuntimeException(e)
        }
    }

    override fun getCollection(name: String?): Collection<DatabaseRecord?>? {
        if (collectionMap.containsKey(name)) {
            return collectionMap[name]
        }
        val collection: Collection<DatabaseRecord?> = PieceCollection(ArrayList(), nextFreeId(), name)
        collectionMap[collection.name] = collection
        return collection
    }

    private fun nextFreeId(): Long {
        val current = ThreadLocalRandom.current().nextLong()

        if (collectionMap.entries.stream()
                .anyMatch { entry: Map.Entry<String?, Collection<DatabaseRecord?>> -> entry.value.id === current }
        ) return nextFreeId()

        return current
    }


    override fun save() {
        for ((key, value) in collectionMap) {
            //AbstractDatabase.EXECUTOR.execute(() -> {
            try {
                val nioFile = databaseFolder.getFile("${key}.db_bin") as NioFile
                nioFile.memoryFile!!.data = collectionWriter.writeCollection(value) { piece: Piece ->
                    if (piece.contains("_auto_delete") && piece.contains("_created_at") && piece.getInt("_auto_delete") != -1) {
                        val days = piece.getInt("_auto_delete")
                        val createdAt = piece.getLong("_created_at")

                        val timeDif = System.currentTimeMillis() - createdAt

                        if (timeDif >= (DAY_IN_MILLIS.toLong() * days)) value.deleteOnePiece(
                            piece
                        )
                    }
                }
                nioFile.write()
            } catch (e: Throwable) {
                throw RuntimeException(e)
            }

            //});
        }
    }

    override fun load() {
        try {
            for (file in databaseFolder.listFiles()) {
                file.read()

                if (file.memoryFile == null)
                    continue

                val data = file.memoryFile!!.data
                val collection = collectionReader.read(data)
                collectionMap[collection!!.name] = collection
            }
        } catch (e: Throwable) {
            throw RuntimeException(e)
        }
    }

    companion object {
        private const val DAY_IN_MILLIS = 86400000
    }
}
