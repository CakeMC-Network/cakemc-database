package net.cakemc.database.encryption;

/**
 * The type File encryption.
 */
public abstract class FileEncryption {
    /**
     * Encrypt byte [ ].
     *
     * @param source the source
     * @return the byte [ ]
     */
    public abstract byte[] encrypt(byte[] source);

    /**
     * Decrypt byte [ ].
     *
     * @param source the source
     * @return the byte [ ]
     */
    public abstract byte[] decrypt(byte[] source);
}
