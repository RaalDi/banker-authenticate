package com.raaldi.banker.authentication.service;

import com.raaldi.banker.authentication.model.User;
import com.raaldi.banker.authentication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/** User service provides access to the user repository. */
@Service("userService")
@Transactional
public class UserService {

  /** The user repository. */
  private UserRepository repository;

  @Autowired
  public UserService(final UserRepository repository) {
    this.repository = repository;
  }

  public void save(final User model) {
    repository.save(model);
  }

  public User findOne(final long id) {
    return repository.findOne(id);
  }

  public Iterable<User> findAll() {
    return repository.findAll();
  }

  public boolean exists(final User model) {
    return this.exists(model.getUserId());
  }

  public boolean exists(final long id) {
    return repository.exists(id);
  }

  /** Find User by UserName. */
  public Optional<User> findByUsername(final String username) {
    return repository.findByUsername(username);
  }

  /** Find User by UserName. */
  public Optional<User> findByUsernameAndPassword(final String username, final String password) {
    return repository.findByUsernameAndPassword(username, password);
  }
}
