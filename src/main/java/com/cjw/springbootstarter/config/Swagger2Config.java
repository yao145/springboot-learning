/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: Swagger2Initialization
 * Author:   yao
 * Date:     2018/9/20 16:23
 * Description: swagger2的初始化操作
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.config;

import com.cjw.springbootstarter.base.ApplicationConstant;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 〈一句话功能简述〉<br>
 * 〈swagger2的初始化操作〉
 *
 * @author yao
 * @create 2018/9/20
 * @since 1.0.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Config implements WebMvcConfigurer {
    /**
     * 定义api组
     *
     * @return
     */
    @Bean
    public Docket enavApi() {
        // 返回一个Docket定义group的名字,apiinfo,及path  定义哪些路径的api在ui中显示出来
        return new Docket(DocumentationType.SWAGGER_2).groupName(ApplicationConstant.PROJECT_GROUPNAME).apiInfo(apiInfo()).select()
                //.apis(RequestHandlerSelectors.basePackage("net.xdclass.xdvideo.controller"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(ApplicationConstant.PROJECT_NAME).
                description(ApplicationConstant.PROJECT_DES).version(ApplicationConstant.PROJECT_VERSION).build();
    }

    @Value("${fileupload.folder.path}")
    private String FileUploadFolderPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("plot/uploadfiles/**")
                .addResourceLocations("file:" + FileUploadFolderPath + "plot/");
    }
}
