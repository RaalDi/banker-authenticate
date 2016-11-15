package com.raaldi.banker.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raaldi.banker.util.model.AbstractModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "bk_user")
@Cacheable(true)
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "User")
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT c FROM User c"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT c FROM User c WHERE c.username = :username") })
// @SQLInsert(sql = "INSERT INTO bk_user (first_name, last_name, username,
// gov_id, phone_number, password, active, company_id, shop_id) VALUES
// (?,?,?,?,?,CRYPT(?, GEN_SALT('bf')),?,?,?)")
// @SQLUpdate(sql = "UPDATE bk_user SET first_name = ?, last_name = ?, username
// =
// ?, gov_id = ?, phone_number = ?, password = CRYPT(?, GEN_SALT('bf')), active
// = ?, company_id = ?, shop_id = ? WHERE user_id = ?")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractModel implements UserDetails {

  private static final long serialVersionUID = -8491559884264082143L;

  @Id
  @SequenceGenerator(name = "bk-user-seq-gen", sequenceName = "bk_user_seq_id", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bk-user-seq-gen")
  @Column(name = "user_id")
  private long userId;

  @NotNull
  @Size(min = 2, max = 25, message = "2-25 letters and spaces")
  @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
  @Column(name = "first_name", nullable = false)
  private String firstName;

  @NotNull
  @Size(min = 2, max = 25, message = "2-25 letters and spaces")
  @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
  @Column(name = "last_name", nullable = false)
  private String lastName;

  @NotNull
  @Size(min = 2, max = 16, message = "8-16 letters and spaces")
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "gov_id")
  private String govId;

  @NotNull
  @Size(min = 10, max = 12, message = "10-12 Numbers")
  @Digits(fraction = 0, integer = 12, message = "Not valid")
  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @JsonIgnore
  @NotNull
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "signed_in_date", columnDefinition = "timestamp")
  private LocalDateTime signedInDate;

  @JsonIgnore
  @Column(name = "token_expiration_date", columnDefinition = "timestamp")
  private LocalDateTime tokeExpirationDate;

  @NotNull
  @Column(name = "active", nullable = false, columnDefinition = "boolean default false")
  private boolean active;

  @NotNull
  @Column(name = "signed_in", nullable = false, columnDefinition = "boolean default false")
  private boolean signedIn;

  @NotNull
  @Column(name = "company_id", nullable = false)
  private long companyId;

  @Column(name = "address_id")
  private Long addressId;

  @NotNull
  @Column(name = "role", nullable = false, columnDefinition = "varchar(25) default 'USER'")
  @Enumerated(EnumType.STRING)
  private AuthRole role;

  @Column(name = "shop_id")
  private Long shopId;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(getRole());
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isEnabled() {
    return active;
  }
}
