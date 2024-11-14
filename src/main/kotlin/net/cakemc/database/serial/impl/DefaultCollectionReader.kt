package net.cakemc.database.serial.impl

import net.cakemc.database.api.DatabaseRecord
import net.cakemc.database.api.Piece
import net.cakemc.database.collection.Collection
import net.cakemc.database.collection.PieceCollection
import net.cakemc.database.serial.AbstractRead
import java.io.*
import java.util.*

/**
 * Default collection reader.
 */
class DefaultCollectionReader : AbstractRead() {

    override fun read(data: ByteArray?): Collection<DatabaseRecord> {
        val byteStream = ByteArrayInputStream(data)
        val dataStream = DataInputStream(byteStream)

        // Read collection details
        val id = dataStream.readLong()
        val name = dataStream.readUTF()

        val numDocuments = dataStream.readInt()
        val documents: MutableList<DatabaseRecord> = ArrayList<DatabaseRecord>(numDocuments)

        // Deserialize each document
        for (i in 0 until numDocuments) {
            val docSize = dataStream.readInt()
            val docBytes = ByteArray(docSize)
            dataStream.readFully(docBytes)
            val doc = readElement(docBytes)
            documents.add(doc)
        }

        return PieceCollection(documents, id, name)
    }

    /**
     * Read an element and return a Piece.
     *
     * @param data the data as a byte array
     * @return the deserialized Piece
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun readElement(data: ByteArray): Piece {
        val byteStream = ByteArrayInputStream(data)
        val dataStream = DataInputStream(byteStream)

        val documentId = dataStream.readLong()
        val documentIndex = dataStream.readInt()

        val size = dataStream.readInt()
        val values = Hashtable<String, Any>(size)

        for (i in 0 until size) {
            val key = dataStream.readUTF()

            val typeId = dataStream.readByte()
            val value: Any? = when (typeId.toInt()) {
                1 -> dataStream.readUTF()
                2 -> dataStream.readInt()
                3 -> dataStream.readLong()
                4 -> dataStream.readDouble()
                5 -> dataStream.readBoolean()
                6 -> UUID.fromString(dataStream.readUTF())
                7 -> {
                    val objectSize = dataStream.readInt()
                    val objectBytes = ByteArray(objectSize)
                    dataStream.readFully(objectBytes)
                    val objectStream = ByteArrayInputStream(objectBytes)
                    ObjectInputStream(objectStream).use { it.readObject() }
                }
                else -> throw IOException("Unknown type identifier: $typeId")
            }
            values[key] = value
        }

        return Piece(documentIndex, documentId, values)
    }
}
