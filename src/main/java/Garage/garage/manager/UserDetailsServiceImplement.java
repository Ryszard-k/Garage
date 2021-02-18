package Garage.garage.Manager;

import Garage.garage.Model.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
