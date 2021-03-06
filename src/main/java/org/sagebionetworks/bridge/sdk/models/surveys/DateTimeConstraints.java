package org.sagebionetworks.bridge.sdk.models.surveys;

import org.joda.time.DateTime;


public class DateTimeConstraints extends Constraints {

    private boolean allowFuture = false;
    private DateTime earliestValue;
    private DateTime latestValue;

    public DateTimeConstraints() {
        setDataType(DataType.DATETIME);
    }

    public boolean getAllowFuture() {
        return allowFuture;
    }
    public void setAllowFuture(boolean allowFuture) {
        this.allowFuture = allowFuture;
    }
    public DateTime getEarliestValue() {
        return earliestValue;
    }
    public void setEarliestValue(DateTime earliestValue) {
        this.earliestValue = earliestValue;
    }
    public DateTime getLatestValue() {
        return latestValue;
    }
    public void setLatestValue(DateTime latestValue) {
        this.latestValue = latestValue;
    }
}
