package com.raaldi.banker.authorization.token;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BankerTokenEnhancer implements TokenEnhancer {

  private static final String ISSUER = "com.raaldi.banker";
  private static final String USER_KEY = "user";
  private static final long EXPIRATION_HOURS = 12L;

  @Getter(AccessLevel.PRIVATE)
  private ObjectMapper objectMapper;

  @Autowired
  public BankerTokenEnhancer(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  @SneakyThrows
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    final Map<String, Object> additionalInfo = new HashMap<>();
    final String jsonUser = getObjectMapper().writeValueAsString(authentication.getPrincipal());
    additionalInfo.put(USER_KEY, jsonUser);
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
    return accessToken;
  }
}
