
package harmony.config;

import harmony.services.RunEnvironment;
import harmony.services.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConf extends WebSecurityConfigurerAdapter{
    @Autowired
    private RunEnvironment environment;
    @Autowired
    private TokenFilter filter;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.addFilter(filter);
        http.authorizeRequests()
            .antMatchers("/login").permitAll()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers("/**").authenticated();
        http.csrf().disable();
    }
        
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String origin = environment.production() ?
                        "https://harmonia.muurahainen.net" : "https://localhost:3000";
                registry.addMapping("/**")
                        .allowedOrigins(origin);
            }
        };
    }
}
