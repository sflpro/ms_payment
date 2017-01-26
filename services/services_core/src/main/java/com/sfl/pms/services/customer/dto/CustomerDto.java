package com.sfl.pms.services.customer.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.customer.model.Customer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/9/16
 * Time: 7:08 PM
 */
public class CustomerDto extends AbstractDomainEntityModelDto<Customer> {

    /* Properties */
    private String uuId;

    private String email;

    /* Constructors */
    public CustomerDto() {
    }

    public CustomerDto(final String uuId, final String email) {
        this.uuId = uuId;
        this.email = email;
    }

    /* Properties getters and setters */
    public String getUuId() {
        return uuId;
    }

    public void setUuId(final String uuId) {
        this.uuId = uuId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    /* Utility methods */
    @Override
    public void updateDomainEntityProperties(final Customer user) {
        Assert.notNull(user, "User should not be null");
        user.setUuId(uuId);
        user.setEmail(email);
    }

    /* Hash code and equals */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerDto)) {
            return false;
        }
        final CustomerDto user = (CustomerDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(o));
        builder.append(getUuId(), user.getUuId());
        builder.append(getEmail(), user.getEmail());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getUuId());
        builder.append(getEmail());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("uuId", getUuId());
        builder.append("email", getEmail());
        return builder.build();
    }


}
