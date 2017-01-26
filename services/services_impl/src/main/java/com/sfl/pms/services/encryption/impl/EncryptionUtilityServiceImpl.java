package com.sfl.pms.services.encryption.impl;

import com.sfl.pms.services.common.exception.ServicesRuntimeException;
import com.sfl.pms.services.encryption.EncryptionUtilityService;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/4/15
 * Time: 5:11 PM
 */
@Service
public class EncryptionUtilityServiceImpl implements EncryptionUtilityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptionUtilityServiceImpl.class);

    /* Constants */
    private static final String SALT_PREFIX = "$5$";

    private static final String SALT_POSTFIX = "$";

    private static final String SALT_PATTERN = "^" + Pattern.quote(SALT_PREFIX) + ".*" + Pattern.quote(SALT_POSTFIX);

    private static final String HMAC_SHA256_ENCODING = "UTF-8";

    /* Password encoder */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /* Constructors */
    public EncryptionUtilityServiceImpl() {
        LOGGER.debug("Initializing encryption utility service");
    }


    @Nonnull
    @Override
    public String calculateSha2Hash(@Nonnull final String valueToHash, @Nonnull final String salt) {
        Assert.notNull(valueToHash, "Value to hash should not be null");
        Assert.notNull(salt, "Salt should not be null");
        LOGGER.debug("Calculating SHA2 hash");
        final String hashedValue = Sha2Crypt.sha256Crypt(valueToHash.getBytes(Charset.forName("UTF8")), SALT_PREFIX + salt);
        final String hashedValueWithoutSalt = hashedValue.replaceFirst(SALT_PATTERN, StringUtils.EMPTY);
        LOGGER.debug("Successfully calculated hash - {}", hashedValueWithoutSalt);
        return hashedValueWithoutSalt;
    }

    @Nonnull
    @Override
    public String calculateBase64EncodedHmacSha1(@Nonnull final String value, @Nonnull final String key) {
        Assert.notNull(value, "Value should not be null");
        Assert.notNull(key, "Key should not be null");
        LOGGER.debug("Calculating HMAC Sha1");
        final byte[] hmac = HmacUtils.hmacSha1(key, value);
        final String hmacBase64 = Base64.encodeBase64String(hmac);
        LOGGER.debug("Successfully calculated hmac encoded into base64 - {}", hmacBase64);
        return hmacBase64;
    }

    @Nonnull
    @Override
    public String calculateBase64EncodedHmacSha256(@Nonnull String value, @Nonnull String key) {
        Assert.notNull(value, "Value should not be null");
        Assert.notNull(key, "Key should not be null");
        LOGGER.debug("Calculating HMAC Sha256");
        final byte[] keyHexBytes;
        try {
            keyHexBytes = Hex.decodeHex(key.toCharArray());
        } catch (DecoderException e) {
            LOGGER.error("Error when getting hex bytes from hex string - {}", key);
            throw new ServicesRuntimeException("Error when getting hex bytes from hex string - " + key, e);
        }
        final byte[] valueBytes;
        try {
            valueBytes = value.getBytes(HMAC_SHA256_ENCODING);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error when getting from string - {}, with encoding - {}", value, HMAC_SHA256_ENCODING);
            throw new ServicesRuntimeException("Error when getting from string - " + value + ", with encoding - " + HMAC_SHA256_ENCODING, e);
        }
        final byte[] hmac = HmacUtils.hmacSha256(keyHexBytes, valueBytes);
        final String hmacBase64 = Base64.encodeBase64String(hmac);
        LOGGER.debug("Successfully calculated hmac encoded into base64 - {}", hmacBase64);
        return hmacBase64;
    }

    @Nonnull
    @Override
    public String encodePassword(@Nonnull final String password) {
        assertPasswordNotNull(password);
        LOGGER.debug("Generating encoded password");
        final String encodedPassword = passwordEncoder.encode(password);
        LOGGER.debug("Successfully encoded password");
        return encodedPassword;
    }

    @Override
    public boolean verifyPassword(@Nonnull String password, @Nonnull String encodedPassword) {
        assertPasswordNotNull(password);
        Assert.notNull(encodedPassword, "Encoded password should not be null");
        LOGGER.debug("Verifying if password matches");
        final boolean matches = passwordEncoder.matches(password, encodedPassword);
        LOGGER.debug("Password verification result is  - {}", matches);
        return matches;
    }

    /* Utility methods */
    private void assertPasswordNotNull(final String password) {
        Assert.notNull(password, "Password should not be null");
    }

    /* Properties getters and setters */
    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
