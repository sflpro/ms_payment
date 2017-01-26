package com.sfl.pms.services.payment.notification.impl.adyen.json;

import com.sfl.pms.services.payment.notification.impl.processors.adyen.json.AdyenNotificationJsonDeserializerImpl;
import com.sfl.pms.services.payment.notification.impl.processors.adyen.json.model.AdyenNotificationJsonModel;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.TestSubject;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 4/28/15
 * Time: 2:13 PM
 */
public class AdyenNotificationJsonDeserializerImplTest extends AbstractServicesUnitTest {

    /* Test subject and mocks */
    @TestSubject
    private AdyenNotificationJsonDeserializerImpl adyenNotificationJsonDeserializer = new AdyenNotificationJsonDeserializerImpl();

    /* Constructors */
    public AdyenNotificationJsonDeserializerImplTest() {
    }

    /* Test methods */
    @Test
    public void testDeserializeAdyenNotificationWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            adyenNotificationJsonDeserializer.deserializeAdyenNotification(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testDeserializeAdyenNotification() {
        // Test data
        final AdyenNotificationJsonModel expectedResult = getServicesImplTestHelper().createAdyenNotificationJsonModel();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        final AdyenNotificationJsonModel result = adyenNotificationJsonDeserializer.deserializeAdyenNotification(getServicesImplTestHelper().getAdyenNotificationRawContent());
        assertNotNull(result);
        assertEquals(expectedResult, result);
        // Verify
        verifyAll();
    }

}
