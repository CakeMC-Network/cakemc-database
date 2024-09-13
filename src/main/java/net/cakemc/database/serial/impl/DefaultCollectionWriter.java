package net.cakemc.database.serial.impl;

import net.cakemc.database.api.DatabaseRecord;
import net.cakemc.database.api.Piece;
import net.cakemc.database.collection.Collection;
import net.cakemc.database.serial.AbstractWrite;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * The type Default collection writer.
 */
public class DefaultCollectionWriter extends AbstractWrite {

    @Override
    public byte[] writeCollection(Collection<DatabaseRecord> collection, Consumer<Piece> consumer) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        List<Piece> documents = collection.collect();

        // Write number of documents
        dataStream.writeLong(collection.getId());
        dataStream.writeUTF(collection.getName());

        dataStream.writeInt(documents.size());

        // Serialize each document
        for (Piece doc : documents) {
            consumer.accept(doc);

            byte[] docBytes = this.serializeDocument(doc);
            dataStream.writeInt(docBytes.length);
            dataStream.write(docBytes);
        }

        dataStream.flush();
        return byteStream.toByteArray();
    }

    /**
     * Serialize document byte [ ].
     *
     * @param piece the piece
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    public byte[] serializeDocument(Piece piece) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        dataStream.writeLong(piece.getId());
        dataStream.writeInt(piece.getIndex());

        dataStream.writeInt(piece.size());

        for (Map.Entry<String, Object> entry : piece.getElements().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value == null)
                continue;

            dataStream.writeUTF(key);

            switch (value) {
                case String s -> {
                    dataStream.writeByte(1);

                    dataStream.writeUTF(s);
                }
                case Integer i -> {
                    dataStream.writeByte(2);

                    dataStream.writeInt(i);
                }
                case Long l -> {
                    dataStream.writeByte(3);

                    dataStream.writeLong(l);
                }
                case Double v -> {
                    dataStream.writeByte(4);

                    dataStream.writeDouble(v);
                }
                case Boolean b -> {
                    dataStream.writeByte(5);
                    dataStream.writeBoolean(b);
                }
                case UUID uuid -> {
                    dataStream.writeByte(6);
                    dataStream.writeUTF(uuid.toString());
                }
                case Serializable serializable -> {
                    dataStream.writeByte(7);
                    ByteArrayOutputStream objectStream = new ByteArrayOutputStream();
                    ObjectOutputStream objOutStream = new ObjectOutputStream(objectStream);
                    objOutStream.writeObject(value);
                    objOutStream.flush();
                    byte[] objectBytes = objectStream.toByteArray();
                    dataStream.writeInt(objectBytes.length);
                    dataStream.write(objectBytes);
                }
                case null, default -> throw new IOException("Unsupported data type: " + value.getClass().getSimpleName());
            }
        }

        dataStream.flush();
        return byteStream.toByteArray();
    }

}
