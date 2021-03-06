package org.sagebionetworks.bridge.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class ConfigTest {

    @Test
    public void createConfig() {
        Config conf = Config.valueOf();
        assertNotNull(conf);
        assertEquals("conf returns values", "/api/v1/healthdata/asdf", conf.getHealthDataTrackerApi("asdf"));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void configChecksArguments() {
        Config conf = Config.valueOf();
        conf.getHealthDataTrackerApi(null);
    }
    
}
