package com.project.securerestfulapi.repository;

import com.project.securerestfulapi.entity.ERole;
import com.project.securerestfulapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
