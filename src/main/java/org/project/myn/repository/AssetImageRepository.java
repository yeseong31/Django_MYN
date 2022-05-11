package org.project.myn.repository;

import org.project.myn.entity.AssetImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AssetImageRepository extends JpaRepository<AssetImage, Long>, QuerydslPredicateExecutor<AssetImage> {
}
