package org.project.myn.repository;

import org.project.myn.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CollectionRepository extends JpaRepository<Collection, Long>, QuerydslPredicateExecutor<Collection> {
}
