package org.group4.Edu_Course_Catalog.repository;

import org.group4.Edu_Course_Catalog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByAccount(String account);

    boolean existsByAccount(String account);

    Optional<User> findById(int userId);

    Page<User> findByStatus(String status, Pageable pageable);

    List<User> findByStatus(String status);
}
