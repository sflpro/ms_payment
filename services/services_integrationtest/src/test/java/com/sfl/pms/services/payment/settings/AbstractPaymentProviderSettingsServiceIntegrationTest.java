package com.sfl.pms.services.payment.settings;

import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 1:20 PM
 */
@Ignore
public abstract class AbstractPaymentProviderSettingsServiceIntegrationTest<T extends PaymentProviderSettings> extends AbstractServiceIntegrationTest {

    /* Constructors */
    public AbstractPaymentProviderSettingsServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testGetPaymentProviderSettingsById() {
        final T providerSettings = getInstance();
        // Flush, reload and assert
        final T result = getService().getPaymentProviderSettingsById(providerSettings.getId());
        assertEquals(providerSettings, result);
    }

    /* Abstract methods */
    protected abstract AbstractPaymentProviderSettingsService<T> getService();

    protected abstract T getInstance();
}
