package com.sfl.pms.queue.amqp.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sfl.pms.queue.amqp.model.AbstractRPCTransferModel;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * User: Alfred Kaghyan
 * Company: SFL LLC
 * Date: 6/18/2015
 * Time: 6:43 PM
 */
public class ReportRPCTransferModel extends AbstractRPCTransferModel {

    private static final long serialVersionUID = -1871880195685619408L;

    /* Properties */
    @JsonProperty(value = "reportId", required = true)
    private Long reportId;

    /* Constructors */
    public ReportRPCTransferModel() {
    }

    public ReportRPCTransferModel(final Long reportId) {
        this.reportId = reportId;
    }

    /* Properties getters and setters */
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(final Long reportId) {
        this.reportId = reportId;
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReportRPCTransferModel)) {
            return false;
        }
        final ReportRPCTransferModel that = (ReportRPCTransferModel) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getReportId(), that.getReportId());
        return builder.isEquals();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getReportId());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("reportId", getReportId());
        return builder.build();
    }
}
