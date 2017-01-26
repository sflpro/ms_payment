package com.sfl.pms.services.encryption;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/4/15
 * Time: 5:11 PM
 */
public interface EncryptionUtilityService {

    /**
     * Calculates SHA2 hash for provided value and salt
     *
     * @param valueToHash
     * @param salt
     * @return calculatedHash
     */
    @Nonnull
    String calculateSha2Hash(@Nonnull final String valueToHash, @Nonnull final String salt);

    /**
     * Calculates HMAC provided value and key by using Sha1
     *
     * @param value
     * @param key
     * @return calculatedHmac
     */
    @Nonnull
    String calculateBase64EncodedHmacSha1(@Nonnull final String value, @Nonnull final String key);

    /**
     * Calculates HMAC provided value and key by using Sha256
     *
     * @param value
     * @param key
     * @return calculatedHmac
     */
    @Nonnull
    String calculateBase64EncodedHmacSha256(@Nonnull final String value, @Nonnull final String key);

    /**
     * Calculates encoded version of the password
     *
     * @param password
     * @return encodedPassword
     */
    @Nonnull
    String encodePassword(@Nonnull final String password);


    /**
     * Calculates encoded version of the password
     *
     * @param password
     * @param encodedPassword
     * @return result
     */
    boolean verifyPassword(@Nonnull final String password, @Nonnull final String encodedPassword);
}
