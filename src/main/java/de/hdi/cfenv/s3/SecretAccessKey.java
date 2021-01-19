package de.hdi.cfenv.s3;

public class SecretAccessKey {

    private final String value;

    public SecretAccessKey(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "SecretAccessKey=*******";
    }
}
