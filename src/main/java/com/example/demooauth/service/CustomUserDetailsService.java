package com.example.demooauth.service;

import com.example.demooauth.model.CustomUserDetails;
import com.example.demooauth.model.SecurityGroup;
import com.example.demooauth.model.User;
import com.example.demooauth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityGroupService securityGroupService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.getByUsername(username);

        if (users.isEmpty()) {
            throw new UsernameNotFoundException("Could not find the user " + username);
        }

        User user = users.get(0);

        List<SecurityGroup> securityGroups = securityGroupService.listUserGroups(user.getCompanyId(), user.getId());

        return new CustomUserDetails(user, securityGroups.stream()
                .map(e -> e.getId())
                .collect(Collectors.toList()));
    }
}
