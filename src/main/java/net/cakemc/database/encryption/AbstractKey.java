package net.cakemc.database.encryption;

import javax.crypto.SecretKey;

/**
 * The type Abstract key.
 */
public abstract class AbstractKey {

    /**
     * Gets key.
     *
     * @return the key
     */
    public abstract SecretKey getKey();

    /**
     * Gets algorithm.
     *
     * @return the algorithm
     */
    public abstract String getAlgorithm();

}
