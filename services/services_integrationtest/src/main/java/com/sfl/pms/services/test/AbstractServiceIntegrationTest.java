package com.sfl.pms.services.test;

import com.sfl.pms.services.helper.ServicesTestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Ruben Dilanyan
 *         <p>
 *         Aug 25, 2013
 */
@ContextConfiguration("classpath:applicationContext-services-integrationtest.xml")
public abstract class AbstractServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ServicesTestHelper servicesTestHelper;

    @PersistenceContext
    private EntityManager entityManager;


    @Value("#{appProperties['test.integration.payment.enable']}")
    private boolean enablePaymentIntegrationTests;


    public AbstractServiceIntegrationTest() {

    }

    /* Utility methods */
    protected boolean isWindowsOS() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    protected boolean isEnablePaymentIntegrationTests() {
        return enablePaymentIntegrationTests;
    }

    protected void flush() {
        entityManager.flush();
    }

    protected void clear() {
        entityManager.clear();
    }

    protected void flushAndClear() {
        flush();
        clear();
    }

    /* Properties */
    public ServicesTestHelper getServicesTestHelper() {
        return servicesTestHelper;
    }
}
