package org.sagebionetworks.bridge.sdk.exceptions;

@SuppressWarnings("serial")
public class BridgeServerException extends RuntimeException {

    private final int statusCode;
    private final String endpoint;

    public BridgeServerException(String message, Throwable throwable, String endpoint) {
        super(message, throwable);
        this.statusCode = 500;
        this.endpoint = endpoint;
    }

    public BridgeServerException(String message, int statusCode, String endpoint) {
        super(message);
        this.statusCode = statusCode;
        this.endpoint = endpoint;
    }

    public BridgeServerException(Throwable t, int statusCode, String endpoint) {
        super(t.getMessage(), t);
        this.statusCode = statusCode;
        this.endpoint = endpoint;
    }

    public final int getStatusCode() {
        return this.statusCode;
    }

    public final String getRestEndpoint() {
        return endpoint;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endpoint == null) ? 0 : endpoint.hashCode());
        result = prime * result + statusCode;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BridgeServerException other = (BridgeServerException) obj;
        if (endpoint == null) {
            if (other.endpoint != null)
                return false;
        } else if (!endpoint.equals(other.endpoint))
            return false;
        if (statusCode != other.statusCode)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BridgeServerException[message=" + getMessage() + ", statusCode=" + getStatusCode() + ", endpoint="
                + getRestEndpoint() + "]";
    }
}
