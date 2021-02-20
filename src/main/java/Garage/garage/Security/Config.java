package Garage.garage.Security;

import Garage.garage.Model.UserRepo;
import Garage.garage.Model.entity.User;
import Garage.garage.Manager.UserDetailsServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class Config extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImplement userDetailsServiceImplement;
    private UserRepo userRepo;

    @Autowired
    public Config(UserDetailsServiceImplement userDetailsServiceImplement, UserRepo userRepo) {
        this.userDetailsServiceImplement = userDetailsServiceImplement;
        this.userRepo = userRepo;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImplement);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/employees/cars").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .defaultSuccessUrl("/employees/cars", true)
                .and()
                .logout().permitAll()
                .deleteCookies("JSESSIONID");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillUserDB(){
        User user1 = new User("Employee", passwordEncoder().encode("Employee1"), "Employee");
        userRepo.save(user1);
        User user2 = new User("Customer", passwordEncoder().encode("Customer1"), "Customer");
        userRepo.save(user2);
    }
}
