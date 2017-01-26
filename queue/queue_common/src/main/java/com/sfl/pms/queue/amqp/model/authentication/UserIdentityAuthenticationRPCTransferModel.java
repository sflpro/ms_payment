package com.sfl.pms.queue.amqp.model.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 3/18/15
 * Time: 6:20 PM
 */
public class UserIdentityAuthenticationRPCTransferModel extends AbstractRPCTransferModel {
    private static final long serialVersionUID = 3286597177478371155L;

    /* Properties */
    @JsonProperty(value = "userIdentityAuthenticationId", required = true)
    private Long userIdentityAuthenticationId;

    /* Constructors */
    public UserIdentityAuthenticationRPCTransferModel(final Long userIdentityAuthenticationId) {
        this.userIdentityAuthenticationId = userIdentityAuthenticationId;
    }

    public UserIdentityAuthenticationRPCTransferModel() {
    }

    /* Properties getters and setters */
    public Long getUserIdentityAuthenticationId() {
        return userIdentityAuthenticationId;
    }

    public void setUserIdentityAuthenticationId(final Long userIdentityAuthenticationId) {
        this.userIdentityAuthenticationId = userIdentityAuthenticationId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserIdentityAuthenticationRPCTransferModel)) {
            return false;
        }
        final UserIdentityAuthenticationRPCTransferModel that = (UserIdentityAuthenticationRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getUserIdentityAuthenticationId(), that.getUserIdentityAuthenticationId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getUserIdentityAuthenticationId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("userIdentityAuthenticationId", getUserIdentityAuthenticationId());
        return builder.build();
    }
}
