package com.lush.javaAggregator.service;

import com.lush.javaAggregator.modles.LoginUser;
import com.lush.javaAggregator.modles.User;
import com.lush.javaAggregator.repositories.TestRepositories;
import com.lush.javaAggregator.repositories.UserRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class TestService
//    implements UserDetailsService
{

  @Autowired
  private TestRepositories testRepositories;

  @Autowired
  private UserRepository userRepository;

//  @Autowired
//  private UserDetailsService userDetailsService;

//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//    User user = userRepository.findByUsername(username);
//    if (user == null) {
//      throw new UsernameNotFoundException("UsernameNotFound [" + username + "]");
//    }
//    UserDetails loginUser = createUser(user);
//    return loginUser;
//  }

  private LoginUser createUser(User user) {
    LoginUser loginUser = new LoginUser(user);
    if (loginUser.getUserType().equals("1")) {
      loginUser.setRoles(Arrays.asList("ROLE_ADMIN"));
    } else {
      loginUser.setRoles(Arrays.asList("ROLE_USER"));
    }
    return loginUser;
  }

}
