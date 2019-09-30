package proj.skb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import proj.skb.ProjSkbConstants
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import java.util.Collections

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("proj.skb"))
            .paths(PathSelectors.any())
            .build()
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo())
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            "SKB Cache API",
            "API for handling REST access to the cache",
            ProjSkbConstants.API_VERSION,
            "Terms of service URL",
            Contact("Sergey Okatov", "https://github.com/svok", "sokatov@gmail.com"),
            "MIT",
            "https://opensource.org/licenses/MIT",
            emptyList()
        )
    }

}
