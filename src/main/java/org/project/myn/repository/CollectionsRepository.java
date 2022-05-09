package org.project.myn.repository;

import org.project.myn.entity.Collections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CollectionsRepository extends JpaRepository<Collections, Long>, QuerydslPredicateExecutor<Collections> {
}
