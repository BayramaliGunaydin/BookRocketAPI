package com.example.library.Repository;


import com.example.library.Model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<CustomUser, Long> {
     public Optional<CustomUser> findByUsername(String username);

     @Query("SELECT u FROM CustomUser u WHERE u.username LIKE %:username%")
     List<CustomUser> findUsersWithPartOfName(@Param("username") String username);

     @Transactional
     @Modifying
     @Query("UPDATE CustomUser SET role.id=3 WHERE id = :userid")
     public void setEditor(@Param("userid") Long userid);
}
