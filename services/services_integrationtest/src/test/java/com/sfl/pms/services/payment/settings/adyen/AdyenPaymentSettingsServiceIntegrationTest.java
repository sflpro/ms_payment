package com.sfl.pms.services.payment.settings.adyen;

import com.sfl.pms.services.payment.settings.AbstractPaymentProviderSettingsService;
import com.sfl.pms.services.payment.settings.AbstractPaymentProviderSettingsServiceIntegrationTest;
import com.sfl.pms.services.payment.settings.dto.adyen.AdyenPaymentSettingsDto;
import com.sfl.pms.services.payment.settings.exception.PaymentProviderSettingsExistsForEnvironmentException;
import com.sfl.pms.services.payment.settings.model.adyen.AdyenPaymentSettings;
import com.sfl.pms.services.system.environment.model.EnvironmentType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 1:24 PM
 */
public class AdyenPaymentSettingsServiceIntegrationTest extends AbstractPaymentProviderSettingsServiceIntegrationTest<AdyenPaymentSettings> {

    /* Dependencies */
    @Autowired
    private AdyenPaymentSettingsService adyenPaymentSettingsService;

    @Value("#{ appProperties['adyen.environment.type']}")
    private String environmentType;

    /* Constructors */
    public AdyenPaymentSettingsServiceIntegrationTest() {
    }

    /* Test methods */
    @Test
    public void testGetActivePaymentSettings() {
        // Prepare data
        final AdyenPaymentSettingsDto adyenPaymentSettingsDto = getServicesTestHelper().createAdyenPaymentSettingsDto();
        adyenPaymentSettingsDto.setEnvironmentType(EnvironmentType.valueOf(environmentType));
        final AdyenPaymentSettings adyenPaymentSettings = getServicesTestHelper().createAdyenPaymentSettings(adyenPaymentSettingsDto);
        flushAndClear();
        // Load current settings
        final AdyenPaymentSettings result = adyenPaymentSettingsService.getActivePaymentSettings();
        assertEquals(adyenPaymentSettings, result);
    }

    @Test
    public void testCreatePaymentProviderSettings() {
        // Prepare data
        final AdyenPaymentSettingsDto paymentSettingsDto = getServicesTestHelper().createAdyenPaymentSettingsDto();
        // Create settings
        AdyenPaymentSettings paymentSettings = adyenPaymentSettingsService.createPaymentProviderSettings(paymentSettingsDto);
        getServicesTestHelper().assertAdyenPaymentSettings(paymentSettings, paymentSettingsDto);
        // Flush, reload and assert again
        flushAndClear();
        paymentSettings = adyenPaymentSettingsService.getPaymentProviderSettingsById(paymentSettings.getId());
        getServicesTestHelper().assertAdyenPaymentSettings(paymentSettings, paymentSettingsDto);
    }

    @Test
    public void testCreatePaymentProviderSettingsWhenTheyExistsForEnvironment() {
        // Prepare data
        final AdyenPaymentSettingsDto paymentSettingsDto = getServicesTestHelper().createAdyenPaymentSettingsDto();
        // Create settings
        AdyenPaymentSettings paymentSettings = adyenPaymentSettingsService.createPaymentProviderSettings(paymentSettingsDto);
        getServicesTestHelper().assertAdyenPaymentSettings(paymentSettings, paymentSettingsDto);
        try {
            adyenPaymentSettingsService.createPaymentProviderSettings(paymentSettingsDto);
            fail("Exception should be thrown");
        } catch (final PaymentProviderSettingsExistsForEnvironmentException ex) {
            // Expected
        }
        // Flush, reload and assert again
        flushAndClear();
        try {
            adyenPaymentSettingsService.createPaymentProviderSettings(paymentSettingsDto);
            fail("Exception should be thrown");
        } catch (final PaymentProviderSettingsExistsForEnvironmentException ex) {
            // Expected
        }
    }


    /* Utility methods */
    @Override
    protected AbstractPaymentProviderSettingsService<AdyenPaymentSettings> getService() {
        return adyenPaymentSettingsService;
    }

    @Override
    protected AdyenPaymentSettings getInstance() {
        return getServicesTestHelper().createAdyenPaymentSettings();
    }
}
