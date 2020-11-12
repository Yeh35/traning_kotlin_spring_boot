package me.sangoh.training.kotlin_spring_boot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("me.sangoh.training.kotlin_spring_boot"))
                .build()
                .useDefaultResponseMessages(false)
    }

    private fun swaggerInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("Spring API Documentation")
                .description("앱 개발시 사용되는 서버 API에 대한 연동 문서입니다.")
                .license("sangoh")
                .licenseUrl("https://github.com/Yeh35/traning_kotlin_spring_boot")
                .version("1")
                .build()
    }

}