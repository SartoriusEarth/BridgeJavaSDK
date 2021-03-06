package org.sagebionetworks.bridge.sdk.models.holders;

import org.joda.time.DateTime;

public interface GuidCreatedOnVersionHolder {
    public String getGuid();
    public DateTime getCreatedOn();
    public Long getVersion();
}
