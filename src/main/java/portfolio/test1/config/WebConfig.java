
package portfolio.test1.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${save.path}")
    private String path; // 실제 파일 경로

    private String resourcePath = "/upload/**"; // view에서 접근할 경로

    private String savePath; // 실제 파일 저장 경로


    @PostConstruct
    public void init() {
        savePath = "file:" + path;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePath);
        registry.addResourceHandler("/thumb/**")
                .addResourceLocations("file:"+ path + "Thumb/"

                );

    }
}
