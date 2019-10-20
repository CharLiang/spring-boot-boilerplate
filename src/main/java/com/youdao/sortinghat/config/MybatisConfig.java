/**
 * @(#)MybatisConfig.java, 2018-11-02.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.config;

import com.github.pagehelper.PageHelper;
import com.youdao.sortinghat.util.MyMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tk.mybatis.mapper.autoconfigure.MapperProperties;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Properties;

;

/**
 * MybatisConfig
 *
 * @author 
 *
 */
@Configuration
@MapperScan(value = "com.youdao.sortinghat.dao.mysql.mapper", markerInterface = MyMapper.class)
public class MybatisConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * 持久层配置
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MapperProperties mapperProperties = new MapperProperties();
        mapperProperties.setNotEmpty(false);
        mapperProperties.setIdentity("MYSQL");

        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.setConfig(mapperProperties);

        tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();
        configuration.setMapperHelper(mapperHelper);
        // 懒加载
        configuration.setLazyLoadingEnabled(true);
        configuration.setAggressiveLazyLoading(false);
        configuration.setLazyLoadTriggerMethods(new HashSet<>());

        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setConfiguration(configuration);
        sessionFactory.setDataSource(dataSource);
        // 指定bean所在包
        sessionFactory.setTypeAliasesPackage("com.youdao.sortinghat.dao.mysql.model");
        // 指定映射文件
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*.xml"));
        return sessionFactory.getObject();
    }

    /**
     * 分页插件配置
     * @return
     */
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("reasonable", "false");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}