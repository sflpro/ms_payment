package com.sfl.pms.scheduler.test;

import com.sfl.pms.scheduler.jobs.helper.SchedulerTestHelper;
import com.sfl.pms.services.helper.ServicesTestHelper;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/4/14
 * Time: 9:15 PM
 */
@Ignore
@ContextConfiguration(value = {"classpath:applicationContext-scheduler-integrationtest.xml", "classpath:applicationContext-services-integrationtest.xml"})
public class AbstractSchedulerIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {

    /* Dependencies */
    @Autowired
    private SchedulerTestHelper schedulerTestHelper;

    @Autowired
    private ServicesTestHelper servicesTestHelper;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("#{appProperties['test.integration.payment.enable']}")
    private boolean enablePaymentIntegrationTests;

    /* Constrictors */
    public AbstractSchedulerIntegrationTest() {
    }

    /* Utility methods */
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

    protected boolean isEnablePaymentIntegrationTests() {
        return enablePaymentIntegrationTests;
    }

    /* Getters and setters */
    protected SchedulerTestHelper getSchedulerTestHelper() {
        return schedulerTestHelper;
    }

    public void setSchedulerTestHelper(final SchedulerTestHelper schedulerTestHelper) {
        this.schedulerTestHelper = schedulerTestHelper;
    }

    public void setServicesTestHelper(ServicesTestHelper servicesTestHelper) {
        this.servicesTestHelper = servicesTestHelper;
    }

    protected ServicesTestHelper getServicesTestHelper() {
        return servicesTestHelper;
    }
}
