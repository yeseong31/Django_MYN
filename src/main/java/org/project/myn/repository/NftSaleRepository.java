package org.project.myn.repository;

import org.project.myn.entity.NftSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NftSaleRepository extends JpaRepository<NftSale, Long>, QuerydslPredicateExecutor<NftSale> {
}
