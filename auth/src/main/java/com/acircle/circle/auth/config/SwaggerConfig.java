package com.acircle.circle.auth.config;

import com.acircle.circle.common.config.BaseSwaggerConfig;
import com.acircle.circle.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.acircle.circle.auth.controller")
                .title("circle认证中心")
                .description("circle认证中心相关接口文档")
                .contactName("acircle")
                .version("1.0.0")
                .enableSecurity(true)
                .build();
    }
}
