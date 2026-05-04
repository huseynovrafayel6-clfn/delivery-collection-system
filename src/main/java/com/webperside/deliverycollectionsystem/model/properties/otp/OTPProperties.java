package com.webperside.deliverycollectionsystem.model.properties.otp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@ConfigurationProperties("otp")
@Configuration
public class OTPProperties {

    OTPJwtData jwt;

}
