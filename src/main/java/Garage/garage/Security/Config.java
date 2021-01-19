package Garage.garage.Security;

import Garage.garage.DAO.UserRepo;
import Garage.garage.DAO.entity.User;
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
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/employees/cars").hasAuthority("User")
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
        User user1 = new User("User1", passwordEncoder().encode("User1"), "User");
        userRepo.save(user1);
    }
}
