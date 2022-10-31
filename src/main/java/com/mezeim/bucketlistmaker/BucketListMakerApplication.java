package com.mezeim.bucketlistmaker;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@OpenAPIDefinition(info = @Info(title = "BucketList API", description = "BucketList Information"))
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class})
public class BucketListMakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BucketListMakerApplication.class, args);
    }

}
