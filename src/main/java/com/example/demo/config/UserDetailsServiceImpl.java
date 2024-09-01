package com.example.demo.config;

import com.example.demo.Entities.User;
import com.example.demo.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

// 2nd step involves implementing UserDetailsService class and all it's methods
// here we need to return the object of UserDetails that we just implemented in Custom_User_Details
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // fetching user from database
        User user = userRepository.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Username not Found !!");
        }

        Custom_User_Details custom_user_details = new Custom_User_Details(user);
        return custom_user_details;
    }
}
