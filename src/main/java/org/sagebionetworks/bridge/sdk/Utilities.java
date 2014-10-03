package org.sagebionetworks.bridge.sdk;

import static org.apache.commons.validator.routines.UrlValidator.ALLOW_LOCAL_URLS;
import static org.apache.commons.validator.routines.UrlValidator.NO_FRAGMENTS;

import java.io.IOException;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Utilities {

    private static final String[] schemes = { "http", "https" };
    private static final UrlValidator urlValidator = new UrlValidator(schemes, NO_FRAGMENTS + ALLOW_LOCAL_URLS);
    private static final EmailValidator emailValidator = EmailValidator.getInstance();

    private static final ObjectMapper mapper = new ObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static Utilities INSTANCE = null;

    private Utilities() {}

    public static Utilities getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Utilities();
        }
        return INSTANCE;
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public boolean isValidEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null.");
        }
        return emailValidator.isValid(email);
    }

    public boolean isValidUrl(String url) {
        if (url == null) {
            throw new IllegalArgumentException("URL cannot be null.");
        }
        return urlValidator.isValid(url);
    }

    public boolean isConnectableUrl(String url, int timeout) {
        if (!isValidUrl(url)) {
            throw new IllegalArgumentException("URL is not a valid one: " + url);
        } else if (timeout <= 0 || 10 * 1000 <= timeout) {
            throw new IllegalArgumentException("timeout isn't in the valid range (0 < timeout < 10 minutes): "
                    + timeout);
        }
        url = url.replaceFirst("https", "http");
        try {
            Response response = Request.Head(url).connectTimeout(timeout).execute();
            int statusCode = response.returnResponse().getStatusLine().getStatusCode();
            return (200 <= statusCode && statusCode <= 399);
        } catch (IOException exception) {
            return false;
        }
    }
}