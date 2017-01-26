package com.sfl.pms.services.payment.settings;

import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;
import com.sfl.pms.services.payment.settings.model.adyen.AdyenPaymentSettings;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 1:23 PM
 */
public class PaymentProviderSettingsServiceIntegrationTest extends AbstractPaymentProviderSettingsServiceIntegrationTest<PaymentProviderSettings> {

    /* Dependencies */
    @Autowired
    private PaymentProviderSettingsService paymentProviderSettingsService;

    /* Constructors */
    public PaymentProviderSettingsServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testGetActivePaymentProviderSettingsForTypeWithAdyen() {
        // Prepare data
        final AdyenPaymentSettings adyenPaymentSettings = getServicesTestHelper().createAdyenPaymentSettings();
        // Load active settings for type and assert
        PaymentProviderSettings result = paymentProviderSettingsService.getActivePaymentProviderSettingsForType(PaymentProviderType.ADYEN);
        assertNotNull(result);
        Assert.assertEquals(adyenPaymentSettings.getId(), result.getId());
        // Flush, clear, reload and assert
        flushAndClear();
        result = paymentProviderSettingsService.getActivePaymentProviderSettingsForType(PaymentProviderType.ADYEN);
        assertNotNull(result);
        Assert.assertEquals(adyenPaymentSettings.getId(), result.getId());
    }

    /* Utility methods */
    @Override
    protected AbstractPaymentProviderSettingsService<PaymentProviderSettings> getService() {
        return paymentProviderSettingsService;
    }

    @Override
    protected PaymentProviderSettings getInstance() {
        return getServicesTestHelper().createAdyenPaymentSettings();
    }
}
