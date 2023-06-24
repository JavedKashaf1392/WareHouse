package in.nit.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("in.nit.controller.rest"))
				.paths(PathSelectors.regex("/rest.*"))
				.build()
				.apiInfo(getApiInfo());
				
	}

	@SuppressWarnings("rawtypes")
	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"WAREHOUSE APP",
				"ABSOFT WH APP ", 
				"2.3", 
				"http://absoft.com/", 
				new Contact("Mr.Abdula","http://absoft.com/","abdula@gmail.com"), 
				"AB LICENSE",
				"http://absoft.com/",
				new ArrayList<VendorExtension>());
	}
	
}
