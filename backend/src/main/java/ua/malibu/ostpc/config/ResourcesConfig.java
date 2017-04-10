package ua.malibu.ostpc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@Configuration
public class ResourcesConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("src/main/");
        registry.addResourceHandler("/resources/css/**").addResourceLocations("src/main/resources");
        registry.addResourceHandler("/resources/js/**").addResourceLocations("src/main/resources");
        registry.addResourceHandler("/resources/fonts/**").addResourceLocations("src/main/resources");
    }
}
