package com.meiyou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@MapperScan("com.meiyou.mapper")
@EnableCaching //hzy,启用Redis缓存
public class MeiyouApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeiyouApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize("204800KB");
        /// 总上传数据大小
        factory.setMaxRequestSize("204800KB");
        return factory.createMultipartConfig();
    }


}
