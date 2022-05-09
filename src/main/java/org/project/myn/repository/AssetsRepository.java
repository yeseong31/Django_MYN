package org.project.myn.repository;

import org.project.myn.entity.Assets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AssetsRepository extends JpaRepository<Assets, Long>, QuerydslPredicateExecutor<Assets> {
}
