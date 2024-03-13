package pe.mil.ejercito.microservice.components.configuration;

import com.bxcode.tools.loader.constants.CategoryRestConstant;
import com.bxcode.tools.loader.dto.base.ResponseBase;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import pe.mil.ejercito.microservice.components.properties.DataSourceProperties;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * MicroservicesConfiguration
 * <p>
 * MicroservicesConfiguration class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 25/02/2024
 */
@Configuration
@EnableSwagger2
@EnableOpenApi
@EnableWebFlux
@EnableAutoConfiguration(exclude = {WebMvcAutoConfiguration.class})
public class MicroservicesConfiguration implements WebFluxConfigurer {

    private final TypeResolver typeResolver;
    private final String appName;
    private final String appVersion;

    @Autowired
    public MicroservicesConfiguration(TypeResolver typeResolver,
                                      @Value("${spring.application.name}") String appName,
                                      @Value("${spring.application.version}") String appVersion) {
        this.typeResolver = typeResolver;
        this.appName = appName;
        this.appVersion = appVersion;
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("microservice-api")
                .useDefaultResponseMessages(false)
                .additionalModels(
                        typeResolver.resolve(ResponseBase.class)
                )
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("pe.mil.ejercito.microservice.controllers"))
                .build()
                .ignoredParameterTypes(ApiIgnore.class)
                .enableUrlTemplating(true)
                .tags(
                        new Tag(CategoryRestConstant.CATEGORY_GENERAL, CategoryRestConstant.CATEGORY_GENERAL_DESCRIPTION),
                        new Tag(CategoryRestConstant.CATEGORY_BUSINESS, CategoryRestConstant.CATEGORY_BUSINESS_DESCRIPTION)
                );
    }

    @Bean
    public UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayOperationId(false)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .defaultModelRendering(ModelRendering.EXAMPLE)
                .displayRequestDuration(false)
                .docExpansion(DocExpansion.NONE)
                .filter(false)
                .maxDisplayedTags(null)
                .operationsSorter(OperationsSorter.ALPHA)
                .showExtensions(false)
                .showCommonExtensions(false)
                .tagsSorter(TagsSorter.ALPHA)
                .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                .validatorUrl(null)
                .build();
    }

    private ApiInfo apiInfo() {
        final String URL = "localhost";
        return new ApiInfoBuilder()
                .version(appName)
                .title(appVersion)
                .contact(new Contact("bxcode Inc.", URL, "bxcode@gmail.com"))
                .license("bxcode Inc.")
                .licenseUrl(URL)
                .build();
    }

    @Bean
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

}