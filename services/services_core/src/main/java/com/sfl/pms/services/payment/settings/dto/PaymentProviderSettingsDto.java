package com.sfl.pms.services.payment.settings.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.settings.model.PaymentProviderSettings;
import com.sfl.pms.services.system.environment.model.EnvironmentType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 1/6/15
 * Time: 1:31 PM
 */
public abstract class PaymentProviderSettingsDto<T extends PaymentProviderSettings> extends AbstractDomainEntityModelDto<T> {
    private static final long serialVersionUID = 1165925966269484588L;

    /* Properties */
    private EnvironmentType environmentType;

    private String userName;

    private String password;

    private String notificationsToken;

    /* Constructors */
    public PaymentProviderSettingsDto() {
    }

    public PaymentProviderSettingsDto(final EnvironmentType environmentType, final String userName, final String password, final String notificationsToken) {
        this.environmentType = environmentType;
        this.userName = userName;
        this.password = password;
        this.notificationsToken = notificationsToken;
    }

    /* Properties getters and setters */
    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(final EnvironmentType environmentType) {
        this.environmentType = environmentType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getNotificationsToken() {
        return notificationsToken;
    }

    public void setNotificationsToken(final String notificationsToken) {
        this.notificationsToken = notificationsToken;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T settings) {
        settings.setEnvironmentType(getEnvironmentType());
        settings.setUserName(getUserName());
        settings.setPassword(getPassword());
        settings.setNotificationsToken(getNotificationsToken());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProviderSettingsDto)) {
            return false;
        }
        final PaymentProviderSettingsDto that = (PaymentProviderSettingsDto) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getEnvironmentType(), that.getEnvironmentType());
        builder.append(this.getUserName(), that.getUserName());
        builder.append(this.getPassword(), that.getPassword());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getEnvironmentType());
        builder.append(this.getUserName());
        builder.append(this.getPassword());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("environmentType", this.getEnvironmentType());
        builder.append("userName", this.getUserName());
        builder.append("password", "****************");
        return builder.build();
    }

}
