package org.group4.Edu_Course_Catalog.repository;

import org.group4.Edu_Course_Catalog.entity.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
}
