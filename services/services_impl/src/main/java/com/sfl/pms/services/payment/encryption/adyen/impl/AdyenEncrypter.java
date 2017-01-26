package com.sfl.pms.services.payment.encryption.adyen.impl;

import com.sfl.pms.services.payment.encryption.exception.PaymentProviderEncrypterException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/21/15
 * Time: 1:30 AM
 * Copied from https://github.com/adyenpayments/client-side-encryption/blob/master/android/clientencryption/src/com/adyen/clientencryption/Encrypter.java
 * Modified (line 120)
 */
public class AdyenEncrypter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdyenEncrypter.class);

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /* Constants */
    private static final String PREFIX = "adyenan";

    private static final String VERSION = "0_1_1";

    private static final String SEPARATOR = "$";

    private static final int KEY_SPEC_RADIX = 16;

    private static final int SECRET_KEY_LENGTH = 256;

    private static final int SECRET_IV_SIZE = 12;

    private static final String ERROR_GENERIC_MESSAGE = "Error occurred while encrypting card data";


    /* Properties */
    private PublicKey publicKey;

    private Cipher aesCipher;

    private Cipher rsaCipher;

    private SecureRandom sRandom;

    public AdyenEncrypter(final String publicKeyString) {

        sRandom = new SecureRandom();
        String[] keyComponents = publicKeyString.split("\\|");

        // The bytes can be converted back to a public key object
        KeyFactory keyFactory;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Error occurred while creating key factory", e);
            return;
        }

        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(
                new BigInteger(keyComponents[1].toLowerCase(), KEY_SPEC_RADIX),
                new BigInteger(keyComponents[0].toLowerCase(), KEY_SPEC_RADIX));

        try {
            publicKey = keyFactory.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Problem reading public key: " + publicKeyString, e);
        }

        try {
            aesCipher = Cipher.getInstance("AES/CCM/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Problem instantiation AES Cipher Algorithm", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Problem instantiation AES Cipher Padding", e);
        }

        try {
            rsaCipher = Cipher.getInstance("RSA/None/PKCS1Padding");
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Problem instantiation RSA Cipher Algorithm", e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Problem instantiation RSA Cipher Padding", e);
        } catch (InvalidKeyException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Invalid public key: " + publicKeyString, e);
        }

    }


    public String encrypt(final String plainText) {
        SecretKey aesKey = generateAESKey(SECRET_KEY_LENGTH);

        byte[] iv = generateIV(SECRET_IV_SIZE);

        byte[] encrypted;
        try {
            aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, new IvParameterSpec(iv));
            // getBytes is UTF-8 on Android by default
            // Modified to use  org.apache.commons.codec.binary.StringUtils getBytesUtf8
            encrypted = aesCipher.doFinal(StringUtils.getBytesUtf8(plainText));
        } catch (IllegalBlockSizeException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Incorrect AES Block Size", e);
        } catch (BadPaddingException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Incorrect AES Padding", e);
        } catch (InvalidKeyException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Invalid AES Key", e);
        } catch (InvalidAlgorithmParameterException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Invalid AES Parameters", e);
        }

        byte[] result = new byte[iv.length + encrypted.length];
        // copy IV to result
        System.arraycopy(iv, 0, result, 0, iv.length);
        // copy encrypted to result
        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);

        byte[] encryptedAESKey;
        try {
            encryptedAESKey = rsaCipher.doFinal(aesKey.getEncoded());
            return PREFIX + VERSION + SEPARATOR + Base64.encodeBase64String(encryptedAESKey) + SEPARATOR + Base64.encodeBase64String(result);

        } catch (IllegalBlockSizeException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Incorrect RSA Block Size", e);
        } catch (BadPaddingException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Incorrect RSA Padding", e);
        }
    }


    private SecretKey generateAESKey(final int keySize) {
        KeyGenerator kgen;
        try {
            kgen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(ERROR_GENERIC_MESSAGE, e);
            throw new PaymentProviderEncrypterException("Unable to get AES algorithm", e);
        }
        kgen.init(keySize);
        return kgen.generateKey();
    }

    /**
     * Generate a random Initialization Vector (IV)
     *
     * @param ivSize the SecretKey
     * @return the IV bytes
     */
    private synchronized byte[] generateIV(int ivSize) {

        //Generate random IV AES is always 16bytes, but in CCM mode this represents the NONCE
        byte[] iv = new byte[ivSize];
        sRandom.nextBytes(iv);
        return iv;
    }

    /* Properties getters and setters */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(final PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
