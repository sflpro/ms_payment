package com.sfl.pms.services.encryption.impl;

import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.apache.commons.lang3.StringUtils;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/4/15
 * Time: 5:27 PM
 */
public class EncryptionUtilityServiceImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private EncryptionUtilityServiceImpl encryptionUtilityService = new EncryptionUtilityServiceImpl();

    @Mock
    private PasswordEncoder passwordEncoder;

    /* Constructors */
    public EncryptionUtilityServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testCalculateBase64EncodedHmacSha256WithInvalidArguments() {
        // Test data
        final String key = "HLGYFT6rfGK&(R%^Cgk";
        final String value = "testValue";
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            encryptionUtilityService.calculateBase64EncodedHmacSha256(null, key);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptionUtilityService.calculateBase64EncodedHmacSha256(value, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCalculateBase64EncodedHmacSha256() throws DecoderException, UnsupportedEncodingException {
        // Test data
        final String key = "F43B5DED28093CA8FE8255340FDAE6BA51CA77E538017DF407D1A8728BFA1E91";
        final String value = "10000GBP2007-10-20Internet Order 123454aD37dJATestMerchant2007-10-11T11:00:00Z";
        final byte[] keyHexBytes = Hex.decodeHex(key.toCharArray());
        final byte[] valueBytes = value.getBytes("UTF-8");
        final byte[] hmac = HmacUtils.hmacSha256(keyHexBytes, valueBytes);
        final String hmacBase64 = Base64.encodeBase64String(hmac);
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final String result = encryptionUtilityService.calculateBase64EncodedHmacSha256(value, key);
        assertEquals(hmacBase64, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCalculateBase64EncodedHmacSha1WithInvalidArguments() {
        // Test data
        final String key = "HLGYFT6rfGK&(R%^Cgk";
        final String value = "testValue";
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            encryptionUtilityService.calculateBase64EncodedHmacSha1(null, key);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptionUtilityService.calculateBase64EncodedHmacSha1(value, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCalculateBase64EncodedHmacSha1() {
        // Test data
        final String key = "Kah942*$7sdp0)";
        final String value = "10000GBP2007-10-20Internet Order 123454aD37dJATestMerchant2007-10-11T11:00:00Z";
        final byte[] hmac = HmacUtils.hmacSha1(key, value);
        final String hmacBase64 = Base64.encodeBase64String(hmac);
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final String result = encryptionUtilityService.calculateBase64EncodedHmacSha1(value, key);
        assertEquals(hmacBase64, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testVerifyPasswordWithInvalidArguments() {
        // Test data
        final String password = "JHUGYOLIHYY";
        final String encodedPassword = "JHUGYOLIHYY:UGYFTTRRTTYYU";
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            encryptionUtilityService.verifyPassword(null, encodedPassword);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptionUtilityService.verifyPassword(password, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testVerifyPasswordWhenItMatches() {
        // Test data
        final String password = "JHUGYOLIHYY";
        final String encodedPassword = "JHUGYOLIHYY:UGYFTTRRTTYYU";
        // Reset
        resetAll();
        // Expectations
        expect(passwordEncoder.matches(eq(password), eq(encodedPassword))).andReturn(false).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = encryptionUtilityService.verifyPassword(password, encodedPassword);
        assertFalse(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testVerifyPasswordWhenItDoesNotMatch() {
        // Test data
        final String password = "JHUGYOLIHYY";
        final String encodedPassword = "JHUGYOLIHYY:UGYFTTRRTTYYU";
        // Reset
        resetAll();
        // Expectations
        expect(passwordEncoder.matches(eq(password), eq(encodedPassword))).andReturn(true).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = encryptionUtilityService.verifyPassword(password, encodedPassword);
        assertTrue(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testEncodePasswordWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            encryptionUtilityService.encodePassword(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testEncodePassword() {
        // Test data
        final String password = "UGTFTFOYFOYFTFOTFYO";
        final String encodedPassword = "UGTFTFOYFOYFTFOTFYOKHUGOUYGOYGYOOTO";
        // Reset
        resetAll();
        // Expectations
        expect(passwordEncoder.encode(eq(password))).andReturn(encodedPassword).once();
        // Replay
        replayAll();
        // Run test scenario
        final String result = encryptionUtilityService.encodePassword(password);
        assertEquals(encodedPassword, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCalculateSHA2HashWithInvalidArguments() {
        // Test data
        final String valueToHash = "longloongvaluetohash";
        final String salt = "longloongsalt";
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            encryptionUtilityService.calculateSha2Hash(null, salt);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            encryptionUtilityService.calculateSha2Hash(valueToHash, null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCalculateSHA2Hash() {
        // Test data
        final String valueToHash = "longloongvaluetohash";
        final String salt = "TFCDD6742&VGJHC$#GV::";
        final String expectedHashWithSalt = Sha2Crypt.sha256Crypt(valueToHash.getBytes(Charset.forName("UTF8")), "$5$" + salt);
        final String expectedHash = expectedHashWithSalt.replaceFirst("^" + Pattern.quote("$5$") + ".*" + Pattern.quote("$"), StringUtils.EMPTY);
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final String hashedValue = encryptionUtilityService.calculateSha2Hash(valueToHash, salt);
        assertNotNull(hashedValue);
        assertEquals(expectedHash, hashedValue);
        // Verify
        verifyAll();
    }

}
