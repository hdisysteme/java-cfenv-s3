package de.hdi.cfenv.s3;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.core.CfService;
import io.pivotal.cfenv.spring.boot.CfEnvProcessorProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CfEnvS3ProcessorUnitTest {

    private static final String CF_ACCESS_KEY_ID = "access_key_id";
    private static final String CF_SECRET_ACCESS_KEY = "secret_access_key";
    private static final String CF_BUCKET = "bucket";
    private static final String CF_REGION = "region";

    private static final String PROP_ACCESS_KEY_ID = "cfenv.s3.accessKeyId";
    private static final String PROP_SECRET_ACCESS_KEY = "cfenv.s3.secretKey";
    private static final String PROP_BUCKET = "cfenv.s3.bucket";
    private static final String PROP_REGION = "cfenv.s3.region";

    @Mock
    private CfService cfService;

    @Mock
    private CfCredentials cfCredentials;

    @BeforeEach
    void setup() {
        openMocks(this);
    }

    @Test
    void testAcceptPathExistsByTagIgnoreCaseReturnsTrue() {
        when(cfService.existsByTagIgnoreCase("s3")).thenReturn(true);
        when(cfService.getLabel()).thenReturn("any Label");
        when(cfService.getName()).thenReturn("any Name");

        assertTrue(new CfEnvS3Processor().accept(cfService));
    }

    @Test
    void testAcceptPathGetLabelReturnsTrue() {
        when(cfService.existsByTagIgnoreCase("s3")).thenReturn(false);
        when(cfService.getLabel()).thenReturn("Label s3");
        when(cfService.getName()).thenReturn("any Name");

        assertTrue(new CfEnvS3Processor().accept(cfService));
    }

    @Test
    void testAcceptPathGetNameReturnsTrue() {
        when(cfService.existsByTagIgnoreCase("s3")).thenReturn(false);
        when(cfService.getLabel()).thenReturn("any Label");
        when(cfService.getName()).thenReturn("S3 Name");

        assertTrue(new CfEnvS3Processor().accept(cfService));
    }

    @Test
    void testAcceptAllPathsReturnFalse() {
        when(cfService.existsByTagIgnoreCase("s3")).thenReturn(false);
        when(cfService.getLabel()).thenReturn("any Label");
        when(cfService.getName()).thenReturn("S2 Name");

        assertFalse(new CfEnvS3Processor().accept(cfService));
    }

    @Test
    void testAcceptPathGetLabelReturnsNull() {
        when(cfService.existsByTagIgnoreCase("s3")).thenReturn(false);
        when(cfService.getLabel()).thenReturn(null);
        when(cfService.getName()).thenReturn("S2 Name");

        assertFalse(new CfEnvS3Processor().accept(cfService));
    }

    @Test
    void testAcceptPathGetNameReturnsNull() {
        when(cfService.existsByTagIgnoreCase("s3")).thenReturn(false);
        when(cfService.getLabel()).thenReturn("any Label");
        when(cfService.getName()).thenReturn(null);

        assertFalse(new CfEnvS3Processor().accept(cfService));
    }

    @Test
    void testProcess() {
        final String accessKeyId = "Access Key Id";
        final String secretAccessKeyId = "Secret Access Key Id";
        final String bucket = "Bucket";
        final String region = "region";
        when(cfCredentials.getString(CF_ACCESS_KEY_ID)).thenReturn(accessKeyId);
        when(cfCredentials.getString(CF_SECRET_ACCESS_KEY)).thenReturn(secretAccessKeyId);
        when(cfCredentials.getString(CF_BUCKET)).thenReturn(bucket);
        when(cfCredentials.getString(CF_REGION)).thenReturn(region);

        Map<String, Object> properties = new HashMap<>();
        new CfEnvS3Processor().process(cfCredentials, properties);

        assertEquals(accessKeyId, properties.get(PROP_ACCESS_KEY_ID));
        assertEquals(secretAccessKeyId, properties.get(PROP_SECRET_ACCESS_KEY));
        assertEquals(bucket, properties.get(PROP_BUCKET));
        assertEquals(region, properties.get(PROP_REGION));
    }

    @Test
    void testGetProperties() {
        CfEnvProcessorProperties properties = new CfEnvS3Processor().getProperties();
        assertEquals("cfenv.s3", properties.getPropertyPrefixes());
        assertEquals("s3", properties.getServiceName());
    }
}
