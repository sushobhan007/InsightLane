package com.blog.insight_lane.security;

import com.blog.insight_lane.entities.User;
import com.blog.insight_lane.exceptions.ResourceNotFoundException;
import com.blog.insight_lane.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("User", " User Id: ", username)
        );
        return user;
    }
}
