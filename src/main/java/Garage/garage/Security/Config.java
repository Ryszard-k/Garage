package Garage.garage.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.util.Collections;


@Configuration
public class Config extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(new User("User1",
                        passwordEncoder().encode("User1"),
                        Collections.singleton(new SimpleGrantedAuthority("User"))));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/Grid").hasAuthority("User")
                .antMatchers("/AddCars").hasAuthority("User")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .defaultSuccessUrl("/Grid", true)
                .and()
                .logout().permitAll()
                .deleteCookies("JSESSIONID");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }
}
