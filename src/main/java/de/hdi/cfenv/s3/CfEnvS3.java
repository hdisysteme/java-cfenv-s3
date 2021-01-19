package de.hdi.cfenv.s3;

import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

public class CfEnvS3 {

    private final Environment environment;

    public CfEnvS3(Environment environment) {
        Assert.notNull(environment, "Environment must not be null!");
        this.environment = environment;
    }

    public String getRegion() {
        return this.getPropertyValue(CfEnvS3Processor.PROP_REGION);
    }

    public String getAccessKeyId() {
        return this.getPropertyValue(CfEnvS3Processor.PROP_ACCESS_KEY_ID);
    }

    public SecretAccessKey getSecretAccessKey() {
        return new SecretAccessKey(this.getPropertyValue(CfEnvS3Processor.PROP_SECRET_ACCESS_KEY));
    }

    public String getBucket() {
        return this.getPropertyValue(CfEnvS3Processor.PROP_BUCKET);
    }

    private String getPropertyValue(final String key) {
        return environment.getProperty(key);
    }

    public String toString() {
        return String.format("CfEnvS3(region=%s, accessKeyId=%s, %s, bucket=%s)",
                this.getRegion(), this.getAccessKeyId(), this.getSecretAccessKey() ,this.getBucket());
    }
}
