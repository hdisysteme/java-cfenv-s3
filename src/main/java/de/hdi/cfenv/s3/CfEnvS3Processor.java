package de.hdi.cfenv.s3;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.core.CfService;
import io.pivotal.cfenv.spring.boot.CfEnvProcessor;
import io.pivotal.cfenv.spring.boot.CfEnvProcessorProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.Optional;

public class CfEnvS3Processor implements CfEnvProcessor {

    private static final String S3_STRING = "s3";
    private static final String CF_ACCESS_KEY_ID = "access_key_id";
    private static final String CF_SECRET_ACCESS_KEY = "secret_access_key";
    private static final String CF_BUCKET = "bucket";
    private static final String CF_REGION = "region";

    static final String PROP_ACCESS_KEY_ID = "cfenv.s3.accessKeyId";
    static final String PROP_SECRET_ACCESS_KEY = "cfenv.s3.secretKey";
    static final String PROP_BUCKET = "cfenv.s3.bucket";
    static final String PROP_REGION = "cfenv.s3.region";

    @Autowired
    private Environment env;

    @Override
    public boolean accept(CfService service) {
        final String empty = "";
        return service.existsByTagIgnoreCase(S3_STRING) ||
                Optional.ofNullable(service.getLabel()).orElse(empty).toLowerCase().contains(S3_STRING.toLowerCase()) ||
                Optional.ofNullable(service.getName()).orElse(empty).toLowerCase().contains(S3_STRING.toLowerCase());
    }

    @Override
    public void process(CfCredentials cfCredentials, Map<String, Object> properties) {
        properties.put(PROP_ACCESS_KEY_ID, cfCredentials.getString(CF_ACCESS_KEY_ID));
        properties.put(PROP_BUCKET, cfCredentials.getString(CF_BUCKET));
        properties.put(PROP_REGION, cfCredentials.getString(CF_REGION));
        properties.put(PROP_SECRET_ACCESS_KEY, cfCredentials.getString(CF_SECRET_ACCESS_KEY));
    }

    @Override
    public CfEnvProcessorProperties getProperties() {
        return CfEnvProcessorProperties.builder()
                .propertyPrefixes("cfenv.s3")
                .serviceName(S3_STRING)
                .build();
    }
}
