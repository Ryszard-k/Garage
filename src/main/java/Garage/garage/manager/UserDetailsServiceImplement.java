package Garage.garage.manager;

import Garage.garage.DAO.UserRepo;
import Garage.garage.DAO.entity.Car;
import Garage.garage.DAO.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplement implements UserDetailsService {

    private UserRepo userRepo;

    @Autowired
    public UserDetailsServiceImplement(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String a) throws UsernameNotFoundException {
        return userRepo.findByUsername(a);
    }

}
