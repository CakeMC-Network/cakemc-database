package net.cakemc.database.serial.impl

import net.cakemc.database.api.DatabaseRecord
import net.cakemc.database.api.Piece
import net.cakemc.database.collection.Collection
import net.cakemc.database.serial.AbstractWrite
import java.io.*
import java.util.*
import java.util.function.Consumer

/**
 * Default collection writer.
 */
class DefaultCollectionWriter : AbstractWrite() {

    override fun writeCollection(collection: Collection<DatabaseRecord>, consumer: Consumer<Piece>): ByteArray {
        val byteStream = ByteArrayOutputStream()
        val dataStream = DataOutputStream(byteStream)

        val documents = collection.collect()

        // Write collection details
        dataStream.writeLong(collection.id)
        dataStream.writeUTF(collection.name)

        dataStream.writeInt(documents.size)

        // Serialize each document
        for (doc in documents) {
            consumer.accept(doc)

            val docBytes = serializeDocument(doc)
            dataStream.writeInt(docBytes.size)
            dataStream.write(docBytes)
        }

        dataStream.flush()
        return byteStream.toByteArray()
    }

    /**
     * Serialize document to byte array.
     *
     * @param piece the piece
     * @return the byte array
     * @throws IOException if an I/O error occurs
     */
    @Throws(IOException::class)
    fun serializeDocument(piece: Piece): ByteArray {
        val byteStream = ByteArrayOutputStream()
        val dataStream = DataOutputStream(byteStream)

        dataStream.writeLong(piece.id)
        dataStream.writeInt(piece.index)

        dataStream.writeInt(piece.size())

        for ((key, value) in piece.elements) {
            if (value == null) continue

            dataStream.writeUTF(key)

            when (value) {
                is String -> {
                    dataStream.writeByte(1)
                    dataStream.writeUTF(value)
                }
                is Int -> {
                    dataStream.writeByte(2)
                    dataStream.writeInt(value)
                }
                is Long -> {
                    dataStream.writeByte(3)
                    dataStream.writeLong(value)
                }
                is Double -> {
                    dataStream.writeByte(4)
                    dataStream.writeDouble(value)
                }
                is Boolean -> {
                    dataStream.writeByte(5)
                    dataStream.writeBoolean(value)
                }
                is UUID -> {
                    dataStream.writeByte(6)
                    dataStream.writeUTF(value.toString())
                }
                is Serializable -> {
                    dataStream.writeByte(7)
                    val objectStream = ByteArrayOutputStream()
                    ObjectOutputStream(objectStream).use { it.writeObject(value) }
                    val objectBytes = objectStream.toByteArray()
                    dataStream.writeInt(objectBytes.size)
                    dataStream.write(objectBytes)
                }
                else -> throw IOException("Unsupported data type: ${value::class.simpleName}")
            }
        }

        dataStream.flush()
        return byteStream.toByteArray()
    }
}
