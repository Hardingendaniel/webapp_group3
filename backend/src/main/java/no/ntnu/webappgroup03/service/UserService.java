package no.ntnu.webappgroup03.service;

import java.util.Optional;
import no.ntnu.webappgroup03.model.User;
import no.ntnu.webappgroup03.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  /**
   * Get all user currently stored in the application state (database).
   *
   * @return all users.
   */
  public Iterable<User> getAll() {
    return userRepository.findAll();
  }

  /**
   * Look up a user in the application state (database).
   *
   * @param id ID of the user to look up
   * @return The user or null if none found
   */
  public Optional<User> findUserById(Integer id) {
    return userRepository.findById(id);
  }

  /**
   * Add a user to the database.
   *
   * @param user The user to add
   * @return True on success, false if the user was not added.
   */
  public boolean add(User user) {
    boolean added = false;
    if (canBeAdded(user)) {
      userRepository.save(user);
      added = true;
    }
    return added;
  }

  /**
   * Check if the provided user can be added to the application state (database).
   *
   * @param user User to be checked
   * @return True if the user is valid and can be added to the database
   */
  private boolean canBeAdded(User user) {
    return user != null && user.isValid()
        && (userRepository.findById(user.getId()).isEmpty());
    // user.getId() == null ||
  }

  /**
   * Method to create a new user and checks if
   * email already exist.
   *
   * @param user the user to be created.
   * @return returns the new user.
   * @throws Exception exception to be cast if email already exists.
   */
  public User registerUser(User user) throws Exception {
    if (userRepository.existsByEmail(user.getEmail())) {
      throw new Exception("Email already exists");
    }
    user.setPassword(user.getPassword());
    return userRepository.save(user);
  }


  /**
   * Delete a user from application state (database).
   *
   * @param id ID of the user to delete
   * @return true when deleted, false on error
   */
  public boolean deleteUser(int id) {
    boolean deleted = false;
    if (findUserById(id).isPresent()) {
      userRepository.deleteById(id);
      deleted = true;
    }
    return deleted;
  }
}
