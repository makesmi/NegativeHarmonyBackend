
package harmony.config;

import harmony.services.RunEnvironment;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Profile({"dev", "prod"})
public class MySqlConfig {
    @Autowired
    private RunEnvironment environment;
    
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/muurahainen?useSSL=false&serverTimezone=UTC");
        dataSource.setUsername("muurahainen");
        dataSource.setPassword(getPassword());
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapteri = new HibernateJpaVendorAdapter();
        adapteri.setGenerateDdl(true);
        adapteri.setDatabase(Database.MYSQL);
        return adapteri;
    }
    
    private String getPassword(){
        String file = environment.production() ?
                "/home/ohjelmatiedostot/NegativeHarmonyBackend/d1.dat"
                : "d1.dat";
        try {
            return Files.readString(Paths.get(file)).trim();
        } catch (IOException ex) {
            return null;
        }
    }
}
