package com.example.library.Repository;

import com.example.library.Model.CustomUser;
import com.example.library.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    public Optional<Role> findByid(Long id);
}
