/**
 * @(#)DruidDataSourceConfiguration.java, 2018-03-05.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Druid连接池配置
 *
 * @author 
 *
 */
@Configuration
public class DruidConfig {

    /**
     * 配置连接池
     * @return
     */
    @Bean(destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        // 初始化大小，最小，最大
        druidDataSource.setInitialSize(5);
        druidDataSource.setMinIdle(5);
        druidDataSource.setMaxActive(20);
        // 配置获取连接等待超时的时间
        druidDataSource.setMaxWait(60000);
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("SELECT 1 FROM DUAL");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        // 打开PSCache，并且指定每个连接上PSCache的大小
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        // 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
        druidDataSource.setFilters("stat,wall,slf4j");
        // 通过connectProperties属性来打开mergeSql功能；慢SQL记录
        druidDataSource.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");
        // 合并多个DruidDataSource的监控数据
        // druidDataSource.setUseGlobalDataSourceStat(true);
        return druidDataSource;
    }

    /**
     * 配置Filter 配合druid监控使用
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }

    /**
     * 配置druid监控页面
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        servletRegistrationBean.addInitParameter("allow", "220.181.102.181,127.0.0.1"); // IP白名单(没有配置或者为空，则允许所有访问)
//        servletRegistrationBean.addInitParameter("deny", "192.168.16.111"); // IP黑名单(存在共同时，deny优先于allow)
        servletRegistrationBean.addInitParameter("loginUsername", "huihui"); // 用户名
        servletRegistrationBean.addInitParameter("loginPassword", "t4ed6p@h@u)i!h*u$i!6"); // 密码
        servletRegistrationBean.addInitParameter("resetEnable", "false"); // 禁用HTML页面上的“Reset All”功能
        return servletRegistrationBean;
    }

    /**
     * 配置druid的统计标准 貌似与druid配置文件里的
     * spring.datasource.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000效果一样
     * https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_StatFilter
     * @return
     */
//    @Bean
//    public StatFilter statFilter(){
//        StatFilter statFilter = new StatFilter();
//        statFilter.setLogSlowSql(true); // slowSqlMillis用来配置SQL慢的标准，执行时间超过slowSqlMillis的就是慢。
//        statFilter.setMergeSql(true); // SQL合并配置
//        statFilter.setSlowSqlMillis(1000); // slowSqlMillis的缺省值为3000，也就是3秒。
//        return statFilter;
//    }

    /**
     * druid的防火墙配置 防止sql注入等
     * https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter
     * @return
     */
    @Bean
    public WallFilter wallFilter(){
        WallFilter wallFilter = new WallFilter();
        // 允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true); // 解决批量查询报错的问题
        wallFilter.setConfig(config);
        return wallFilter;
    }
}