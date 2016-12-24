package com.raaldi.banker.authorization.configuration;

import com.raaldi.banker.util.model.Role;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class BankerResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    final String[] managerMatchers = new String[] { "/users/**", "/lotteries/**", "/plays/**", "/currencies/**",
        "/shops/**" };
    final String[] supervisorMatchers = new String[] { "/users/**" };

    // http.antMatcher("/auth/user").authorizeRequests().anyRequest().authenticated();
    http.authorizeRequests().antMatchers("/auth/sign-out/*").authenticated().antMatchers("/**/*")
        .hasAuthority(Role.ADMIN.name()).antMatchers(managerMatchers).hasAuthority(Role.MANAGER.name())
        .antMatchers(supervisorMatchers).hasAuthority(Role.SUPERVISOR.name()).anyRequest().authenticated();
  }
}
