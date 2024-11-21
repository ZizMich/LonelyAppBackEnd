package com.aziz.lonelyapp.repository;

import com.aziz.lonelyapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
/**
 * This interface represents a repository for managing {@link Role} entities.
 * It extends Spring Data JPA's {@link JpaRepository} to provide basic CRUD operations.
 * Additionally, it includes custom query methods for retrieving distinct task groups and
 * finding task progress models by user ID.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
    /**
     * Finds all task roles associated with a given name.
     *
     * @param name The unique identifier of the role.
     * @return A task progress models associated with the given role ID.
     */
    Optional<Role> findByName(String name);
}
