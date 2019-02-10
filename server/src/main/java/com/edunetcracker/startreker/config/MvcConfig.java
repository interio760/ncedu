package com.edunetcracker.startreker.config;

import com.edunetcracker.startreker.dao.RoleDAO;
import com.edunetcracker.startreker.dao.UserDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/*")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists()
                                && requestedResource.isReadable()
                                ? requestedResource
                                : new ClassPathResource("/static/index.html");
                    }
                });
        registry.setOrder(Ordered.LOWEST_PRECEDENCE);
    }

    @Bean(name="UserDetailsServiceImpl")
    public UserDetailsService clazzImplA(){
        return new UserDAO(new RoleDAO());
    }

}
