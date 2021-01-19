package de.hdi.cfenv.s3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecretAccessKeyUnitTest {


    @Test
    void testGetValue() {
        final String secretValue = "mySecretValue";
        assertEquals(secretValue, new SecretAccessKey(secretValue).getValue());
    }

    @Test
    void testToString() {
        final String secretValue = "mySecretValue";
        assertEquals("SecretAccessKey=*******", new SecretAccessKey(secretValue).toString());
    }

}
