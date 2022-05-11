package org.project.myn.repository;

import org.project.myn.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AssetRepository extends JpaRepository<Asset, Long>, QuerydslPredicateExecutor<Asset> {
}
