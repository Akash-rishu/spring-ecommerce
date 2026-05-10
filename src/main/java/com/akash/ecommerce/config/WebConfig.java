package com.akash.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
<<<<<<< HEAD
                .addResourceLocations("file:uploads/");
=======
                .addResourceLocations("file:///D:/project/ecommerce/uploads/");
>>>>>>> b654978 (My code)
    }
}