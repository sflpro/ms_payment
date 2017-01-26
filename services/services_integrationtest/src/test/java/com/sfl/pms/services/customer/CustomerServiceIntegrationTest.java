package com.sfl.pms.services.customer;

import com.sfl.pms.services.customer.dto.CustomerDto;
import com.sfl.pms.services.customer.exception.CustomerAlreadyExistsForUuIdException;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.test.AbstractServiceIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Customer: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 10/01/14
 * Time: 10:18 PM
 */
public class CustomerServiceIntegrationTest extends AbstractServiceIntegrationTest {


    /* Properties */
    @Autowired
    private CustomerService userService;

    /* Constructors */
    public CustomerServiceIntegrationTest() {

    }

    /* Test methods */
    @Test
    public void testGetOrCreateCustomerForUuId() {
        // Prepare data
        final CustomerDto customerDto = getServicesTestHelper().createCustomerDto();
        // Check if user exists
        boolean userExists = userService.checkIfCustomerExistsForUuId(customerDto.getUuId());
        assertFalse(userExists);
        // Get or create user
        final Customer firstCustomer = userService.getOrCreateCustomerForUuId(customerDto);
        assertNotNull(firstCustomer);
        userExists = userService.checkIfCustomerExistsForUuId(customerDto.getUuId());
        assertTrue(userExists);
        // Flush, clear and try again
        flushAndClear();
        userExists = userService.checkIfCustomerExistsForUuId(customerDto.getUuId());
        assertTrue(userExists);
        // Call again and check that same user is returned
        final Customer secondCustomer = userService.getOrCreateCustomerForUuId(customerDto);
        assertNotNull(secondCustomer);
        Assert.assertEquals(firstCustomer.getId(), secondCustomer.getId());
    }

    @Test
    public void testCheckIfCustomerExistsForUuId() {
        // Prepare data
        final CustomerDto userDto = getServicesTestHelper().createCustomerDto();
        // Check no user exists
        boolean userExists = userService.checkIfCustomerExistsForUuId(userDto.getUuId());
        assertFalse(userExists);
        // Create user
        final Customer user = userService.createCustomer(userDto);
        assertNotNull(user);
        // Check if user exists
        userExists = userService.checkIfCustomerExistsForUuId(userDto.getUuId());
        assertTrue(userExists);
        // Flush, clear, reload and assert again
        flushAndClear();
        userExists = userService.checkIfCustomerExistsForUuId(userDto.getUuId());
        assertTrue(userExists);
    }

    @Test
    public void testGetCustomerById() {
        // Prepare data
        final Customer user = getServicesTestHelper().createCustomer();
        // Load user by id
        Customer result = userService.getCustomerById(user.getId());
        assertEquals(user, result);
        // Flush, clear, reload and assert again
        flushAndClear();
        result = userService.getCustomerById(user.getId());
        assertEquals(user, result);
    }

    @Test
    public void testGetCustomerByUuId() {
        // Prepare data
        final Customer user = getServicesTestHelper().createCustomer();
        // Load user by id
        Customer result = userService.getCustomerByUuId(user.getUuId());
        assertEquals(user, result);
        // Flush, clear, reload and assert again
        flushAndClear();
        result = userService.getCustomerByUuId(user.getUuId());
        assertEquals(user, result);
    }

    @Test
    public void testCreateCustomer() {
        // Prepare data
        final CustomerDto userDto = getServicesTestHelper().createCustomerDto();
        // Create user
        Customer user = userService.createCustomer(userDto);
        getServicesTestHelper().assertCustomer(user, userDto);
        // Flush, clear, reload and assert again
        flushAndClear();
        user = userService.getCustomerById(user.getId());
        getServicesTestHelper().assertCustomer(user, userDto);
    }

    @Test
    public void testCreateCustomerWithExistingUuId() {
        // Prepare data
        final CustomerDto userDto = getServicesTestHelper().createCustomerDto();
        // Create user
        Customer user = userService.createCustomer(userDto);
        assertNotNull(user);
        // Try to recreate user
        try {
            userService.createCustomer(userDto);
            fail("Exception should be thrown");
        } catch (final CustomerAlreadyExistsForUuIdException ex) {
            // Expected
        }
        // Flush, clear, reload and assert again
        flushAndClear();
        // Try to recreate user
        try {
            userService.createCustomer(userDto);
            fail("Exception should be thrown");
        } catch (final CustomerAlreadyExistsForUuIdException ex) {
            // Expected
        }
    }
}
