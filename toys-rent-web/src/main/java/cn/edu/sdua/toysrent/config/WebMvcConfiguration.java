package cn.edu.sdua.toysrent.config;


import cn.edu.sdua.toysrent.interceptor.AuthenticationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;


    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        log.info("开始添加拦截器...");
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api/login/**",
                        "/api/image/**",
                        "/login/**",
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources",
                        "/v3/api-docs/**");
    }

    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射...");
        // Knife4j 的文档页面资源
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        // 添加默认静态资源路径（解决 favicon.ico 404 问题）
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/public/", "classpath:/resources/");
    }

}
