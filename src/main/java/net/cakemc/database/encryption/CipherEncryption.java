package net.cakemc.database.encryption;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * The type Cipher encryption.
 */
public class CipherEncryption extends FileEncryption {

	private final Cipher decrypt;
	private final Cipher encrypt;

	/**
	 * Instantiates a new Cipher encryption.
	 *
	 * @param keyFile the key file
	 */
	public CipherEncryption(AbstractKey keyFile) {
		SecretKey secretKey = keyFile.getKey();

		try {
			encrypt = Cipher.getInstance(keyFile.getAlgorithm());
			encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}

		try {
			decrypt = Cipher.getInstance(keyFile.getAlgorithm());
			decrypt.init(Cipher.DECRYPT_MODE, secretKey);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public byte[] encrypt(byte[] source) {
		if (source.length == 0)
			return source;

		try {
			return encrypt.doFinal(source);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public byte[] decrypt(byte[] source) {
		if (source.length == 0)
			return source;

		try {
			return decrypt.doFinal(source);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new RuntimeException(e);
		}
	}


}