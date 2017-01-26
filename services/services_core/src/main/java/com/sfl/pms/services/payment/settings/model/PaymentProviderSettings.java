package com.sfl.pms.services.payment.settings.model;

import com.sfl.pms.services.common.model.AbstractDomainEntityModel;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.system.environment.model.EnvironmentType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 12/23/14
 * Time: 9:40 AM
 */
@Entity
@Table(name = "payment_provider_settings", uniqueConstraints = {@UniqueConstraint(name = "UK_payment_provider_environment", columnNames = {"type", "environment_type"})}, indexes = {@Index(name = "IDX_payment_provider_settings_type", columnList = "type"), @Index(name = "IDX_payment_provider_settings_environment_type", columnList = "environment_type")})
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class PaymentProviderSettings extends AbstractDomainEntityModel {
    private static final long serialVersionUID = 1455411837447644124L;

    /* Properties */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    private PaymentProviderType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "environment_type", nullable = false)
    private EnvironmentType environmentType;

    @Column(name = "username", nullable = false, unique = false)
    private String userName;

    @Column(name = "password", nullable = false, unique = false)
    private String password;

    @Column(name = "notifications_token", nullable = false)
    private String notificationsToken;

    /* Constructors */
    public PaymentProviderSettings() {
    }

    /* Properties getters and setters */
    public PaymentProviderType getType() {
        return type;
    }

    public void setType(final PaymentProviderType type) {
        this.type = type;
    }

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

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentProviderSettings)) {
            return false;
        }
        final PaymentProviderSettings that = (PaymentProviderSettings) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(this.getType(), that.getType());
        builder.append(this.getEnvironmentType(), that.getEnvironmentType());
        builder.append(this.getUserName(), that.getUserName());
        builder.append(this.getPassword(), that.getPassword());
        builder.append(this.getNotificationsToken(), that.getNotificationsToken());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(this.getType());
        builder.append(this.getEnvironmentType());
        builder.append(this.getUserName());
        builder.append(this.getPassword());
        builder.append(this.getNotificationsToken());
        return builder.build();
    }


    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("type", this.getType());
        builder.append("environmentType", this.getEnvironmentType());
        builder.append("userName", this.getUserName());
        builder.append("password", "****************");
        builder.append("notificationsToken", this.getNotificationsToken());
        return builder.build();
    }
}
