__Attention: This library is no longer being actively developed!__

# java-cfenv-s3

This spring boot library allows easy access to the Cloud Foundry VCAP variables for an S3 bucket, without the values having to be stored as plain text.

## Use Case

A spring boot application in Cloud Foundry uses an S3 bucket to store data. The data can only be read and written once the application has been authenticated on the S3 bucket. The data for authentication can be easily determined with the help of this library.

**Limitation:** This library currently only supports one S3 bucket per app.

## HowTo

### Embed Maven-Dependency

The following dependency-Entry is required:

```xml
<dependency>
  <groupId>de.hdi.cfenv</groupId>
  <artifactId>java-cfenv-s3</artifactId>
  <version>1.0.2</version>
</dependency>
```

### Example

There are two options to gather the S3-Information

1. Using application Properties
2. Using the class ```CfEnvS3```

A bean of the type ```CfEnvS3``` must be created for both variants. To get an instance, a method annotated with ```@Bean``` is recommended, which must be passed an instance of ```org.springframework.core.env.Environment```:

```java
@Bean
public CfEnvS3 cfEnvS3(Environment environment) {
	return new CfEnvS3(environment);
}
```

#### Properties

The S3 information can be determined as usual using the following properties:

- ```cfenv.s3.accessKeyId```
- ```cfenv.s3.secretKey```
- ```cfenv.s3.region```
- ```cfenv.s3.bucket```

However, these properties must not be present in the application.yml, because the dynamically determined values ​​would be overwritten.

The S3 properties are then accessed via member variables or formal parameters that are annotated with ```@Value```:

```java
@Value("${cfenv.s3.accessKeyId}")
private String accessKeyId;

@Value("${cfenv.s3.secretKey}")
private String secretAccessKey;

@Value("${cfenv.s3.region}")
private String region;

@Value("${cfenv.s3.bucket}")
private String bucket;
```

#### CfEnvS3

Alternatively, you can also have an instance of the type ```CfEnvS3``` injected directly and access the S3 information via the getters:

- ```public String getRegion()```
- ```public String getAccessKeyId()```
- ```public SecretAccessKey getSecretAccessKey()```
- ```public String getBucket()```
