package org.project.myn.repository;

import org.project.myn.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AccountsRepository extends JpaRepository<Accounts, Long>, QuerydslPredicateExecutor<Accounts> {
}
