package org.project.myn.repository;

import org.project.myn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, String>/*, QuerydslPredicateExecutor<User> */ {
}
