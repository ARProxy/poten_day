package com.example.config;

import com.example.jwt.JwtTokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.*;

@Configuration

@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtTokenInterceptor jwtTokenInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("인터셉터 등록");
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/v3/**")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/api/user/signUp")
                .excludePathPatterns("/api/user/signIn")
                .excludePathPatterns("/api/board/category")
                .excludePathPatterns("/api/board/saveMyCategories")
                .excludePathPatterns("/api/board/myCategories")
                .excludePathPatterns("/api/board/deleteCategories")
                .excludePathPatterns("/api/board/skin")
                .excludePathPatterns("/api/board/savePostIt");
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}