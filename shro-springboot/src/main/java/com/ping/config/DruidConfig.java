package com.ping.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    //配置Druid的监控
    //1.配置一个管理后台的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> initParams = new HashMap<>();
        // 控制台管理用户名
        initParams.put("loginUsername","admin");
        // 控制台管理密码
        initParams.put("loginPassword","123456");
        // 白名单,多个用逗号分割， 如果allow没有配置或者为空，则允许所有访问
        initParams.put("allow","");
        // 黑名单,多个用逗号分割 (共同存在时，deny优先于allow)
        initParams.put("deny","192.168.15.21");
        // 是否可以重置数据源，禁用HTML页面上的“Reset All”功能
        initParams.put("resetEnable","false");
        bean.setInitParameters(initParams);
        return bean;
    }
    //2.配置一个web监控的filter
    @Bean
    public FilterRegistrationBean ebStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean() ;
        filterRegistrationBean.setFilter(new WebStatFilter());
        //所有请求进行监控处理
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.css,/druid/*");
        return filterRegistrationBean ;
    }
}