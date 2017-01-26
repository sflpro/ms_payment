package com.sfl.pms.services.encryption;

import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/4/15
 * Time: 5:57 PM
 */
public class EncryptionUtilityServiceIntegrationTest extends AbstractServiceIntegrationTest {

    /* Dependencies */
    @Autowired
    private EncryptionUtilityService encryptionUtilityService;

    /* Constructors */
    public EncryptionUtilityServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testCalculateSHA2Hash() {
        // Prepare data
        final String valueToHash = "longloongvaluetohash";
        final String salt = "longloongsalt";
        // Calculate hash
        final String hash = encryptionUtilityService.calculateSha2Hash(valueToHash, salt);
        // Check that hash is not null, correctness of algorithm is checked in unit test
        assertNotNull(hash);
    }

    @Test
    public void testEncodePassword() {
        // Test data
        final String password = "KHUYGYFY&&)YOVYYL";
        // Encode password
        final String encodedPassword = encryptionUtilityService.encodePassword(password);
        assertNotNull(encodedPassword);
        assertFalse(password.equals(encodedPassword));
    }

    @Test
    public void testVerifyPassword() {
        // Test data
        final String password = "KHUYGYFY&&)YOVYYL";
        // Encode password
        final String encodedPassword = encryptionUtilityService.encodePassword(password);
        // Verify password
        boolean result = encryptionUtilityService.verifyPassword(password, encodedPassword);
        assertTrue(result);
        // Try to verify against different password
        result = encryptionUtilityService.verifyPassword(password + "_updated", encodedPassword);
        assertFalse(result);
    }
}
