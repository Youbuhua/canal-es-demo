package canal.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.elasticsearch.xpack.sql.jdbc.EsDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Configuration
    @MapperScan(basePackages = "canal.dao.elasticsearch", sqlSessionFactoryRef = "elasticsearchSqlSessionFactory")
    static class ElasticsearchMyBatisConfig {

        @Bean("elasticsearchDataSource")
        @ConfigurationProperties(prefix = "spring.elasticsearch.datasource")
        public DataSource igniteDataSource(Environment environment) {
            return new EsDataSource();
        }

        @Bean("elasticsearchSqlSessionFactory")
        public SqlSessionFactory elasticsearchSqlSessionFactory(DataSource elasticsearchDataSource) throws Exception {
            SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
            factoryBean.setDataSource(elasticsearchDataSource);
            factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mybatis/mapper/elasticsearch/*.xml"));
            return factoryBean.getObject();
        }
    }

}
