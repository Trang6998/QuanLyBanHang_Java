package nhom18.com.restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import nhom18.com.restful.config.FileStorageProperties;

@EnableConfigurationProperties({
    FileStorageProperties.class
})
@SpringBootApplication
public class SbMySqlRestfulApplication implements WebMvcConfigurer{
	
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
//        registry.addResourceHandler("/img/artists/**").addResourceLocations("/resources/img/artist/");
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
    }

	public static void main(String[] args) {
		SpringApplication.run(SbMySqlRestfulApplication.class, args);
	}

}
