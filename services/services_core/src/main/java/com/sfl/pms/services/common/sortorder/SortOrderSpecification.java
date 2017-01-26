package com.sfl.pms.services.common.sortorder;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/5/14
 * Time: 12:20 PM
 */
public class SortOrderSpecification<T extends SortColumn> implements Serializable {

    private static final long serialVersionUID = 7715089563340890469L;

    /* Properties */
    private final T sortColumn;

    private final SortDirection sortDirection;

    /* Constructors */
    public SortOrderSpecification(final T sortColumn, final SortDirection sortDirection) {
        Assert.notNull(sortColumn, "Sort order specification sort column should not be null.");
        Assert.notNull(sortDirection, "Sort order specification sort direction should not be null.");
        this.sortColumn = sortColumn;
        this.sortDirection = sortDirection;
    }

    /* Getters and setters */
    public T getSortColumn() {
        return sortColumn;
    }

    public SortDirection getSortDirection() {
        return sortDirection;
    }

    /* Public method overrides */
    public Sort constructSpringDataDomainSortCriteria() {
        final Sort.Direction direction;
        if (getSortDirection().equals(SortDirection.ASCENDING)) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }

        return new Sort(direction, sortColumn.getSpecification());
    }

    /* Equals, HashCode and ToString */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SortOrderSpecification)) {
            return false;
        }
        final SortOrderSpecification that = (SortOrderSpecification) o;
        final EqualsBuilder builder = new EqualsBuilder();
        builder.append(getSortDirection(), that.getSortDirection());
        builder.append(getSortColumn(), that.getSortColumn());
        return builder.build();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getSortDirection());
        builder.append(getSortColumn());
        return builder.build();
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this);
        builder.append("sortColumn", getSortDirection());
        builder.append("sortDirection", getSortColumn());
        return builder.build();
    }
}
