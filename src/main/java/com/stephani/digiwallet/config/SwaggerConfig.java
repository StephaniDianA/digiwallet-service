package com.stephani.digiwallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    public static final String ELASTIC_CONTROLLER = "elastic-controller";
    public static final String USER_CONTROLLER = "user-controller";
    public static final String ORDER_CONTROLLER = "order-controller";

    @Bean
    public Docket digiwalletApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.stephani.digiwallet.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag(ELASTIC_CONTROLLER, "Elasticsearch service endpoint"))
                .tags(new Tag(USER_CONTROLLER, "User service endpoint"))
                .tags(new Tag(ORDER_CONTROLLER, "Order service endpoint"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Digiwallet API")
                .version("1.0")
                .contact(new Contact("Stephani Dian Angelina", "https://linkedin.com/stephaniangelina", "stephani.angelina91@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .description("Digiwallet API")
                .build();
    }
}
