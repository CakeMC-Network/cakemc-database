package net.cakemc.database.compression;

/**
 * The type File compression.
 */
public abstract class FileCompression {

    /**
     * Compress byte [ ].
     *
     * @param source the source
     * @return the byte [ ]
     */
    public abstract byte[] compress(byte[] source);

    /**
     * Decompress byte [ ].
     *
     * @param source the source
     * @return the byte [ ]
     */
    public abstract byte[] decompress(byte[] source);
}
