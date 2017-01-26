package com.sfl.pms.services.payment.redirect.dto;

import com.sfl.pms.services.common.dto.AbstractDomainEntityModelDto;
import com.sfl.pms.services.payment.provider.model.PaymentProviderType;
import com.sfl.pms.services.payment.redirect.model.PaymentProviderRedirectResult;
import org.springframework.util.Assert;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/11/15
 * Time: 11:42 AM
 */
public class PaymentProviderRedirectResultDto<T extends PaymentProviderRedirectResult> extends AbstractDomainEntityModelDto<T> {

    private static final long serialVersionUID = -133573332358113244L;

    /* Properties */
    private final PaymentProviderType type;

    /* Constructors */
    public PaymentProviderRedirectResultDto(final PaymentProviderType type) {
        this.type = type;
    }

    /* Properties getters and setters */
    public PaymentProviderType getType() {
        return type;
    }

    /* Public interface methods */
    @Override
    public void updateDomainEntityProperties(final T redirectResult) {
        Assert.notNull(redirectResult, "Redirect result should not be null");
    }
}
