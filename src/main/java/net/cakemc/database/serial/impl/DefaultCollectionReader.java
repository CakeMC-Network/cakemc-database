package net.cakemc.database.serial.impl;

import net.cakemc.database.api.DatabaseRecord;
import net.cakemc.database.api.Piece;
import net.cakemc.database.collection.Collection;
import net.cakemc.database.collection.PieceCollection;
import net.cakemc.database.serial.AbstractRead;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

/**
 * The type Default collection reader.
 */
public class DefaultCollectionReader extends AbstractRead {

    @Override
    public Collection<DatabaseRecord> read(byte[] data) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        DataInputStream dataStream = new DataInputStream(byteStream);

        // Read number of documents
        long id = dataStream.readLong();
        String name = dataStream.readUTF();

        int numDocuments = dataStream.readInt();
        List<DatabaseRecord> documents = new ArrayList<>(numDocuments);

        // Deserialize each document
        for (int i = 0 ; i < numDocuments ; i++) {
            int docSize = dataStream.readInt();
            byte[] docBytes = new byte[docSize];
            dataStream.readFully(docBytes);
            Piece doc = readElement(docBytes);
            documents.add(doc);
        }

        return new PieceCollection(documents, id, name);
    }

    /**
     * Read element piece.
     *
     * @param data the data
     * @return the piece
     * @throws IOException the io exception
     */
    public Piece readElement(byte[] data) throws IOException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        DataInputStream dataStream = new DataInputStream(byteStream);

        long documentId = dataStream.readLong();
        int documentIndex = dataStream.readInt();

        int size = dataStream.readInt();
        Hashtable<String, Object> values = new Hashtable<>(size);

        for (int i = 0 ; i < size ; i++) {
            String key = dataStream.readUTF();

            byte typeId = dataStream.readByte();
            Object value;
            switch (typeId) {
                case 1:
                    value = dataStream.readUTF();
                    break;
                case 2:
                    value = dataStream.readInt();
                    break;
                case 3:
                    value = dataStream.readLong();
                    break;
                case 4:
                    value = dataStream.readDouble();
                    break;
                case 5:
                    value = dataStream.readBoolean();
                    break;
                case 6:
                    value = UUID.fromString(dataStream.readUTF());
                    break;
                case 7:
                    int objectSize = dataStream.readInt();
                    byte[] objectBytes = new byte[objectSize];
                    dataStream.readFully(objectBytes);
                    ByteArrayInputStream objectStream = new ByteArrayInputStream(objectBytes);
                    ObjectInputStream objInStream = new ObjectInputStream(objectStream);
                    try {
                        value = objInStream.readObject();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    throw new IOException("Unknown type identifier: " + typeId);
            }
            values.put(key, value);
        }

        return new Piece(documentIndex, documentId, values);
    }

}
