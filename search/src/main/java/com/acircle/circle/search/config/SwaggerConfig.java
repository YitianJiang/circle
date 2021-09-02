package com.acircle.circle.search.config;

import com.acircle.circle.common.config.BaseSwaggerConfig;
import com.acircle.circle.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created by macro on 2018/4/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.acircle.circle.search.controller")
                .title("circle搜索系统")
                .description("circle搜索相关接口文档")
                .contactName("circle")
                .version("1.0")
                .enableSecurity(false)
                .build();
    }
}
