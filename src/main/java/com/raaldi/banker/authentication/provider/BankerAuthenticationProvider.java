package com.raaldi.banker.authentication.provider;

import com.raaldi.banker.authentication.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BankerAuthenticationProvider implements AuthenticationProvider {

  private UserService userService;

  @Autowired
  public BankerAuthenticationProvider(final UserService userService) {
    this.userService = userService;
  }

  @Override
  public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
    final String username = authentication.getName();
    final String password = authentication.getCredentials().toString();
    log.info("###### Authentication Username: {}, Password: {}", username, password);
    return userService.findByUsernameAndPassword(username, password).map(user -> {
      log.info("###### Username: {} Has been Authenticated", username);
      return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); // new
                                                                                         // BankerAuthentication(user);
    }).orElse(null);
  }

  @Override
  public boolean supports(final Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
