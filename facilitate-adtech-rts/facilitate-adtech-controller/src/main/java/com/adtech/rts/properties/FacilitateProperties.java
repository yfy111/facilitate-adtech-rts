package com.adtech.rts.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description 项目的环境变量
 */
@Component
@ConfigurationProperties(prefix = "facilitate")
@PropertySource(value = "config.properties")
@Data
public class FacilitateProperties {
    //#刘氓的application程序
    private String application_app;
    //#刘氓的session程序
    private String application_session;
    //#plus程序
    private String application_plus;

    //登录名
    private String userName;
    //密码
    private String passWord;
}
