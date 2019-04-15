/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: MybatisPlusConfig
 * Author:   yao
 * Date:     2019/1/22 16:29
 * Description: MybatisPlus配置类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cjw.springbootstarter.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 〈一句话功能简述〉<br>
 * 〈MybatisPlus配置类〉
 *
 * @author yao
 * @create 2019/1/22
 * @since 1.0.0
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.cjw.springbootstarter.mapper")
public class MybatisPlusConfig {
    /**
     * mybatis-plus分页插件<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
