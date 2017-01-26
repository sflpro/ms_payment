package com.sfl.pms.externalclients.payment.adyen.model.recurring;

import com.sfl.pms.externalclients.payment.adyen.model.AbstractAdyenApiCommunicatorModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Ruben Dilanyan
 * Company: SFL LLC
 * Date: 7/4/15
 * Time: 10:41 AM
 */
public abstract class AbstractRecurringContractDetailsModel extends AbstractAdyenApiCommunicatorModel {

    private static final long serialVersionUID = -7329255447687378615L;

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRecurringContractDetailsModel.class);

    /* Constants */
    protected static final String DATETIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    /* Properties */
    private final String variant;

    private final Date creationDate;

    private final String detailReference;

    /* Constructors */
    public AbstractRecurringContractDetailsModel() {
        this.creationDate = null;
        this.variant = null;
        this.detailReference = null;
    }

    public AbstractRecurringContractDetailsModel(final String detailReference, final String variant, final Date creationDate) {
        this.variant = variant;
        this.creationDate = creationDate;
        this.detailReference = detailReference;
    }

    public AbstractRecurringContractDetailsModel(final AbstractRecurringContractDetailsBuilder builder) {
        this.variant = builder.getVariant();
        this.creationDate = builder.getCreationDate();
        this.detailReference = builder.getDetailReference();
    }

    /* Properties getters and setters */
    public String getVariant() {
        return variant;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getDetailReference() {
        return detailReference;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractRecurringContractDetailsModel)) {
            return false;
        }
        AbstractRecurringContractDetailsModel that = (AbstractRecurringContractDetailsModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.appendSuper(super.equals(that));
        builder.append(getVariant(), that.getVariant());
        builder.append(getDetailReference(), that.getDetailReference());
        builder.append(getCreationDate(), that.getCreationDate());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.appendSuper(super.hashCode());
        builder.append(getVariant());
        builder.append(getDetailReference());
        builder.append(getCreationDate());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.appendSuper(super.toString());
        builder.append("variant", getVariant());
        builder.append("detailReference", getDetailReference());
        builder.append("creationDate", getCreationDate());
        return builder.build();
    }

    /* Inner classes */
    public abstract static class AbstractRecurringContractDetailsBuilder<T extends AbstractRecurringContractDetailsBuilder> {

        /* Builder properties */
        private String variant;

        private String detailReference;

        private Date creationDate;

        /* Constructors */
        protected AbstractRecurringContractDetailsBuilder() {
        }

        /* builder methods */
        public T variant(final String variant) {
            this.variant = variant;
            return (T) this;
        }

        public T detailReference(final String detailReference) {
            this.detailReference = detailReference;
            return (T) this;
        }

        public T creationDateFromString(final String creationDate) {
            final DateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);
            try {
                this.creationDate = dateFormat.parse(creationDate);
            } catch (final ParseException e) {
                LOGGER.error("Unable to parse credit card creation date", e);
            }
            return (T) this;
        }

        /* Getters */
        public String getVariant() {
            return variant;
        }

        public String getDetailReference() {
            return detailReference;
        }

        public Date getCreationDate() {
            return creationDate;
        }
    }
}
