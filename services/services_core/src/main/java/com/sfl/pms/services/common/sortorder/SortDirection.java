package com.sfl.pms.services.common.sortorder;

/**
 * User: Mher Sargsyan
 * Company: SFL LLC
 * Date: 12/5/14
 * Time: 12:24 PM
 */
public enum SortDirection {

    /**
     * ascending sort order
     */
    ASCENDING("asc"),
    /**
     * descending sort order
     */
    DESCENDING("desc");

    private String shortName;

    SortDirection(String shortName) {
        this.shortName = shortName;
    }

    /* Getters and setters */
    public String getShortName() {
        return shortName;
    }
}
