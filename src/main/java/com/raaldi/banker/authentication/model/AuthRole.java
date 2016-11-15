package com.raaldi.banker.authentication.model;

import org.springframework.security.core.GrantedAuthority;

public enum AuthRole implements GrantedAuthority {
  ADMIN, MANAGER, SUPERVISOR, USER;

  @Override
  public String getAuthority() {
    return this.name();
  }
}
