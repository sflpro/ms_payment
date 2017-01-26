package com.sfl.pms.services.customer.model;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 10/30/14
 * Time: 9:01 AM
 */
@Entity
@Table(name = "customer")
public class Customer extends AbstractDomainEntityModel {

    private static final long serialVersionUID = 4537854801373037849L;

    /* Properties */
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuId;

    @Column(name = "email", nullable = false)
    private String email;

    /* Constructors */
    public Customer() {
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

    /* Hash code and equals */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        final Customer user = (Customer) o;
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
