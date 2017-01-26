package com.sfl.pms.services.customer.impl;

import com.sfl.pms.persistence.repositories.customer.CustomerRepository;
import com.sfl.pms.services.customer.dto.CustomerDto;
import com.sfl.pms.services.customer.exception.CustomerAlreadyExistsForUuIdException;
import com.sfl.pms.services.customer.exception.CustomerNotFoundForIdException;
import com.sfl.pms.services.customer.exception.CustomerNotFoundForUuidException;
import com.sfl.pms.services.customer.model.Customer;
import com.sfl.pms.services.test.AbstractServicesUnitTest;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

/**
 * Customer: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 10/01/14
 * Time: 10:18 PM
 */
public class CustomerServiceImplTest extends AbstractServicesUnitTest {

    /* Test target and dependencies */
    @TestSubject
    private CustomerServiceImpl userService = new CustomerServiceImpl();

    @Mock
    private CustomerRepository userRepository;


    /* Constructors */
    public CustomerServiceImplTest() {

    }

    /* Test methods */
    @Test
    public void testGetOrCreateCustomerForUuIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            userService.getOrCreateCustomerForUuId(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrCreateCustomerForUuIdWhenCustomerExists() {
        // Test data
        final Long userId = 1L;
        final Customer user = getServicesImplTestHelper().createCustomer();
        user.setId(userId);
        final CustomerDto customerDto = getServicesImplTestHelper().createCustomerDto();
        // Reset
        resetAll();
        // Expectations
        expect(userRepository.findByUuId(eq(customerDto.getUuId()))).andReturn(user).once();
        // Replay
        replayAll();
        // Run test scenario
        final Customer result = userService.getOrCreateCustomerForUuId(customerDto);
        assertEquals(user, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetOrCreateCustomerForUuIdWhenCustomerDoesNotExist() {
        // Test data
        final CustomerDto customerDto = getServicesImplTestHelper().createCustomerDto();
        // Reset
        resetAll();
        // Expectations
        expect(userRepository.findByUuId(eq(customerDto.getUuId()))).andReturn(null).times(2);
        expect(userRepository.save(isA(Customer.class))).andAnswer(() -> (Customer) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final Customer result = userService.getOrCreateCustomerForUuId(customerDto);
        getServicesImplTestHelper().assertCustomer(result, customerDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerByUuIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            userService.getCustomerByUuId(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerByUuIdWithNotExistingUuId() {
        // Test data
        final String uuId = "KBLHOYUFOYTF^&FOYCCKTHLJ:";
        // Reset
        resetAll();
        // Expectations
        expect(userRepository.findByUuId(eq(uuId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            userService.getCustomerByUuId(uuId);
            fail("Exception should be thrown");
        } catch (final CustomerNotFoundForUuidException ex) {
            // Expected
            assertCustomerNotFoundForUuidException(ex, uuId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerByUuId() {
        // Test data
        final String uuId = "KBLHOYUFOYTF^&FOYCCKTHLJ:";
        final Customer user = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Expectations
        expect(userRepository.findByUuId(eq(uuId))).andReturn(user).once();
        // Replay
        replayAll();
        // Run test scenario
        final Customer result = userService.getCustomerByUuId(uuId);
        assertEquals(user, result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfCustomerExistsForUuIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            userService.checkIfCustomerExistsForUuId(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfCustomerExistsForUuIdWhenCustomerExists() {
        // Test data
        final String uuId = "JBYIUYFITYDR^DITDTTYCTK";
        final Customer user = getServicesImplTestHelper().createCustomer();
        // Reset
        resetAll();
        // Expectations
        expect(userRepository.findByUuId(eq(uuId))).andReturn(user).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = userService.checkIfCustomerExistsForUuId(uuId);
        assertTrue(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCheckIfCustomerExistsForUuIdWhenCustomerDoesNotExist() {
        // Test data
        final String uuId = "JBYIUYFITYDR^DITDTTYCTK";
        // Reset
        resetAll();
        // Expectations
        expect(userRepository.findByUuId(eq(uuId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenario
        final boolean result = userService.checkIfCustomerExistsForUuId(uuId);
        assertFalse(result);
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateCustomerWithInvalidArguments() {
        // Test data
        final CustomerDto customerDto = getServicesImplTestHelper().createCustomerDto();
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenario
        try {
            userService.createCustomer(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            userService.createCustomer(new CustomerDto(null, customerDto.getEmail()));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        try {
            userService.createCustomer(new CustomerDto(customerDto.getUuId(), null));
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateCustomerWithExistingUuId() {
        // Test data
        final CustomerDto userDto = getServicesImplTestHelper().createCustomerDto();
        final Long existingCustomerId = 1L;
        final Customer existingCustomer = getServicesImplTestHelper().createCustomer();
        existingCustomer.setId(existingCustomerId);
        // Reset
        resetAll();
        // Expectations
        expect(userRepository.findByUuId(eq(userDto.getUuId()))).andReturn(existingCustomer).once();
        // Replay
        replayAll();
        // Run test scenario
        try {
            userService.createCustomer(userDto);
            fail("Exception should be thrown");
        } catch (final CustomerAlreadyExistsForUuIdException ex) {
            // Expected
            assertCustomerAlreadyExistsForUuIdException(ex, userDto.getUuId(), existingCustomerId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testCreateCustomer() {
        // Test data
        final CustomerDto userDto = getServicesImplTestHelper().createCustomerDto();
        // Reset
        resetAll();
        // Expectations
        expect(userRepository.findByUuId(eq(userDto.getUuId()))).andReturn(null).once();
        expect(userRepository.save(isA(Customer.class))).andAnswer(() -> (Customer) getCurrentArguments()[0]).once();
        // Replay
        replayAll();
        // Run test scenario
        final Customer result = userService.createCustomer(userDto);
        getServicesImplTestHelper().assertCustomer(result, userDto);
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerByIdWithInvalidArguments() {
        // Reset
        resetAll();
        // Replay
        replayAll();
        // Run test scenarios
        try {
            userService.getCustomerById(null);
            fail("Exception should be thrown");
        } catch (final IllegalArgumentException ex) {
            // Expected
        }
        // Verify
        verifyAll();

    }

    @Test
    public void testGetCustomerByIdWithNotExistingCustomer() {
        // Test data
        final Long userId = 1L;
        // Reset
        resetAll();
        // Expectations
        expect(userRepository.findOne(eq(userId))).andReturn(null).once();
        // Replay
        replayAll();
        // Run test scenarios
        try {
            userService.getCustomerById(userId);
            fail("Exception should be thrown");
        } catch (final CustomerNotFoundForIdException ex) {
            // Expected
            assertCustomerNotFoundForIdException(ex, userId);
        }
        // Verify
        verifyAll();
    }

    @Test
    public void testGetCustomerById() {
        // Test data
        final Long userId = 1L;
        final Customer user = getServicesImplTestHelper().createCustomer();
        user.setId(userId);
        // Reset
        resetAll();
        // Expectations
        expect(userRepository.findOne(eq(userId))).andReturn(user).once();
        // Replay
        replayAll();
        // Run test scenarios
        final Customer result = userService.getCustomerById(userId);
        assertEquals(user, result);
        // Verify
        verifyAll();
    }


    /* Utility methods */
    private void assertCustomerAlreadyExistsForUuIdException(final CustomerAlreadyExistsForUuIdException ex, final String uuId, final Long existingCustomerId) {
        assertEquals(uuId, ex.getUuId());
        assertEquals(existingCustomerId, ex.getExistingUserId());
    }

    private void assertCustomerNotFoundForIdException(final CustomerNotFoundForIdException ex, final Long id) {
        Assert.assertEquals(id, ex.getId());
        Assert.assertEquals(Customer.class, ex.getEntityClass());
    }

    private void assertCustomerNotFoundForUuidException(final CustomerNotFoundForUuidException ex, final String uuId) {
        Assert.assertEquals(uuId, ex.getUuId());
    }
}
