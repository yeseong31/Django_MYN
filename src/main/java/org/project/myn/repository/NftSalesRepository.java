package org.project.myn.repository;

import org.project.myn.entity.NftSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NftSalesRepository extends JpaRepository<NftSales, Long>, QuerydslPredicateExecutor<NftSales> {
}
