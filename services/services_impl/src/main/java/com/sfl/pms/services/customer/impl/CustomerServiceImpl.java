package com.sfl.pms.services.customer.impl;

import com.sfl.pms.persistence.repositories.customer.CustomerRepository;
import com.sfl.pms.services.customer.CustomerService;
import com.sfl.pms.services.customer.dto.CustomerDto;
import com.sfl.pms.services.customer.exception.CustomerAlreadyExistsForUuIdException;
import com.sfl.pms.services.customer.exception.CustomerNotFoundForIdException;
import com.sfl.pms.services.customer.exception.CustomerNotFoundForUuidException;
import com.sfl.pms.services.customer.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 10/01/14
 * Time: 10:18 PM
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    /* Properties */
    @Autowired
    private CustomerRepository userRepository;

    /* Constructors */
    public CustomerServiceImpl() {

    }

    /* Public methods */
    @Nonnull
    @Override
    public Customer getCustomerById(@Nonnull final Long id) {
        Assert.notNull(id, "User id should not be null");
        LOGGER.debug("Getting user for id - {}", id);
        final Customer user = userRepository.findOne(id);
        assertUserNotNullForId(user, id);
        LOGGER.debug("Successfully retrieved user for id - {}, user - {}", user.getId(), user);
        return user;
    }

    @Transactional
    @Nonnull
    @Override
    public Customer createCustomer(@Nonnull final CustomerDto userDto) {
        assertUserDto(userDto);
        LOGGER.debug("Creating user for DTO - {}", userDto);
        assertNoUserExistsForUuId(userDto.getUuId());
        // Create new user
        Customer user = new Customer();
        userDto.updateDomainEntityProperties(user);
        // Persist user
        user = userRepository.save(user);
        LOGGER.debug("Successfully created user with id - {}, user - {}", user.getId(), user);
        return user;
    }

    @Nonnull
    @Override
    public boolean checkIfCustomerExistsForUuId(@Nonnull final String uuId) {
        assertUserUuIdNotNull(uuId);
        LOGGER.debug("Checking if user exists for uuid - {}", uuId);
        final Customer user = userRepository.findByUuId(uuId);
        final boolean exists = (user != null);
        LOGGER.debug("Check if user exists for uuid - {} is - {}", uuId, exists);
        return exists;
    }

    @Nonnull
    @Override
    public Customer getCustomerByUuId(@Nonnull final String uuId) {
        assertUserUuIdNotNull(uuId);
        LOGGER.debug("Getting user for uuid - {}", uuId);
        final Customer user = userRepository.findByUuId(uuId);
        assertUserNotNullForUuId(user, uuId);
        LOGGER.debug("Successfully retrieved user for uuid - {}, user - {}", uuId, user);
        return user;
    }

    @Transactional
    @Nonnull
    @Override
    public Customer getOrCreateCustomerForUuId(final CustomerDto customerDto) {
        assertUserDto(customerDto);
        LOGGER.debug("Getting or creating user for DTO - {}", customerDto);
        Customer user = userRepository.findByUuId(customerDto.getUuId());
        if (user == null) {
            LOGGER.debug("No user was found for uuid - {}, creating new one", customerDto.getUuId());
            user = createCustomer(customerDto);
        } else {
            LOGGER.debug("User for uuid already exists, using it, uuid - {}, user - {}", customerDto.getUuId(), user);
        }
        return user;
    }

    /* Utility methods */
    private void assertUserUuIdNotNull(final String uuId) {
        Assert.notNull(uuId, "User uuid should not be null");
    }

    private void assertNoUserExistsForUuId(final String uuId) {
        final Customer user = userRepository.findByUuId(uuId);
        if (user != null) {
            LOGGER.error("User with id -{} already exists for UUID - {}", user.getId(), user.getUuId());
            throw new CustomerAlreadyExistsForUuIdException(uuId, user.getId());
        }
    }

    private void assertUserDto(@Nonnull final CustomerDto userDto) {
        Assert.notNull(userDto, "User DTO should not be null");
        Assert.notNull(userDto.getUuId(), "UUID in User DTO should not be null");
        Assert.notNull(userDto.getEmail(), "Email in User DTO should not be null");
    }

    private void assertUserNotNullForId(final Customer user, final Long id) {
        if (user == null) {
            LOGGER.error("No user was found for id - {}", id);
            throw new CustomerNotFoundForIdException(id);
        }
    }

    private void assertUserNotNullForUuId(final Customer user, final String uuId) {
        if (user == null) {
            LOGGER.error("No user was found for uuid - {}", uuId);
            throw new CustomerNotFoundForUuidException(uuId);
        }
    }

    /* Properties getters and setters */
    public CustomerRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(final CustomerRepository userRepository) {
        this.userRepository = userRepository;
    }
}
