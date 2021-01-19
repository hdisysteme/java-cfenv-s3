package de.hdi.cfenv.s3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CfEnvS3UnitTest {

    private static final String REGION = "Region 01";
    private static final String ACCESS_KEY_ID = "1234567";
    private static final String SECRET_ACCESS_KEY = "89101112";
    private static final String BUCKET = "Bucket 01";

    @Mock
    private Environment environment;

    @InjectMocks
    private CfEnvS3 cfEnvS3;

    @BeforeEach
    void setup() {
        initMocks(this);
        this.prepareEnvironment();
    }

    @Test
    void testGetRegion() {
        assertEquals(REGION, cfEnvS3.getRegion());
    }

    @Test
    void testGetAccessKeyId() {
        assertEquals(ACCESS_KEY_ID, cfEnvS3.getAccessKeyId());
    }

    @Test
    void testGetSecretAccessKey() {
        assertEquals(SECRET_ACCESS_KEY, cfEnvS3.getSecretAccessKey().getValue());
    }

    @Test
    void testGetBucket() {
        assertEquals(BUCKET, cfEnvS3.getBucket());
    }

    @Test
    void testToStringSecretAccessKeyNotVisible() {
        assertTrue(cfEnvS3.toString().contains("SecretAccessKey=*******"));
    }

    private void prepareEnvironment() {
        when(environment.getProperty(CfEnvS3Processor.PROP_REGION)).thenReturn(REGION);
        when(environment.getProperty(CfEnvS3Processor.PROP_ACCESS_KEY_ID)).thenReturn(ACCESS_KEY_ID);
        when(environment.getProperty(CfEnvS3Processor.PROP_SECRET_ACCESS_KEY)).thenReturn(SECRET_ACCESS_KEY);
        when(environment.getProperty(CfEnvS3Processor.PROP_BUCKET)).thenReturn(BUCKET);
    }

}
