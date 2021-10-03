package com.justlife.casestudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Import({ BeanValidatorPluginsConfiguration.class, SpringDataRestConfiguration.class })
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).groupName("CASESTUDY").select()
                .paths(PathSelectors.any()).build()
                .apiInfo(apiInfo("Case Study API",
                        "This api shows the capabilities of REST requests in this project."));
    }

    private ApiInfo apiInfo(String title, String description) {

        return new ApiInfoBuilder().title(title).description(description).version("1.0.0-Snapshot").build();
    }
}