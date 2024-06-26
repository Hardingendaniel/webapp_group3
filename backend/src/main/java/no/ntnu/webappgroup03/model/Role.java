package no.ntnu.webappgroup03.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * User role (Admin, Regular user, etc.)
 */
@Entity(name = "roles")
@Schema(
    description = "Represents a Role"
)
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users = new LinkedHashSet<>();

  /**
   * Empty constructor needed for JPA
   */
  public Role() {
  }

  public Role(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
