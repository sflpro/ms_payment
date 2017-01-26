package com.sfl.pms.services.payment.settings.impl;

import com.sfl.pms.persistence.repositories.payment.settings.AbstractPaymentProviderSettingsRepository;
import com.sfl.pms.services.payment.settings.AbstractPaymentProviderSettingsService;
import com.sfl.pms.services.payment.settings.exception.PaymentProviderSettingsNotFoundForIdException;
import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.junit.Test;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 1:08 PM
 */
public abstract class AbstractPaymentProviderSettingsServiceImplTest<T extends PaymentProviderSettings> extends AbstractServicesUnitTest {

    /* Constructors */
    public AbstractPaymentProviderSettingsServiceImplTest() {
    }

    /* Test methods */
    @Test
    public void testGetPaymentProviderSettingsByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentProviderSettingsById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderSettingsByIdWithNotExistingId() {
        // Test data
        final Long settingsId = 1l;
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(settingsId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            getService().getPaymentProviderSettingsById(settingsId);
            fail("Exception should be thrown");
        } catch (final PaymentProviderSettingsNotFoundForIdException ex) {
            // Expected
            assertPaymentProviderSettingsNotFoundException(ex, settingsId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetPaymentProviderSettingsById() {
        // Test data
        final Long settingsId = 1l;
        final T settings = getInstance();
        // Reset
        resetAll();
        // Expectations
        expect(getRepository().findOne(eq(settingsId))).andReturn(settings).once();
        // Replay
        replayAll();
        // Run test scenario
        final T result = getService().getPaymentProviderSettingsById(settingsId);
        assertEquals(settings, result);
        // Verify
        verifyAll();
    }

    /* Utility methods */
    protected void assertPaymentProviderSettingsNotFoundException(final PaymentProviderSettingsNotFoundForIdException ex, final Long id) {
        assertEquals(id, ex.getId());
        assertEquals(getInstanceClass(), ex.getEntityClass());
    }

    /* Abstract methods */
    protected abstract AbstractPaymentProviderSettingsService<T> getService();

    protected abstract AbstractPaymentProviderSettingsRepository<T> getRepository();

    protected abstract Class<T> getInstanceClass();

    protected abstract T getInstance();
}
