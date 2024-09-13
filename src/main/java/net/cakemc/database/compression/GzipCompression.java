package net.cakemc.database.compression;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.*;

/**
 * The type Gzip compression.
 */
public class GzipCompression extends FileCompression {


    private final Deflater deflater;
    private final Inflater inflater;

    /**
     * Instantiates a new Database compression.
     */
    public GzipCompression() {
        deflater = new Deflater(Deflater.BEST_COMPRESSION);
        inflater = new Inflater();
    }

    @Override
    public byte[] compress(byte[] data) {
        deflater.reset();

        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }

        try {
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return outputStream.toByteArray();
    }

    @Override
    public byte[] decompress(byte[] compressedData) {
        inflater.reset();

        inflater.setInput(compressedData);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(compressedData.length);

        try {
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }

            outputStream.close();
        } catch (IOException | DataFormatException e) {
            throw new RuntimeException(e);
        }

        return outputStream.toByteArray();
    }

}
